package com.example.cropprice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cropprice.Adapters.BuyerLoginAndRegisterAdapter;
import com.example.cropprice.Adapters.SellerLoginAndRegisterAdapter;
import com.google.android.material.tabs.TabLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BuyerLoginAndRegisterActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    BuyerLoginAndRegisterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_login_and_register);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Register"));

        adapter = new BuyerLoginAndRegisterAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        String loadActivity = getIntent().getStringExtra("loadActivity");
        if(loadActivity !=null){
            viewPager2.setCurrentItem(1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            String imgSrc = data.getData().toString();
            Intent intent = new Intent(BuyerLoginAndRegisterActivity.this, BuyerLoginAndRegisterActivity.class);
            intent.putExtra("loadActivity",imgSrc);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}