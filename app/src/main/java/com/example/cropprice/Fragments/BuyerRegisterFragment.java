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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BuyerRegisterFragment extends Fragment {

    ImageView imgBuyerRegister;
    TextView tvUploadBuyerRegister;
    Button btnBuyerRegister;
    EditText etBuyerRegisterName, etBuyerRegisterPhone, etBuyerLoginEmail, etBuyerLoginPassword;
    String contactRegex = "^\\d{11}$";
    String emailRegex = "^[a-z0-9_]+@[a-z0-9.-]+\\.[a-z]{3}$";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buyer_register, container, false);

        imgBuyerRegister = view.findViewById(R.id.imgBuyerRegister);
        tvUploadBuyerRegister = view.findViewById(R.id.tvUploadBuyerRegister);
        etBuyerRegisterName = view.findViewById(R.id.etBuyerRegisterName);
        etBuyerRegisterPhone = view.findViewById(R.id.etBuyerRegisterPhone);
        etBuyerLoginEmail = view.findViewById(R.id.etBuyerLoginEmail);
        etBuyerLoginPassword = view.findViewById(R.id.etBuyerLoginPassword);
        btnBuyerRegister = view.findViewById(R.id.btnBuyerRegister);
        btnBuyerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etBuyerRegisterName.getText().toString();
                String contact = etBuyerRegisterPhone.getText().toString();
                String email = etBuyerLoginEmail.getText().toString();
                String password = etBuyerLoginPassword.getText().toString();
                String loadActivity = getActivity().getIntent().getStringExtra("loadActivity");
                if (name.trim().equals("")) {
                    etBuyerRegisterName.setError("Name is required");
                } else if (contact.trim().equals("")) {
                    etBuyerRegisterPhone.setError("Contact is required");
                } else if (contact.trim().equals("") || !contact.matches(contactRegex)) {
                    etBuyerRegisterPhone.setError("Contact number like 03xxxxxxxxx");
                } else if (email.trim().equals("")) {
                    etBuyerLoginEmail.setError("Email is required");
                } else if (email.trim().equals("") || !email.matches(emailRegex)) {
                    etBuyerLoginEmail.setError("Email format is invalid");
                } else if (password.trim().equals("")) {
                    etBuyerLoginPassword.setError("Password is required");
                } else if (loadActivity == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please upload image", Toast.LENGTH_SHORT).show();
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(true);
                    pDialog.show();
                }
            }
        });


        String getImageSource = getActivity().getIntent().getStringExtra("loadActivity");
        if (getImageSource != null) {
            imgBuyerRegister.setImageURI(Uri.parse(getImageSource.toString()));
        }

        imgBuyerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getActivity().getApplicationContext())
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

        tvUploadBuyerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getActivity().getApplicationContext())
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
        new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("Something went wrong!").show();
    }
}