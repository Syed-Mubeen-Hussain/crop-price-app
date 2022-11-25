package com.example.cropprice.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cropprice.Adapters.HomeCategoryAdapter;
import com.example.cropprice.Adapters.HomeNewCropAdapter;
import com.example.cropprice.CategoryWiseCropActivity;
import com.example.cropprice.Modals.HomeCategoryModel;
import com.example.cropprice.Modals.HomeCropModel;
import com.example.cropprice.R;
import com.example.cropprice.SellerHomeActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ShopFragment extends Fragment {

    Toolbar toolbar;
    RecyclerView shopCropRcView;
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    ShimmerFrameLayout shimmerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        shopCropRcView = view.findViewById(R.id.shopCropRcView);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);

        // set the toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Shop");

        // new crops work
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 1", "1500", "THis is a description", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 2", "1600", "THis is a description", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 3", "1700", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 4", "1800", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 5", "1900", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 6", "2000", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 7", "2100", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 8", "2200", "THis is a description Long Decription ", "20"));
        HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, getContext());
        shopCropRcView.setAdapter(newCropAdapter);
        GridLayoutManager newCropLayoutManager = new GridLayoutManager(getContext(), 2);
        shopCropRcView.setLayoutManager(newCropLayoutManager);
        shopCropRcView.setNestedScrollingEnabled(false);

        // shimmerLayout work
        shimmerLayout.startShimmer();
//        shimmerLayout.stopShimmer();
//        shimmerLayout.setVisibility(View.GONE);
//        homeNewCropRcView.setVisibility(View.VISIBLE);

        return view;
    }
}