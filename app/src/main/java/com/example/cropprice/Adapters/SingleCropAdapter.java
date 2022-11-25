package com.example.cropprice.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cropprice.Modals.HomeCategoryModel;
import com.example.cropprice.Modals.SingleCropModel;
import com.example.cropprice.R;

import java.util.ArrayList;

public class SingleCropAdapter extends RecyclerView.Adapter<SingleCropAdapter.viewHolder>{

    ArrayList<SingleCropModel> list;
    Context context;

    public SingleCropAdapter(ArrayList<SingleCropModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_bidder,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SingleCropModel model = list.get(position);
        holder.bidderName.setText(model.getBidderName());
        holder.bidderPrice.setText(model.getBidderPrice());
        holder.bidderTime.setText(model.getBidderTime());
        Glide.with(holder.bidderImage.getContext()).load(model.getBidderImage()).into(holder.bidderImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView bidderImage;
        TextView bidderName, bidderPrice, bidderTime;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            bidderImage = itemView.findViewById(R.id.bidderImage);
            bidderName = itemView.findViewById(R.id.bidderName);
            bidderPrice = itemView.findViewById(R.id.bidderPrice);
            bidderTime = itemView.findViewById(R.id.bidderTime);
        }
    }
}
