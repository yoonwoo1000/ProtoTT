package com.example.protott.navigation;

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
import com.example.protott.R;
import com.example.protott.model.ContentDTO;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FeedMain1Adapter extends RecyclerView.Adapter<FeedMain1Adapter.CustomViewHolder> {

    private ArrayList<ContentDTO> contentDTOS = new ArrayList<ContentDTO>();


    @NonNull
    @Override

    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_feed_main1, parent, false);


        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        ContentDTO contentDTO = contentDTOS.get(position);

        Glide.with(holder.itemView)
                .load(contentDTOS.get(position).getImageUrl())
                .into(holder.ivFeedPicture);


        holder.tvUserName.setText(contentDTOS.get(position).getUserid());
        holder.tvLocation.setText(contentDTOS.get(position).getLatitude());
        holder.tvDate.setText(contentDTOS.get(position).getTakenDate());
        holder.tvPictureMemo.setText(String.valueOf(contentDTOS.get(position).getExplain()));


    }

    @Override
    public int getItemCount() {
            return contentDTOS.size();
        //return (arrayList != null ? arrayList.size() : 0);
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFeedPicture;
        TextView tvUserName;
        TextView tvLocation;
        TextView tvDate;
        TextView tvPictureMemo;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFeedPicture = itemView.findViewById(R.id.ivFeedPicture);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDate = itemView.findViewById(R.id.tvDate);
          tvPictureMemo = itemView.findViewById(R.id.tvPictureMemo);
        }
    }

    public void addItem(ContentDTO contentDTO)
    {
        contentDTOS.add(contentDTO);
    }
    public void setItems(ArrayList<ContentDTO> contentDTOS)
    {
        this.contentDTOS = contentDTOS;
    }

    public ContentDTO getItem(int position)
    {
        return contentDTOS.get(position);
    }
    public  void setItem(int position,ContentDTO contentDTO)
    {
        contentDTOS.set(position,contentDTO);
    }

}