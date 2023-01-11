package com.example.cropprice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.cropprice.Adapters.HomeNewCropAdapter;
import com.example.cropprice.Classes.RecyclerItemClickListener;
import com.example.cropprice.Modals.HomeCropModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SellerMyCropsActivity extends AppCompatActivity {

    Toolbar toolbar;
    NestedScrollView categoryWiseCropScrollView;
    LinearLayout sellerMyCropsNoCropFound;
    RecyclerView sellerMyCropsRcView;
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    ShimmerFrameLayout shimmerLayout;
    String CropUrl = "https://crop-price-web.000webhostapp.com/seller.php?sellerMyCrops=true";
    String id;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_my_crops);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // set the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));

        sellerMyCropsRcView = findViewById(R.id.sellerMyCropsRcView);
        categoryWiseCropScrollView = findViewById(R.id.categoryWiseCropScrollView);
        sellerMyCropsNoCropFound = findViewById(R.id.sellerMyCropsNoCropFound);
        shimmerLayout = findViewById(R.id.shimmerLayout);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("My Crops");

        id = getIntent().getStringExtra("id");

        // shimmer start
        shimmerLayout.startShimmer();

        // new crops work
        HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, SellerMyCropsActivity.this);
        GridLayoutManager newCropLayoutManager = new GridLayoutManager(SellerMyCropsActivity.this, 2);
        sellerMyCropsRcView.setLayoutManager(newCropLayoutManager);
        sellerMyCropsRcView.setNestedScrollingEnabled(false);
        sellerMyCropsRcView.setAdapter(newCropAdapter);
        sellerMyCropsRcView.addOnItemTouchListener(new RecyclerItemClickListener
                (SellerMyCropsActivity.this, sellerMyCropsRcView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        HomeCropModel model = newCropList.get(position);
                        Intent intent = new Intent(SellerMyCropsActivity.this, SellerEditCropActivity.class);
                        intent.putExtra("id", model.getAuction_id());
                        intent.putExtra("image", model.getImage());
                        intent.putExtra("name", model.getName());
                        intent.putExtra("qty", model.getQty());
                        intent.putExtra("price", model.getPrice());
                        intent.putExtra("description", model.getDescription());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                }
                ));

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
                            sellerMyCropsRcView.setVisibility(View.VISIBLE);
                        }else{
                            sellerMyCropsNoCropFound.setVisibility(View.VISIBLE);
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
                param.put("id", id);

                return param;
            }
        };

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(SellerMyCropsActivity.this);
            requestQueue.add(newCropRequest);
        } else {
            requestQueue.add(newCropRequest);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}