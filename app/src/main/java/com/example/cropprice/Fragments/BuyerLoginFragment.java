package com.example.cropprice.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cropprice.R;
import com.example.cropprice.SellerHomeActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BuyerLoginFragment extends Fragment {

    Button btnSellerLogin;
    String emailRegex = "^[a-z0-9_]+@[a-z0-9.-]+\\.[a-z]{3}$";
    EditText etBuyerLoginEmail, etBuyerLoginPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyer_login, container, false);

        etBuyerLoginEmail = view.findViewById(R.id.etBuyerLoginEmail);
        etBuyerLoginPassword = view.findViewById(R.id.etBuyerLoginPassword);
        btnSellerLogin = view.findViewById(R.id.btnBuyerLogin);

        btnSellerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etBuyerLoginEmail.getText().toString();
                String password = etBuyerLoginPassword.getText().toString();

                if (email.trim().equals("")) {
                    etBuyerLoginEmail.setError("Email is required");
                } else if (email.trim().equals("") || !email.matches(emailRegex)) {
                    etBuyerLoginEmail.setError("Email format is invalid");
                } else if (password.trim().equals("")) {
                    etBuyerLoginPassword.setError("Password is required");
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