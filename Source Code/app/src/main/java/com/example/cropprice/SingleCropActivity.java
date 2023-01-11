package com.example.cropprice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.bumptech.glide.Glide;
import com.example.cropprice.Adapters.SingleCropAdapter;
import com.example.cropprice.Modals.HomeCropModel;
import com.example.cropprice.Modals.SingleCropModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import cn.iwgang.countdownview.CountdownView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SingleCropActivity extends AppCompatActivity {

    ImageView singleCropImage;
    TextView singleCropName, singleCropPrice, singleCropDescription, singleCropQty, singleCropTotalBids, winingBidderName;
    EditText singleCropEnterBid;
    ImageButton singleCropSubmitBid;
    RecyclerView singleCropBidderRcView;
    ArrayList<SingleCropModel> bidderList = new ArrayList<>();
    CountdownView mCvCountdownView;
    LinearLayout singleCropProgressLinearLayout, singleCropNoBidderFound, bidLinearLayout, winingLinearLayout;
    String singleCropBidderUrl = "https://crop-price-web.000webhostapp.com/seller.php?singleCropBidder=true";
    String singleCropAddAuctionBid = "https://crop-price-web.000webhostapp.com/seller.php?addAuctionBid=true";
    String singleCropHighestBid = "https://crop-price-web.000webhostapp.com/seller.php?highestBid=true";
    String addFeedbackUrl = "https://crop-price-web.000webhostapp.com/seller.php?addFeedback=true";
    String id = "", buyer_id = "";
    SingleCropAdapter adapter;
    private RequestQueue requestQueue;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_crop);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        singleCropImage = findViewById(R.id.singleCropImage);
        singleCropName = findViewById(R.id.singleCropName);
        singleCropPrice = findViewById(R.id.singleCropPrice);
        singleCropDescription = findViewById(R.id.singleCropDescription);
        singleCropQty = findViewById(R.id.singleCropQty);
        singleCropTotalBids = findViewById(R.id.singleCropTotalBids);
        mCvCountdownView = findViewById(R.id.mycountdown);
        singleCropEnterBid = findViewById(R.id.singleCropEnterBid);
        singleCropSubmitBid = findViewById(R.id.singleCropSubmitBid);
        singleCropBidderRcView = findViewById(R.id.singleCropBidderRcView);
        singleCropProgressLinearLayout = findViewById(R.id.singleCropProgressLinearLayout);
        singleCropNoBidderFound = findViewById(R.id.singleCropNoBidderFound);
        bidLinearLayout = findViewById(R.id.bidLinearLayout);
        winingBidderName = findViewById(R.id.winingBidderName);
        winingLinearLayout = findViewById(R.id.winingLinearLayout);

        SharedPreferences sharedPreferences = getSharedPreferences("SellerLoginSharedPreference", MODE_PRIVATE);
        if(!sharedPreferences.getString("id","").equals("")){
            bidLinearLayout.setVisibility(View.GONE);
        }

       SharedPreferences buyerSharedPreferences = getSharedPreferences("BuyerLoginSharedPreference", MODE_PRIVATE);
        if (!buyerSharedPreferences.getString("id", "").equals("")) {
            buyer_id = buyerSharedPreferences.getString("id", "");
            bidLinearLayout.setVisibility(View.VISIBLE);
        }

        id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String description = getIntent().getStringExtra("description");
        String qty = getIntent().getStringExtra("qty");
        String image = getIntent().getStringExtra("image");
        String ending_time = getIntent().getStringExtra("ending_time");
        String bid_count = getIntent().getStringExtra("bid_count");
        Glide.with(this).load(image).placeholder(R.drawable.loading).into(singleCropImage);
        singleCropName.setText(name);
        singleCropPrice.setText(price);
        singleCropDescription.setText(description);
        singleCropQty.setText(qty);
        singleCropTotalBids.setText(bid_count);

        String givenDateString = ending_time;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        try {
            Date mDate = dateFormat.parse(givenDateString);
            long timeInMilliseconds = mDate.getTime();
            long secs = TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds);
            timer = new CountDownTimer((secs - 1) * 1000, 3000) {
                @Override
                public final void onTick(final long millisUntilFinished) {
                    loadBiddersFunction();
                }

                @Override
                public final void onFinish() {
                    timer.cancel();
                }
            }.start();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // this is timer code
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        try {
            Date date = sdf.parse(ending_time);
            long countDownToNewYear = date.getTime() - now.getTime();
            if (now.getTime() > date.getTime()) {
                timer.cancel();
                mCvCountdownView.setVisibility(View.GONE);
                bidLinearLayout.setVisibility(View.GONE);
                StringRequest bidderRequest = new StringRequest(Request.Method.POST, singleCropHighestBid, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.d("jsonArray", String.valueOf(jsonArray.length()));
                            if (success.equals("1")) {
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String name = object.getString("name");
                                        if (!name.equals("null")) {
                                            winingLinearLayout.setVisibility(View.VISIBLE);
                                            winingBidderName.setText(name);
                                        }
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
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
                        param.put("auction_id", id);

                        return param;
                    }
                };

                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(SingleCropActivity.this);
                    requestQueue.add(bidderRequest);
                } else {
                    requestQueue.add(bidderRequest);
                }
            } else {
                mCvCountdownView.start(countDownToNewYear);
                mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                    @Override
                    public void onEnd(CountdownView cv) {
                        timer.cancel();
                        Toast.makeText(SingleCropActivity.this, "Time Up", Toast.LENGTH_SHORT).show();
                        mCvCountdownView.setVisibility(View.GONE);
                        bidLinearLayout.setVisibility(View.GONE);
                        StringRequest bidderRequest = new StringRequest(Request.Method.POST, singleCropHighestBid, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    Log.d("jsonArray", String.valueOf(jsonArray.length()));
                                    if (success.equals("1")) {
                                        if (jsonArray.length() > 0) {
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                String buyer_ID = object.getString("id");
                                                String name = object.getString("name");
                                                winingLinearLayout.setVisibility(View.VISIBLE);
                                                winingBidderName.setText(name);
                                                if (buyer_ID.equals(buyer_id)) {
                                                    // feedback work
                                                    View view = LayoutInflater.from(SingleCropActivity.this).inflate(R.layout.sample_alert_dialog_with_input, null);
                                                    AlertDialog alertDialog = new AlertDialog.Builder(SingleCropActivity.this).create();

                                                    alertDialog.setCancelable(false);

                                                    final EditText alert_comment = view.findViewById(R.id.alert_dialog_comment);
                                                    final Button alert_button = view.findViewById(R.id.alert_dialog_button);

                                                    alert_button.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            if (!alert_comment.getText().toString().trim().equals("")) {
                                                                alertDialog.dismiss();
                                                                SweetAlertDialog pDialog = new SweetAlertDialog(SingleCropActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                                                pDialog.setTitleText("Loading");
                                                                pDialog.setCancelable(false);
                                                                pDialog.show();
                                                                StringRequest request = new StringRequest(Request.Method.POST, addFeedbackUrl, new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        if (response.equals("success")) {
                                                                            pDialog.dismiss();
                                                                            Toast.makeText(getApplicationContext(), "Feedback Has Been Submitted", Toast.LENGTH_LONG).show();

                                                                        } else {
                                                                            pDialog.dismiss();
                                                                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                }, new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                                                        pDialog.dismiss();
                                                                    }
                                                                }) {
                                                                    @Nullable
                                                                    @Override
                                                                    protected Map<String, String> getParams() throws AuthFailureError {

                                                                        Map<String, String> param = new HashMap<>();
                                                                        param.put("fk_buyer_id", buyer_id);
                                                                        param.put("fk_auction_id", id);
                                                                        param.put("message", alert_comment.getText().toString());

                                                                        return param;
                                                                    }
                                                                };

                                                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                                requestQueue.add(request);
                                                            }
                                                        }
                                                    });

                                                    alertDialog.setView(view);
                                                    alertDialog.show();
                                                }
                                            }
                                        }
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(SingleCropActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SingleCropActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> param = new HashMap<>();
                                param.put("auction_id", id);

                                return param;
                            }
                        };

                        if (requestQueue == null) {
                            requestQueue = Volley.newRequestQueue(SingleCropActivity.this);
                            requestQueue.add(bidderRequest);
                        } else {
                            requestQueue.add(bidderRequest);
                        }
                    }
                });
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // show bidders data
        adapter = new SingleCropAdapter(bidderList, SingleCropActivity.this);
        singleCropBidderRcView.setAdapter(adapter);
        singleCropBidderRcView.setLayoutManager(new LinearLayoutManager(this));
        singleCropBidderRcView.setNestedScrollingEnabled(false);
        loadBidders();

        // add bid code
        singleCropSubmitBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bid_amount_text = singleCropEnterBid.getText().toString();
                if (!bid_amount_text.trim().equals("")) {
                    long bid_amount = Long.parseLong(bid_amount_text);
                    if (bid_amount > 0) {
                        SweetAlertDialog pDialog = new SweetAlertDialog(SingleCropActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText("Loading");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        singleCropNoBidderFound.setVisibility(View.GONE);
                        StringRequest request = new StringRequest(Request.Method.POST, singleCropAddAuctionBid, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    Toast.makeText(SingleCropActivity.this, "Bid add successfully", Toast.LENGTH_SHORT).show();
                                    singleCropEnterBid.setText("");
                                    pDialog.dismiss();
                                    loadBidders();
                                } else if (response.equals("no")) {
                                    pDialog.dismiss();
                                    Toast.makeText(SingleCropActivity.this, "Please enter highest bid", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SingleCropActivity.this, "Bid not add", Toast.LENGTH_SHORT).show();
                                    pDialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.dismiss();
                                Toast.makeText(SingleCropActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> param = new HashMap<>();
                                param.put("amount", String.valueOf(bid_amount));
                                param.put("fk_auction_id", id);
                                param.put("fk_buyer_id", buyer_id);

                                return param;
                            }
                        };

                        if (requestQueue == null) {
                            requestQueue = Volley.newRequestQueue(SingleCropActivity.this);
                            requestQueue.add(request);
                        } else {
                            requestQueue.add(request);
                        }
                    }
                }
            }
        });
    }

    public void loadBidders() {
        singleCropBidderRcView.setVisibility(View.GONE);
        singleCropProgressLinearLayout.setVisibility(View.VISIBLE);
        StringRequest bidderRequest = new StringRequest(Request.Method.POST, singleCropBidderUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        bidderList.clear();
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String added_on = object.getString("added_on");
                                String amount = object.getString("amount");
                                String name = object.getString("name");
                                String image = object.getString("image");

                                SingleCropModel crop = new SingleCropModel(name, amount, image, added_on);
                                bidderList.add(crop);
                                singleCropTotalBids.setText(Integer.toString(bidderList.size()));
                                adapter.notifyDataSetChanged();
                                singleCropProgressLinearLayout.setVisibility(View.GONE);
                                singleCropNoBidderFound.setVisibility(View.GONE);
                            }
                        } else {
                            singleCropNoBidderFound.setVisibility(View.VISIBLE);
                            singleCropBidderRcView.setVisibility(View.GONE);
                            singleCropProgressLinearLayout.setVisibility(View.GONE);
                        }
                    }
                    singleCropBidderRcView.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    Toast.makeText(SingleCropActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SingleCropActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("auction_id", id);

                return param;
            }
        };

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(SingleCropActivity.this);
            requestQueue.add(bidderRequest);
        } else {
            requestQueue.add(bidderRequest);
        }
    }

    public void loadBiddersFunction() {
        StringRequest bidderRequest = new StringRequest(Request.Method.POST, singleCropBidderUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        bidderList.clear();
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String added_on = object.getString("added_on");
                                String amount = object.getString("amount");
                                String name = object.getString("name");
                                String image = object.getString("image");

                                SingleCropModel crop = new SingleCropModel(name, amount, image, added_on);
                                bidderList.add(crop);
                                singleCropTotalBids.setText(Integer.toString(bidderList.size()));
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            singleCropNoBidderFound.setVisibility(View.VISIBLE);
                            singleCropBidderRcView.setVisibility(View.GONE);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
                param.put("auction_id", id);

                return param;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(bidderRequest);
        } else {
            requestQueue.add(bidderRequest);
        }
    }
}