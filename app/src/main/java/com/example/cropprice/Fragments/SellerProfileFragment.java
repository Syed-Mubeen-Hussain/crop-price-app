package com.example.cropprice.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.service.quickaccesswallet.GetWalletCardsCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropprice.R;
import com.example.cropprice.SellerEditProfileActivity;

public class SellerProfileFragment extends Fragment {

    Toolbar toolbar;
    LinearLayout sellerProfileGoToHome, selleEditProfile, sellerProfileLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_profile, container, false);
        sellerProfileGoToHome = view.findViewById(R.id.sellerProfileGoToHome);
        selleEditProfile = view.findViewById(R.id.selleEditProfile);
        sellerProfileLogout = view.findViewById(R.id.sellerProfileLogout);

        // set the toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Profile");

        selleEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getContext(), SellerEditProfileActivity.class));
            }
        });

        sellerProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}