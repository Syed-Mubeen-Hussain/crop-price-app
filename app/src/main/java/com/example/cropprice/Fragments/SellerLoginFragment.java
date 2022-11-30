package com.example.cropprice.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cropprice.R;
import com.example.cropprice.SellerEditProfileActivity;
import com.example.cropprice.SellerHomeActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SellerLoginFragment extends Fragment {

    Button btnSellerLogin;
    String emailRegex = "^[a-z0-9_]+@[a-z0-9.-]+\\.[a-z]{3}$";
    EditText etSellerLoginEmail, etSellerLoginPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_login, container, false);
        etSellerLoginEmail = view.findViewById(R.id.etSellerLoginEmail);
        etSellerLoginPassword = view.findViewById(R.id.etSellerLoginPassword);
        btnSellerLogin = view.findViewById(R.id.btnSellerLogin);

        btnSellerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etSellerLoginEmail.getText().toString();
                String password = etSellerLoginPassword.getText().toString();

               if (email.trim().equals("")) {
                   etSellerLoginEmail.setError("Email is required");
                } else if (email.trim().equals("") || !email.matches(emailRegex)) {
                   etSellerLoginEmail.setError("Email format is invalid");
                } else if (password.trim().equals("")) {
                   etSellerLoginPassword.setError("Password is required");
                } else {
                   getActivity().startActivity(new Intent(getActivity().getApplicationContext(), SellerHomeActivity.class));
                   getActivity().finish();
                }

            }
        });
        return view;
    }

    public void errorMessage() {
        new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("Something went wrong!").show();
    }

}