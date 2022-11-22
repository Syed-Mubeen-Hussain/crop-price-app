package com.example.cropprice.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cropprice.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BuyerLoginFragment extends Fragment {
    Button btnSellerRegister;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyer_login, container, false);
        btnSellerRegister = view.findViewById(R.id.btnSellerRegister);
        btnSellerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                errorMessage();
            }
        });
        return view;
    }

    public void errorMessage() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("Something went wrong!").show();
    }

}