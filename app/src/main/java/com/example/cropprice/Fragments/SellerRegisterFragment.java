package com.example.cropprice.Fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropprice.R;
import com.example.cropprice.SellerEditProfileActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SellerRegisterFragment extends Fragment {

    ImageView imgSellerRegister;
    EditText etSellerRegisterName, etSellerRegisterPhone, etSellerLoginEmail, etSellerLoginPassword;
    TextView tvUploadSellerRegister;
    Button btnSellerRegister;
    String contactRegex = "^\\d{11}$";
    String emailRegex = "^[a-z0-9_]+@[a-z0-9.-]+\\.[a-z]{3}$";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seller_register, container, false);

        imgSellerRegister = view.findViewById(R.id.imgSellerRegister);
        tvUploadSellerRegister = view.findViewById(R.id.tvUploadSellerRegister);
        btnSellerRegister = view.findViewById(R.id.btnSellerRegister);
        etSellerRegisterName = view.findViewById(R.id.etSellerRegisterName);
        etSellerRegisterPhone = view.findViewById(R.id.etSellerRegisterPhone);
        etSellerLoginEmail = view.findViewById(R.id.etSellerLoginEmail);
        etSellerLoginPassword = view.findViewById(R.id.etSellerLoginPassword);


        btnSellerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etSellerRegisterName.getText().toString();
                String contact = etSellerRegisterPhone.getText().toString();
                String email = etSellerLoginEmail.getText().toString();
                String password = etSellerLoginPassword.getText().toString();
                String loadActivity = getActivity().getIntent().getStringExtra("loadActivity");
                if (name.trim().equals("")) {
                    etSellerRegisterName.setError("Name is required");
                } else if (contact.trim().equals("")) {
                    etSellerRegisterPhone.setError("Contact is required");
                } else if (contact.trim().equals("") || !contact.matches(contactRegex)) {
                    etSellerRegisterPhone.setError("Contact number like 03xxxxxxxxx");
                } else if (email.trim().equals("")) {
                    etSellerLoginEmail.setError("Email is required");
                } else if (email.trim().equals("") || !email.matches(emailRegex)) {
                    etSellerLoginEmail.setError("Email format is invalid");
                } else if (password.trim().equals("")) {
                    etSellerLoginPassword.setError("Password is required");
                } else if (loadActivity == null) {
                    Toast.makeText(getContext(), "Please upload image", Toast.LENGTH_SHORT).show();
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(true);
                    pDialog.show();
                }
            }
        });


        String getImageSource = getActivity().getIntent().getStringExtra("loadActivity");
        if (getImageSource != null) {
            imgSellerRegister.setImageURI(Uri.parse(getImageSource.toString()));
        }

        imgSellerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                getActivity().startActivityForResult(Intent.createChooser(intent, "Image Browse"), 101);
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

        tvUploadSellerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                getActivity().startActivityForResult(Intent.createChooser(intent, "Image Browse"), 101);
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

        return view;
    }

    public void errorMessage() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("Something went wrong!").show();
    }
}