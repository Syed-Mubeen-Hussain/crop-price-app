package com.example.cropprice.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cropprice.Adapters.HomeCategoryAdapter;
import com.example.cropprice.Adapters.HomeNewCropAdapter;
import com.example.cropprice.CategoryWiseCropActivity;

import com.example.cropprice.Modals.HomeCategoryModel;
import com.example.cropprice.Modals.HomeCropModel;
import com.example.cropprice.R;
import com.example.cropprice.SingleCropActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    Toolbar toolbar;
    TextView tvViewAll;
    RecyclerView homeCategoryRcView, homeNewCropRcView;
    ArrayList<HomeCategoryModel> categoryList = new ArrayList<>();
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    ShimmerFrameLayout shimmerLayout, shimmerLayoutCategory;
    String categoryUrl = "http://crop-price.infinityfreeapp.com/seller.php?categories=true";
    String newCropUrl = "http://crop-price.infinityfreeapp.com/seller.php?auctions=true";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeCategoryRcView = view.findViewById(R.id.homeCategoryRcView);
        homeNewCropRcView = view.findViewById(R.id.homeNewCropRcView);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);
        tvViewAll = view.findViewById(R.id.tvViewAll);
        shimmerLayoutCategory = view.findViewById(R.id.shimmerLayoutCategory);
        toolbar = getActivity().findViewById(R.id.toolbar);

        // shimmer start
        shimmerLayoutCategory.startShimmer();
        shimmerLayout.startShimmer();

        // set the toolbar
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Crop Price");

        // image slider work
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.banner1, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.banner2, ScaleTypes.CENTER_CROP));
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP);

        // category work
        HomeCategoryAdapter adapter = new HomeCategoryAdapter(categoryList, getContext());
        homeCategoryRcView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        homeCategoryRcView.setLayoutManager(layoutManager);

        StringRequest request = new StringRequest(Request.Method.POST, categoryUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String name = object.getString("name");
                            String image = object.getString("image");

                            HomeCategoryModel category = new HomeCategoryModel(image, name);
                            categoryList.add(category);
                            adapter.notifyDataSetChanged();

                            Log.d("category", category.getImage());
                        }
                        shimmerLayoutCategory.stopShimmer();
                        shimmerLayoutCategory.setVisibility(View.GONE);
                        homeCategoryRcView.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error is : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("categories", "true");

                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

        // new crop work
        HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, getContext());
        homeNewCropRcView.setAdapter(newCropAdapter);
        GridLayoutManager newCropLayoutManager = new GridLayoutManager(getContext(), 2);
        homeNewCropRcView.setLayoutManager(newCropLayoutManager);
        homeNewCropRcView.setNestedScrollingEnabled(false);

        StringRequest newCropRequest = new StringRequest(Request.Method.POST, newCropUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String name = object.getString("name");
                            String image = object.getString("image");
                            String price = object.getString("price");
                            String description = object.getString("description");
                            String bid_count = object.getString("bid_count");

                            HomeCropModel crop = new HomeCropModel(image, name, price, description, bid_count);
                            newCropList.add(crop);
                            newCropAdapter.notifyDataSetChanged();

                            Log.d("crop", crop.getImage());
                        }
                        shimmerLayout.stopShimmer();
                        shimmerLayout.setVisibility(View.GONE);
                        homeNewCropRcView.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error is : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("auctions", "true");

                return param;
            }
        };

        RequestQueue newCropRequestQueue = Volley.newRequestQueue(getContext());
        newCropRequestQueue.add(newCropRequest);



        tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ShopFragment()).commit();
            }
        });
        return view;
    }
}