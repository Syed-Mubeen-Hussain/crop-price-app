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
import androidx.viewpager2.widget.ViewPager2;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cropprice.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BuyerRegisterFragment extends Fragment {

    ViewPager2 viewPager2;
    ImageView imgBuyerRegister;
    TextView tvUploadBuyerRegister;
    Button btnBuyerRegister;
    EditText etBuyerRegisterName, etBuyerRegisterPhone, etBuyerLoginEmail, etBuyerLoginPassword;
    String contactRegex = "^\\d{11}$";
    String emailRegex = "^[a-z0-9_]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
    String buyerRegister = "https://crop-price-web.000webhostapp.com/seller.php?buyerRegister=true";
    private RequestQueue requestQueue;

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
        viewPager2 = getActivity().findViewById(R.id.viewPager2);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BuyerRegisterSharedPreferences",MODE_PRIVATE);
        etBuyerRegisterName.setText(sharedPreferences.getString("name",""));
        etBuyerRegisterPhone.setText(sharedPreferences.getString("contact",""));
        etBuyerLoginEmail.setText(sharedPreferences.getString("email",""));
        etBuyerLoginPassword.setText(sharedPreferences.getString("password",""));
        sharedPreferences.edit().clear().apply();

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
                    SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    String finalLoadActivity = loadActivity;
                    StringRequest request = new StringRequest(Request.Method.POST, buyerRegister, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(getActivity().getApplicationContext(), "Register Successfully", Toast.LENGTH_LONG).show();
                                pDialog.dismiss();
                                etBuyerRegisterName.setText("");
                                etBuyerRegisterPhone.setText("");
                                etBuyerLoginEmail.setText("");
                                etBuyerLoginPassword.setText("");
                                getActivity().getIntent().removeExtra("loadActivity");
                                imgBuyerRegister.setImageResource(R.drawable.user);
                                viewPager2.setCurrentItem(0);
                            }
                            else if(response.equals("already")){
                                Toast.makeText(getActivity().getApplicationContext(), "Email already exist", Toast.LENGTH_LONG).show();
                                pDialog.dismiss();
                            }
                            else {
                                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                pDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                            pDialog.dismiss();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> param = new HashMap<>();
                            param.put("name", name);
                            param.put("contact", contact);
                            param.put("email", email);
                            param.put("password", password);
                            param.put("image",finalLoadActivity);

                            return param;
                        }
                    };

                    if (requestQueue == null) {
                        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                        requestQueue.add(request);
                    } else {
                        requestQueue.add(request);
                    }
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
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BuyerRegisterSharedPreferences",MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("name", etBuyerRegisterName.getText().toString());
                                myEdit.putString("contact", etBuyerRegisterPhone.getText().toString());
                                myEdit.putString("email", etBuyerLoginEmail.getText().toString());
                                myEdit.putString("password", etBuyerLoginPassword.getText().toString());
                                myEdit.apply();


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
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BuyerRegisterSharedPreferences",MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("name", etBuyerRegisterName.getText().toString());
                                myEdit.putString("contact", etBuyerRegisterPhone.getText().toString());
                                myEdit.putString("email", etBuyerLoginEmail.getText().toString());
                                myEdit.putString("password", etBuyerLoginPassword.getText().toString());
                                myEdit.apply();

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
}