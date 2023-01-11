package com.example.cropprice.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cropprice.Modals.HomeCategoryModel;
import com.example.cropprice.Modals.HomeCropModel;
import com.example.cropprice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeNewCropAdapter extends RecyclerView.Adapter<HomeNewCropAdapter.viewHolder>{

    ArrayList<HomeCropModel> list;
    Context context;

    public HomeNewCropAdapter(ArrayList<HomeCropModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_home_newcrop_row,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        HomeCropModel model = list.get(position);
        if(model.getName().length() > 15){
            holder.homeNewCropName.setText(model.getName().substring(0,15) + " ...");
        }else{
            holder.homeNewCropName.setText(model.getName());
        }
        if(model.getDescription().length() > 17){
            holder.homeNewCropDescription.setText(model.getDescription().substring(0,17) + " ...");
        }else{
            holder.homeNewCropDescription.setText(model.getDescription());
        }
        holder.homeNewCropPrice.setText(model.getPrice());
        holder.homeNewCropBids.setText(model.getBids());
        Glide.with(holder.homeNewCropImage).load(model.getImage()).placeholder(R.drawable.progress_animation).into(holder.homeNewCropImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView homeNewCropImage;
        TextView homeNewCropName, homeNewCropDescription, homeNewCropPrice, homeNewCropBids;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            homeNewCropImage = itemView.findViewById(R.id.homeNewCropImage);
            homeNewCropName = itemView.findViewById(R.id.homeNewCropName);
            homeNewCropDescription = itemView.findViewById(R.id.homeNewCropDescription);
            homeNewCropPrice = itemView.findViewById(R.id.homeNewCropPrice);
            homeNewCropBids = itemView.findViewById(R.id.homeNewCropBids);
        }
    }

}
