package com.example.cropprice.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
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
import com.example.cropprice.SellerHomeActivity;
import com.example.cropprice.SingleCropActivity;
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
    NestedScrollView shopScrollView;
    LinearLayout shopNoCropFound;
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    ShimmerFrameLayout shimmerLayout;
    String newCropUrl = "https://crop-price-web.000webhostapp.com/seller.php?shopAuctions=true";
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        shopCropRcView = view.findViewById(R.id.shopCropRcView);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);
        shopScrollView = view.findViewById(R.id.shopScrollView);
        shopNoCropFound = view.findViewById(R.id.shopNoCropFound);

        // shimmer start
        shimmerLayout.startShimmer();

        // set the toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Shop");

        // new crops work
        HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, getActivity().getApplicationContext());
        shopCropRcView.setAdapter(newCropAdapter);
        GridLayoutManager newCropLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        shopCropRcView.setLayoutManager(newCropLayoutManager);
        shopCropRcView.setNestedScrollingEnabled(false);
        shopCropRcView.addOnItemTouchListener(new RecyclerItemClickListener
                (getActivity().getApplicationContext(), shopCropRcView, new RecyclerItemClickListener.OnItemClickListener() {
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
                        if(jsonArray.length() > 0) {
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
                            shopCropRcView.setVisibility(View.VISIBLE);
                        }else{
                            shopNoCropFound.setVisibility(View.VISIBLE);
                            shopScrollView.setVisibility(View.GONE);
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
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

        return view;
    }
}