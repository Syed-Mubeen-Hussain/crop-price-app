package com.example.cropprice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cropprice.Adapters.SingleCropAdapter;
import com.example.cropprice.Modals.SingleCropModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
 import java.time.LocalDateTime;
 import java.time.ZoneOffset;
 import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class SingleCropActivity extends AppCompatActivity {

    ImageView singleCropImage;
    TextView singleCropName, singleCropPrice, singleCropDescription, singleCropEndingTime, singleCropQty, singleCropTotalBids;
    EditText singleCropEnterBid;
    ImageButton singleCropSubmitBid;
    RecyclerView singleCropBidderRcView;
    ArrayList<SingleCropModel> bidderList = new ArrayList<>();

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
        singleCropEndingTime = findViewById(R.id.singleCropEndingTime);
        singleCropEnterBid = findViewById(R.id.singleCropEnterBid);
        singleCropSubmitBid = findViewById(R.id.singleCropSubmitBid);
        singleCropBidderRcView = findViewById(R.id.singleCropBidderRcView);

        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String description = getIntent().getStringExtra("description");
        String qty = getIntent().getStringExtra("qty");
        String image = getIntent().getStringExtra("image");
        String ending_time = getIntent().getStringExtra("ending_time");
        String bid_count = getIntent().getStringExtra("bid_count");

        Glide.with(getApplicationContext()).load(image).placeholder(R.drawable.loading).into(singleCropImage);
        singleCropName.setText(name);
        singleCropPrice.setText(price);
        singleCropDescription.setText(description);
        singleCropQty.setText(qty);
        singleCropEndingTime.setText(ending_time);
        singleCropTotalBids.setText(bid_count);

        SingleCropAdapter adapter = new SingleCropAdapter(bidderList, SingleCropActivity.this);
        singleCropBidderRcView.setAdapter(adapter);
        singleCropBidderRcView.setLayoutManager(new LinearLayoutManager(this));
        singleCropBidderRcView.setNestedScrollingEnabled(false);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        Date date = new GregorianCalendar(TimeZone.getTimeZone(ending_time)).getTime();

        Log.d("DateString", "Date is : "+date);

//        Long millisecond = Long.parseLong(date);
//
//        new CountDownTimer(millisecond, 1000) {
//            public void onTick(long millisUntilFinished) {
//                singleCropEndingTime.setText("seconds remaining: " + millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
//                singleCropEndingTime.setText("done!");
//            }
//        }.start();

    }
}