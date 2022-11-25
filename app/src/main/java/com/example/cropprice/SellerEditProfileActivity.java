package com.example.cropprice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerEditProfileActivity extends AppCompatActivity {

    ImageView imgSellerEditProfile;
    TextView tvUploadSellerEditProfile;
    TextInputLayout sellerEditProfileName, sellerEditProfileEmail, sellerEditProfileContact, sellerEditProfilePassword;
    Button btnSellerEditProfile;
    String contactRegex = "^\\d{11}$";
    String emailRegex = "^[a-z0-9_]+@[a-z0-9.-]+\\.[a-z]{3}$";
    String imageFilePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_edit_profile);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        imgSellerEditProfile = findViewById(R.id.imgSellerEditProfile);
        tvUploadSellerEditProfile = findViewById(R.id.tvUploadSellerEditProfile);
        sellerEditProfileName = findViewById(R.id.sellerEditProfileName);
        sellerEditProfileContact = findViewById(R.id.sellerEditProfileContact);
        sellerEditProfileEmail = findViewById(R.id.sellerEditProfileEmail);
        sellerEditProfilePassword = findViewById(R.id.sellerEditProfilePassword);
        btnSellerEditProfile = findViewById(R.id.btnSellerEditProfile);

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
                }
                else if(imageFilePath.equals("")){
                    Toast.makeText(SellerEditProfileActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SellerEditProfileActivity.this, "Submit", Toast.LENGTH_SHORT).show();
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
}