package com.example.cropprice.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;
import com.example.cropprice.AboutActivity;
import com.example.cropprice.ContactActivity;
import com.example.cropprice.R;
import com.example.cropprice.SellerEditCropActivity;
import com.example.cropprice.SellerEditProfileActivity;
import com.example.cropprice.SellerHomeActivity;
import com.example.cropprice.SellerLoginAndRegisterActivity;
import com.example.cropprice.SellerMyCropsActivity;
import com.example.cropprice.SellerWiningCropsActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerProfileFragment extends Fragment {

    Toolbar toolbar;
    TextView sellerProfileName, sellerProfileStatus;
    CircleImageView sellerProfileImage;
    LinearLayout sellerProfileMyCrops, sellerProfileWiningCrops, selleEditProfile, sellerProfileLogout;
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_profile, container, false);
        sellerProfileMyCrops = view.findViewById(R.id.sellerProfileMyCrops);
        selleEditProfile = view.findViewById(R.id.selleEditProfile);
        sellerProfileLogout = view.findViewById(R.id.sellerProfileLogout);
        sellerProfileName = view.findViewById(R.id.sellerProfileName);
        sellerProfileStatus = view.findViewById(R.id.sellerProfileStatus);
        sellerProfileImage = view.findViewById(R.id.sellerProfileImage);
        sellerProfileWiningCrops = view.findViewById(R.id.sellerProfileWiningCrops);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SellerLoginSharedPreference", MODE_PRIVATE);
        if (!sharedPreferences.getString("name", "").equals("")) {
            id = sharedPreferences.getString("id", "");
            sellerProfileName.setText(sharedPreferences.getString("name", ""));
            sellerProfileStatus.setText(sharedPreferences.getString("seller", ""));
            Glide.with(getContext()).load(sharedPreferences.getString("image", "")).placeholder(R.drawable.loading).into(sellerProfileImage);
        }

        // set the toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Profile");

        selleEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), SellerEditProfileActivity.class));
            }
        });

        sellerProfileWiningCrops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SellerWiningCropsActivity.class);
                intent.putExtra("id", id);
                getActivity().startActivity(intent);
            }
        });

        sellerProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SellerLoginSharedPreference", MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();
                getActivity().startActivity(new Intent(getContext(), SellerLoginAndRegisterActivity.class));
                getActivity().finish();

                SellerHomeActivity sellerHomeActivity = new SellerHomeActivity();
                sellerHomeActivity.finish();

                SellerEditProfileActivity sellerEditProfileActivity = new SellerEditProfileActivity();
                sellerEditProfileActivity.finish();

                SellerMyCropsActivity sellerMyCropsActivity = new SellerMyCropsActivity();
                sellerMyCropsActivity.finish();

                SellerEditCropActivity sellerEditCropActivity = new SellerEditCropActivity();
                sellerEditCropActivity.finish();

                AboutActivity aboutActivity = new AboutActivity();
                aboutActivity.finish();

                ContactActivity contactActivity = new ContactActivity();
                contactActivity.finish();
            }
        });

        sellerProfileMyCrops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SellerMyCropsActivity.class);
                intent.putExtra("id", id);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}