package com.example.cropprice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cropprice.Fragments.HomeFragment;
import com.example.cropprice.Fragments.SearchFragment;
import com.example.cropprice.Fragments.SellerAddFragment;
import com.example.cropprice.Fragments.SellerProfileFragment;
import com.example.cropprice.Fragments.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SellerHomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView toolbar_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new HomeFragment());
        transaction.commit();
        
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                        break;
                    case R.id.shop:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ShopFragment()).commit();
                        break;
                    case R.id.add:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SellerAddFragment()).commit();
                        break;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).commit();
                        break;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SellerProfileFragment()).commit();
                        break;
                }
                return true;
            }
        });

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        toolbar_search = toolbar.findViewById(R.id.toolbar_search);
        toolbar_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).commit();
                bottomNavigationView.setSelectedItemId(R.id.search);
            }
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer
        );

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:
                        transaction.replace(R.id.container, new HomeFragment());
                        break;
                    case R.id.shop:
                        transaction.replace(R.id.container, new ShopFragment());
                        break;
                    case R.id.add:
                        transaction.replace(R.id.container, new SellerAddFragment());
                        break;
                    case R.id.profile:
                        transaction.replace(R.id.container, new SellerProfileFragment());
                        break;
                }
                transaction.addToBackStack(null).commit();
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        String loadActivity = getIntent().getStringExtra("loadActivity");
        if(loadActivity !=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SellerAddFragment()).commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            String imgSrc = data.getData().toString();
            Intent intent = new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
            intent.putExtra("loadActivity",imgSrc);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            exitDialog();
        }
    }

    public void exitDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Exit").setContentText("Are you sure you want to exit?").setCancelText("No").setConfirmText("Yes").showCancelButton(true).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                finish();
            }
        }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.cancel();
            }
        }).show();
    }

}