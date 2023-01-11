package com.example.cropprice;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BuyerEditProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imgSellerEditProfile;
    TextView tvUploadSellerEditProfile;
    TextInputLayout sellerEditProfileName, sellerEditProfileEmail, sellerEditProfileContact, sellerEditProfilePassword;
    Button btnSellerEditProfile;
    String contactRegex = "^\\d{11}$";
    String emailRegex = "^[a-z0-9_]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
    String imageFilePath = "";
    String updateBuyerProfile = "https://crop-price-web.000webhostapp.com/seller.php?updateBuyerProfile=true";
    String id;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_edit_profile);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // set the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Edit Profile");

        imgSellerEditProfile = findViewById(R.id.imgSellerEditProfile);
        tvUploadSellerEditProfile = findViewById(R.id.tvUploadSellerEditProfile);
        sellerEditProfileName = findViewById(R.id.sellerEditProfileName);
        sellerEditProfileContact = findViewById(R.id.sellerEditProfileContact);
        sellerEditProfileEmail = findViewById(R.id.sellerEditProfileEmail);
        sellerEditProfilePassword = findViewById(R.id.sellerEditProfilePassword);
        btnSellerEditProfile = findViewById(R.id.btnSellerEditProfile);

        SharedPreferences sharedPreferences = getSharedPreferences("BuyerLoginSharedPreference", MODE_PRIVATE);
        if (!sharedPreferences.getString("name", "").equals("")) {
            id = sharedPreferences.getString("id", "");
            sellerEditProfileName.getEditText().setText(sharedPreferences.getString("name", ""));
            sellerEditProfileContact.getEditText().setText(sharedPreferences.getString("contact", ""));
            sellerEditProfileEmail.getEditText().setText(sharedPreferences.getString("email", ""));
            sellerEditProfilePassword.getEditText().setText(sharedPreferences.getString("password", ""));
            imageFilePath = sharedPreferences.getString("image", "");
            Glide.with(getApplicationContext()).load(sharedPreferences.getString("image", "")).placeholder(R.drawable.loading).into(imgSellerEditProfile);
        }

        btnSellerEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sellerEditProfileName.setError("");
                sellerEditProfileContact.setError("");
                sellerEditProfileEmail.setError("");
                sellerEditProfilePassword.setError("");

                String name = sellerEditProfileName.getEditText().getText().toString();
                String contact = sellerEditProfileContact.getEditText().getText().toString();
                String email = sellerEditProfileEmail.getEditText().getText().toString();
                String password = sellerEditProfilePassword.getEditText().getText().toString();
                if (name.trim().equals("")) {
                    sellerEditProfileName.setError("Name is required");
                } else if (contact.trim().equals("")) {
                    sellerEditProfileContact.setError("Contact is required");
                } else if (contact.trim().equals("") || !contact.matches(contactRegex)) {
                    sellerEditProfileContact.setError("Contact number like 03xxxxxxxxx");
                } else if (email.trim().equals("")) {
                    sellerEditProfileEmail.setError("Email is required");
                } else if (email.trim().equals("") || !email.matches(emailRegex)) {
                    sellerEditProfileEmail.setError("Email format is invalid");
                } else if (password.trim().equals("")) {
                    sellerEditProfilePassword.setError("Password is required");
                } else if (imageFilePath.equals("")) {
                    Toast.makeText(BuyerEditProfileActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(BuyerEditProfileActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, updateBuyerProfile, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(getApplicationContext(), "Profile Update Successfully", Toast.LENGTH_LONG).show();
                                pDialog.dismiss();
                                sellerEditProfileName.getEditText().setText("");
                                sellerEditProfileContact.getEditText().setText("");
                                sellerEditProfileEmail.getEditText().setText("");
                                sellerEditProfilePassword.getEditText().setText("");
                                imgSellerEditProfile.setImageResource(R.drawable.user);

                                SharedPreferences sharedPreferences = getSharedPreferences("BuyerLoginSharedPreference", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("id", id);
                                myEdit.putString("name", name);
                                myEdit.putString("contact", contact);
                                myEdit.putString("email", email);
                                myEdit.putString("password", password);
                                myEdit.putString("image", imageFilePath);
                                myEdit.putString("seller", "Seller");
                                myEdit.apply();

                                startActivity(new Intent(BuyerEditProfileActivity.this, BuyerHomeActivity.class));
                                finish();

                            } else if (response.equals("already")) {
                                Toast.makeText(BuyerEditProfileActivity.this, "Email already exist", Toast.LENGTH_LONG).show();
                                pDialog.dismiss();
                            } else {
                                Toast.makeText(BuyerEditProfileActivity.this, "Profile Not Update", Toast.LENGTH_LONG).show();
                                pDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(BuyerEditProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> param = new HashMap<>();
                            param.put("id", id);
                            param.put("name", name);
                            param.put("contact", contact);
                            param.put("email", email);
                            param.put("password", password);
                            param.put("image", imageFilePath);

                            return param;
                        }
                    };

                    if (requestQueue == null) {
                        requestQueue = Volley.newRequestQueue(BuyerEditProfileActivity.this);
                        requestQueue.add(request);
                    } else {
                        requestQueue.add(request);
                    }
                }
            }
        });

        imgSellerEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Image Browse"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        tvUploadSellerEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Image Browse"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            data.getData().toString();
            imageFilePath = data.getData().toString();
            imgSellerEditProfile.setImageURI(Uri.parse(imageFilePath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}