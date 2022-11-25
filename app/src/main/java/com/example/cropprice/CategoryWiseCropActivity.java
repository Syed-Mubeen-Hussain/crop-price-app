package com.example.cropprice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cropprice.Adapters.HomeCategoryAdapter;
import com.example.cropprice.Adapters.HomeNewCropAdapter;
import com.example.cropprice.Modals.HomeCategoryModel;
import com.example.cropprice.Modals.HomeCropModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class CategoryWiseCropActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView categoryWiseCropRcView;
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    ShimmerFrameLayout shimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_crop);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // set the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));

        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Category Name");


        categoryWiseCropRcView = findViewById(R.id.categoryWiseCropRcView);
        shimmerLayout = findViewById(R.id.shimmerLayout);

        // new crops work
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 1", "1500", "THis is a description", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 2", "1600", "THis is a description", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 3", "1700", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 4", "1800", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 5", "1900", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 6", "2000", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 7", "2100", "THis is a description Long Decription ", "20"));
        newCropList.add(new HomeCropModel("https://cdn-icons-png.flaticon.com/512/2899/2899899.png", "Crop 8", "2200", "THis is a description Long Decription ", "20"));
        HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, getApplicationContext());
        categoryWiseCropRcView.setAdapter(newCropAdapter);
        GridLayoutManager newCropLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        categoryWiseCropRcView.setLayoutManager(newCropLayoutManager);
        categoryWiseCropRcView.setNestedScrollingEnabled(false);

        // shimmerLayout work
        shimmerLayout.startShimmer();
//        shimmerLayout.stopShimmer();
//        shimmerLayout.setVisibility(View.GONE);
//        categoryWiseCropRcView.setVisibility(View.VISIBLE);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}