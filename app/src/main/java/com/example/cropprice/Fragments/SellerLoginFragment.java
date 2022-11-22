package com.example.cropprice.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cropprice.R;
import com.example.cropprice.SellerHomeActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SellerLoginFragment extends Fragment {
    Button btnSellerLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_login, container, false);
        btnSellerLogin = view.findViewById(R.id.btnSellerLogin);
        btnSellerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                errorMessage();
                getActivity().startActivity(new Intent(getContext(), SellerHomeActivity.class));
            }
        });
        return view;
    }

    public void errorMessage() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("Something went wrong!").show();
    }

}