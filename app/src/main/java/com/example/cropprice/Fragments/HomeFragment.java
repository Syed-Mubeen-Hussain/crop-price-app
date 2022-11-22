package com.example.cropprice.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cropprice.Adapters.HomeCategoryAdapter;
import com.example.cropprice.Adapters.HomeNewCropAdapter;
import com.example.cropprice.ChooserActivity;
import com.example.cropprice.MainActivity;
import com.example.cropprice.Modals.HomeCategoryModel;
import com.example.cropprice.Modals.HomeCropModel;
import com.example.cropprice.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView homeCategoryRcView;
    RecyclerView homeNewCropRcView;
    ArrayList<HomeCategoryModel> list = new ArrayList<>();
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    ShimmerFrameLayout shimmerLayout, shimmerLayoutCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeCategoryRcView = view.findViewById(R.id.homeCategoryRcView);
        homeNewCropRcView = view.findViewById(R.id.homeNewCropRcView);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);
        shimmerLayoutCategory = view.findViewById(R.id.shimmerLayoutCategory);

        // image slider work
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.banner1, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.banner2, ScaleTypes.CENTER_CROP));
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP);

        // category work
        list.add(new HomeCategoryModel("https://cdn-icons-png.flaticon.com/512/3944/3944289.png", "Category1"));
        list.add(new HomeCategoryModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Category2"));
        list.add(new HomeCategoryModel("https://cdn-icons-png.flaticon.com/128/1135/1135722.png", "Category3"));
        list.add(new HomeCategoryModel("https://cdn-icons-png.flaticon.com/128/2674/2674638.png", "Category4"));
        list.add(new HomeCategoryModel("https://cdn-icons-png.flaticon.com/512/3658/3658881.png", "Category5"));
        list.add(new HomeCategoryModel("https://cdn-icons-png.flaticon.com/512/2921/2921726.png", "Category6"));
        HomeCategoryAdapter adapter = new HomeCategoryAdapter(list, getContext());
        homeCategoryRcView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        homeCategoryRcView.setLayoutManager(layoutManager);

        // new crops work
        // category work
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 1", "1500", "THis is a description", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 2", "1600", "THis is a description", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 3", "1700", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 4", "1800", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 5", "1900", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 6", "2000", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 7", "2100", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 8", "2200", "THis is a description Long Decription ", "20"));
        HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, getContext());
        homeNewCropRcView.setAdapter(newCropAdapter);
        GridLayoutManager newCropLayoutManager = new GridLayoutManager(getContext(), 2);
        homeNewCropRcView.setLayoutManager(newCropLayoutManager);
        homeNewCropRcView.setNestedScrollingEnabled(false);

        // shimmerLayout work
        shimmerLayout.startShimmer();
        shimmerLayoutCategory.startShimmer();
//        shimmerLayout.stopShimmer();
//        shimmerLayout.setVisibility(View.GONE);
//        homeNewCropRcView.setVisibility(View.VISIBLE);

        return view;
    }
}