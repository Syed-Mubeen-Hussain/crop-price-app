package com.example.cropprice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChooserActivity extends AppCompatActivity {

    Button btnBuyer, btnSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        btnSeller = findViewById(R.id.btnSeller);
        btnBuyer = findViewById(R.id.btnBuyer);

        btnBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SweetAlertDialog pDialog = new SweetAlertDialog(ChooserActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(3000);
                            pDialog.dismiss();
                            SharedPreferences sharedPreferences = getSharedPreferences("BuyerLoginSharedPreference", MODE_PRIVATE);
                            if (!sharedPreferences.getString("name", "").equals("")) {
                                Intent intent = new Intent(ChooserActivity.this, BuyerHomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(ChooserActivity.this, BuyerLoginAndRegisterActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(ChooserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                thread.start();
            }
        });

        btnSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SweetAlertDialog pDialog = new SweetAlertDialog(ChooserActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(3000);
                            pDialog.dismiss();
                            SharedPreferences sharedPreferences = getSharedPreferences("SellerLoginSharedPreference", MODE_PRIVATE);
                            if (!sharedPreferences.getString("name", "").equals("")) {
                                Intent intent = new Intent(ChooserActivity.this, SellerHomeActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(ChooserActivity.this, SellerLoginAndRegisterActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(ChooserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                thread.start();
            }
        });
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

    @Override
    public void onBackPressed() {
        exitDialog();
    }
}