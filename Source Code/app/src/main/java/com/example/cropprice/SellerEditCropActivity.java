package com.example.cropprice;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SellerEditCropActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imgSellerEditCrop;
    TextView tvUploadSellerEditCrop;
    TextInputLayout sellerEditCropName, sellerEditCropPrice, sellerEditCropQty, sellerEditCropDescription;
    Button sellerEditCropSubmitButton;
    String imageFilePath = "";
    String updateCropUrl = "https://crop-price-web.000webhostapp.com/seller.php?updateAuction=true";
    String id;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_edit_crop);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // set the toolbar
        toolbar = findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Edit Crop");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));

        imgSellerEditCrop = findViewById(R.id.imgSellerEditCrop);
        tvUploadSellerEditCrop = findViewById(R.id.tvUploadSellerEditCrop);
        sellerEditCropName = findViewById(R.id.sellerEditCropName);
        sellerEditCropPrice = findViewById(R.id.sellerEditCropPrice);
        sellerEditCropQty = findViewById(R.id.sellerEditCropQty);
        sellerEditCropDescription = findViewById(R.id.sellerEditCropDescription);
        sellerEditCropSubmitButton = findViewById(R.id.sellerEditCropSubmitButton);

        sellerEditCropName.getEditText().setText(getIntent().getStringExtra("name").toString());
        sellerEditCropPrice.getEditText().setText(getIntent().getStringExtra("price").toString());
        sellerEditCropQty.getEditText().setText(getIntent().getStringExtra("qty").toString());
        sellerEditCropDescription.getEditText().setText(getIntent().getStringExtra("description").toString());
        Glide.with(SellerEditCropActivity.this).load(getIntent().getStringExtra("image")).placeholder(R.drawable.loading).into(imgSellerEditCrop);

        id = getIntent().getStringExtra("id");

        imgSellerEditCrop.setOnClickListener(new View.OnClickListener() {
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

        tvUploadSellerEditCrop.setOnClickListener(new View.OnClickListener() {
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

        sellerEditCropSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sellerEditCropName.setError("");
                sellerEditCropPrice.setError("");
                sellerEditCropDescription.setError("");

                String name = sellerEditCropName.getEditText().getText().toString();
                String price = sellerEditCropPrice.getEditText().getText().toString();
                String qty = sellerEditCropQty.getEditText().getText().toString();
                String description = sellerEditCropDescription.getEditText().getText().toString();
                 if (name.trim().equals("")) {
                    sellerEditCropName.setError("Name is required");
                } else if (price.trim().equals("")) {
                    sellerEditCropPrice.setError("Price is required");
                } else if (qty.trim().equals("")) {
                    sellerEditCropQty.setError("Qty is required");
                } else if (description.trim().equals("")) {
                    sellerEditCropDescription.setError("Description is required");
                } else if (getIntent().getStringExtra("image") == null) {
                    Toast.makeText(SellerEditCropActivity.this, "Please upload image", Toast.LENGTH_SHORT).show();
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(SellerEditCropActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, updateCropUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(SellerEditCropActivity.this, "Crop Update Successfully", Toast.LENGTH_LONG).show();
                                pDialog.dismiss();
                                startActivity(new Intent(SellerEditCropActivity.this, SellerHomeActivity.class));
                            } else {
                                Toast.makeText(SellerEditCropActivity.this, "Crop Not Update", Toast.LENGTH_LONG).show();
                                pDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.dismiss();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> param = new HashMap<>();
                            param.put("id", id);
                            param.put("name", name);
                            param.put("price", price);
                            param.put("description", description);
                            param.put("qty", qty);
                            param.put("image", imageFilePath);

                            return param;
                        }
                    };

                     if (requestQueue == null) {
                         requestQueue = Volley.newRequestQueue(SellerEditCropActivity.this);
                         requestQueue.add(request);
                     } else {
                         requestQueue.add(request);
                     }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            data.getData().toString();
            imageFilePath = data.getData().toString();
            imgSellerEditCrop.setImageURI(Uri.parse(imageFilePath));
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