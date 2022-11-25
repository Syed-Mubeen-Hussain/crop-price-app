package com.example.cropprice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropprice.Adapters.SingleCropAdapter;
import com.example.cropprice.Modals.SingleCropModel;

import java.util.ArrayList;

public class SingleCropActivity extends AppCompatActivity {

    ImageView singleCropImage;
    TextView singleCropName, singleCropPrice, singleCropDescription, singleCropEndingTime;
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
        singleCropEndingTime = findViewById(R.id.singleCropEndingTime);
        singleCropEnterBid = findViewById(R.id.singleCropEnterBid);
        singleCropSubmitBid = findViewById(R.id.singleCropSubmitBid);
        singleCropBidderRcView = findViewById(R.id.singleCropBidderRcView);

        // add the item in bidder list
        bidderList.add(new SingleCropModel("Bidder 1","1500","https://cdn-icons-png.flaticon.com/512/2899/2899899.png","10:12:15"));
        bidderList.add(new SingleCropModel("Bidder 2","1600","https://cdn-icons-png.flaticon.com/512/2899/2899899.png","10:12:15"));
        bidderList.add(new SingleCropModel("Bidder 3","1700","https://cdn-icons-png.flaticon.com/512/2899/2899899.png","10:12:15"));
        bidderList.add(new SingleCropModel("Bidder 4","1800","https://cdn-icons-png.flaticon.com/512/2899/2899899.png","10:12:15"));
        bidderList.add(new SingleCropModel("Bidder 5","1900","https://cdn-icons-png.flaticon.com/512/2899/2899899.png","10:12:15"));
        bidderList.add(new SingleCropModel("Bidder 6","2000","https://cdn-icons-png.flaticon.com/512/2899/2899899.png","10:12:15"));
        SingleCropAdapter adapter = new SingleCropAdapter(bidderList, SingleCropActivity.this);
        singleCropBidderRcView.setAdapter(adapter);
        singleCropBidderRcView.setLayoutManager(new LinearLayoutManager(this));
        singleCropBidderRcView.setNestedScrollingEnabled(false);
    }
}