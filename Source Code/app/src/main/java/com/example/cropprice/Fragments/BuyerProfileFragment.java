package com.example.cropprice.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.example.cropprice.AboutActivity;
import com.example.cropprice.BuyerEditProfileActivity;
import com.example.cropprice.BuyerHomeActivity;
import com.example.cropprice.BuyerLoginAndRegisterActivity;
import com.example.cropprice.BuyerWiningCropsActivity;
import com.example.cropprice.ContactActivity;
import com.example.cropprice.R;
import com.example.cropprice.SellerEditProfileActivity;
import com.example.cropprice.SellerHomeActivity;
import com.example.cropprice.SellerLoginAndRegisterActivity;
import com.example.cropprice.SellerMyCropsActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyerProfileFragment extends Fragment {

    Toolbar toolbar;
    TextView buyerProfileName, buyerProfileStatus;
    CircleImageView buyerProfileImage;
    LinearLayout buyerProfileMyCrops, buyerEditProfile, buyerProfileLogout;
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyer_profile, container, false);
        buyerProfileMyCrops = view.findViewById(R.id.buyerProfileMyCrops);
        buyerEditProfile = view.findViewById(R.id.buyerEditProfile);
        buyerProfileLogout = view.findViewById(R.id.buyerProfileLogout);
        buyerProfileName = view.findViewById(R.id.buyerProfileName);
        buyerProfileStatus = view.findViewById(R.id.buyerProfileStatus);
        buyerProfileImage = view.findViewById(R.id.buyerProfileImage);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BuyerLoginSharedPreference", MODE_PRIVATE);
        if (!sharedPreferences.getString("name", "").equals("")) {
            id = sharedPreferences.getString("id", "");
            buyerProfileName.setText(sharedPreferences.getString("name", ""));
            buyerProfileStatus.setText(sharedPreferences.getString("buyer", ""));
            Glide.with(getContext()).load(sharedPreferences.getString("image", "")).placeholder(R.drawable.loading).into(buyerProfileImage);
        }

        // set the toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Profile");

        buyerEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), BuyerEditProfileActivity.class));
            }
        });

        buyerProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BuyerLoginSharedPreference", MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();
                BuyerHomeActivity buyerHomeActivity = new BuyerHomeActivity();
                buyerHomeActivity.finish();
                getActivity().finish();
                getActivity().startActivity(new Intent(getActivity().getApplicationContext(), BuyerLoginAndRegisterActivity.class));
            }
        });

        buyerProfileMyCrops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BuyerWiningCropsActivity.class);
                intent.putExtra("id", id);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}