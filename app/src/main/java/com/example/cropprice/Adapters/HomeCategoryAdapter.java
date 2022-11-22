package com.example.cropprice.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cropprice.Modals.HomeCategoryModel;
import com.example.cropprice.R;

import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.viewHolder>{

    ArrayList<HomeCategoryModel> list;
    Context context;

    public HomeCategoryAdapter(ArrayList<HomeCategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_home_category_row,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        HomeCategoryModel model = list.get(position);
        holder.homeCategoryName.setText(model.getName());
        Glide.with(context).load(model.getImage()).into(holder.homeCategoryImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView homeCategoryImage;
        TextView homeCategoryName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            homeCategoryImage = itemView.findViewById(R.id.homeCategoryImage);
            homeCategoryName = itemView.findViewById(R.id.homeCategoryName);
        }
    }

}
