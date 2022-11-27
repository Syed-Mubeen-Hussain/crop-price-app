package com.example.cropprice.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.cropprice.SellerHomeActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopFragment extends Fragment {

    Toolbar toolbar;
    RecyclerView shopCropRcView;
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    ShimmerFrameLayout shimmerLayout;
    String newCropUrl = "http://crop-price.infinityfreeapp.com/seller.php?shopAuctions=true";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        shopCropRcView = view.findViewById(R.id.shopCropRcView);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);

        // shimmer start
        shimmerLayout.startShimmer();

        // set the toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Shop");

        // new crops work
        HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, getContext());
        shopCropRcView.setAdapter(newCropAdapter);
        GridLayoutManager newCropLayoutManager = new GridLayoutManager(getContext(), 2);
        shopCropRcView.setLayoutManager(newCropLayoutManager);
        shopCropRcView.setNestedScrollingEnabled(false);

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
                        shopCropRcView.setVisibility(View.VISIBLE);
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

        return view;
    }
}