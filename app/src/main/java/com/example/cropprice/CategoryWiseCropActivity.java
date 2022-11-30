package com.example.cropprice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.example.cropprice.Fragments.SearchFragment;
import com.example.cropprice.Modals.HomeCategoryModel;
import com.example.cropprice.Modals.HomeCropModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryWiseCropActivity extends AppCompatActivity {

    Toolbar toolbar;
    NestedScrollView categoryWiseCropScrollView;
    LinearLayout categoryWiseNoCropFound;
    RecyclerView categoryWiseCropRcView;
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    ShimmerFrameLayout shimmerLayout;
    String CropUrl = "http://crop-price.infinityfreeapp.com/seller.php?categoryWiseAuctions=true";

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

        categoryWiseCropRcView = findViewById(R.id.categoryWiseCropRcView);
        categoryWiseCropScrollView = findViewById(R.id.categoryWiseCropScrollView);
        categoryWiseNoCropFound = findViewById(R.id.categoryWiseNoCropFound);
        shimmerLayout = findViewById(R.id.shimmerLayout);

        String id = getIntent().getStringExtra("categoryId").toString();
        String categoryName = getIntent().getStringExtra("categoryName").toString();

        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText(categoryName);

        // shimmer start
        shimmerLayout.startShimmer();

        // new crops work
        HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, getApplicationContext());
        categoryWiseCropRcView.setAdapter(newCropAdapter);
        GridLayoutManager newCropLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        categoryWiseCropRcView.setLayoutManager(newCropLayoutManager);
        categoryWiseCropRcView.setNestedScrollingEnabled(false);

        StringRequest newCropRequest = new StringRequest(Request.Method.POST, CropUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {

                        if(jsonArray.length() > 0){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String name = object.getString("name");
                                String image = object.getString("image");
                                String price = object.getString("price");
                                String qty = object.getString("qty");
                                String description = object.getString("description");
                                String ending_time = object.getString("ending_time");
                                String bid_count = object.getString("bid_count");

                                HomeCropModel crop = new HomeCropModel(image, name, price, description, bid_count, qty, ending_time);
                                newCropList.add(crop);
                                newCropAdapter.notifyDataSetChanged();
                            }
                            shimmerLayout.stopShimmer();
                            shimmerLayout.setVisibility(View.GONE);
                            categoryWiseCropRcView.setVisibility(View.VISIBLE);
                        }else{
                            categoryWiseNoCropFound.setVisibility(View.VISIBLE);
                            categoryWiseCropScrollView.setVisibility(View.GONE);
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("categoryWiseAuctions", "true");
                param.put("id", id);

                return param;
            }
        };

        RequestQueue newCropRequestQueue = Volley.newRequestQueue(getApplicationContext());
        newCropRequestQueue.add(newCropRequest);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}