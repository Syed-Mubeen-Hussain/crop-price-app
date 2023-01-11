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
import android.widget.LinearLayout;
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

import com.example.cropprice.Classes.RecyclerItemClickListener;
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
    LinearLayout homeNoCropFound, homeCategoryNoCropFound;
    ArrayList<HomeCategoryModel> categoryList = new ArrayList<>();
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    ShimmerFrameLayout shimmerLayout, shimmerLayoutCategory;
    String categoryUrl = "https://crop-price-web.000webhostapp.com/seller.php?categories=true";
    String newCropUrl = "https://crop-price-web.000webhostapp.com/seller.php?auctions=true";
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeCategoryRcView = view.findViewById(R.id.homeCategoryRcView);
        homeNewCropRcView = view.findViewById(R.id.homeNewCropRcView);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);
        tvViewAll = view.findViewById(R.id.tvViewAll);
        shimmerLayoutCategory = view.findViewById(R.id.shimmerLayoutCategory);
        homeNoCropFound = view.findViewById(R.id.homeNoCropFound);
        homeCategoryNoCropFound = view.findViewById(R.id.homeCategoryNoCropFound);
        toolbar = getActivity().findViewById(R.id.toolbar);

        // shimmer start
        shimmerLayoutCategory.startShimmer();
        shimmerLayout.startShimmer();

        // set the toolbar
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Crop Price");

        // image slider work
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.soil, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.perticides3, ScaleTypes.CENTER_CROP));
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP);

        // category work
        HomeCategoryAdapter adapter = new HomeCategoryAdapter(categoryList, getActivity().getApplicationContext());
        homeCategoryRcView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        homeCategoryRcView.setLayoutManager(layoutManager);
        homeCategoryRcView.addOnItemTouchListener(new RecyclerItemClickListener
                (getActivity().getApplicationContext(), homeCategoryRcView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        HomeCategoryModel model = categoryList.get(position);
                        Intent intent = new Intent(getActivity().getApplicationContext(), CategoryWiseCropActivity.class);
                        intent.putExtra("categoryId", model.getId());
                        intent.putExtra("categoryName", model.getName());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                }
                ));

        StringRequest request = new StringRequest(Request.Method.POST, categoryUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String id = object.getString("id");
                                String name = object.getString("name");
                                String image = object.getString("image");

                                HomeCategoryModel category = new HomeCategoryModel(id, image, name);
                                categoryList.add(category);
                                adapter.notifyDataSetChanged();

                                Log.d("category", category.getImage());
                            }
                            shimmerLayoutCategory.stopShimmer();
                            shimmerLayoutCategory.setVisibility(View.GONE);
                            homeCategoryRcView.setVisibility(View.VISIBLE);
                        }else{
                            shimmerLayoutCategory.stopShimmer();
                            shimmerLayoutCategory.setVisibility(View.GONE);
                            homeCategoryNoCropFound.setVisibility(View.VISIBLE);
                            homeCategoryRcView.setVisibility(View.GONE);
                        }

                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
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

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(request);
        } else {
            requestQueue.add(request);
        }

        // new crop work
        HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, getActivity().getApplicationContext());
        homeNewCropRcView.setAdapter(newCropAdapter);
        GridLayoutManager newCropLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        homeNewCropRcView.setLayoutManager(newCropLayoutManager);
        homeNewCropRcView.setNestedScrollingEnabled(false);
        homeNewCropRcView.addOnItemTouchListener(new RecyclerItemClickListener
                (getActivity().getApplicationContext(), homeNewCropRcView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        HomeCropModel model = newCropList.get(position);
                        Intent intent = new Intent(getActivity().getApplicationContext(), SingleCropActivity.class);
                        intent.putExtra("id", model.getAuction_id());
                        intent.putExtra("image", model.getImage());
                        intent.putExtra("name", model.getName());
                        intent.putExtra("qty", model.getQty());
                        intent.putExtra("bid_count", model.getBids());
                        intent.putExtra("price", model.getPrice());
                        intent.putExtra("description", model.getDescription());
                        intent.putExtra("ending_time", model.getEnding_time());
                        getActivity().startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                }
                ));

        StringRequest newCropRequest = new StringRequest(Request.Method.POST, newCropUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String auction_id = object.getString("id");
                                String name = object.getString("name");
                                String image = object.getString("image");
                                String price = object.getString("price");
                                String qty = object.getString("qty");
                                String description = object.getString("description");
                                String ending_time = object.getString("ending_time");
                                String bid_count = object.getString("bid_count");

                                HomeCropModel crop = new HomeCropModel(image, name, price, description, bid_count, qty, ending_time, auction_id);
                                newCropList.add(crop);

                                newCropAdapter.notifyDataSetChanged();
                            }
                            shimmerLayout.stopShimmer();
                            shimmerLayout.setVisibility(View.GONE);
                            homeNewCropRcView.setVisibility(View.VISIBLE);
                        } else {
                            shimmerLayout.stopShimmer();
                            shimmerLayout.setVisibility(View.GONE);
                            homeNoCropFound.setVisibility(View.VISIBLE);
                            homeNewCropRcView.setVisibility(View.GONE);
                        }

                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
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

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(newCropRequest);
        } else {
            requestQueue.add(newCropRequest);
        }


        tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ShopFragment()).commit();
            }
        });
        return view;
    }
}