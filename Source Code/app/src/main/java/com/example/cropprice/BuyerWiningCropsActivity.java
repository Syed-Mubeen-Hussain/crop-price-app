package com.example.cropprice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BuyerWiningCropsActivity extends AppCompatActivity {

    Toolbar toolbar;
    NestedScrollView buyerWiningCropScrollView;
    LinearLayout BuyerWiningNoCropFound;
    RecyclerView BuyerWiningRcView;
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    ShimmerFrameLayout shimmerLayout;
    String CropUrl = "https://crop-price-web.000webhostapp.com/seller.php?buyerWiningCrops=true";
    String id, seller_contact;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_wining_crops);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // set the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));

        BuyerWiningRcView = findViewById(R.id.BuyerWiningRcView);
        buyerWiningCropScrollView = findViewById(R.id.buyerWiningCropScrollView);
        BuyerWiningNoCropFound = findViewById(R.id.BuyerWiningNoCropFound);
        shimmerLayout = findViewById(R.id.shimmerLayout);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Wining Crops");

        id = getIntent().getStringExtra("id");
        // shimmer start
        shimmerLayout.startShimmer();

        // new crops work
        HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, BuyerWiningCropsActivity.this);
        GridLayoutManager newCropLayoutManager = new GridLayoutManager(BuyerWiningCropsActivity.this, 2);
        BuyerWiningRcView.setLayoutManager(newCropLayoutManager);
        BuyerWiningRcView.setNestedScrollingEnabled(false);
        BuyerWiningRcView.setAdapter(newCropAdapter);
        BuyerWiningRcView.addOnItemTouchListener(new RecyclerItemClickListener
                (getApplicationContext(), BuyerWiningRcView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        SweetAlertDialog pDialog = new SweetAlertDialog(BuyerWiningCropsActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("Contact Seller");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    sleep(3000);
                                    pDialog.dismiss();

                                    Dexter.withContext(BuyerWiningCropsActivity.this)
                                            .withPermission(Manifest.permission.CALL_PHONE)
                                            .withListener(new PermissionListener() {
                                                @Override
                                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + seller_contact));
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                                }

                                                @Override
                                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                                    permissionToken.continuePermissionRequest();
                                                }
                                            }).check();

                                } catch (Exception e) {
                                    Toast.makeText(BuyerWiningCropsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        thread.start();
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

                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String name = object.getString("name");
                                String image = object.getString("image");
                                String price = object.getString("price");
                                String description = object.getString("description");
                                String bid_count = object.getString("bid_count");
                                seller_contact = object.getString("seller");
                                HomeCropModel crop = new HomeCropModel(image, name, price, description, bid_count);
                                newCropList.add(crop);
                                newCropAdapter.notifyDataSetChanged();
                            }
                            shimmerLayout.stopShimmer();
                            shimmerLayout.setVisibility(View.GONE);
                            BuyerWiningRcView.setVisibility(View.VISIBLE);
                        } else {
                            BuyerWiningNoCropFound.setVisibility(View.VISIBLE);
                            buyerWiningCropScrollView.setVisibility(View.GONE);
                        }
                    } else {
                        BuyerWiningNoCropFound.setVisibility(View.VISIBLE);
                        buyerWiningCropScrollView.setVisibility(View.GONE);
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
                param.put("buyer_id", id);

                return param;
            }
        };

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(BuyerWiningCropsActivity.this);
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