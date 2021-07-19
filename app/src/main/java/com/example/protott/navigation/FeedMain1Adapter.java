package com.example.protott.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.protott.R;
import com.example.protott.model.ContentDTO;

import java.util.ArrayList;

public class FeedMain1Adapter extends RecyclerView.Adapter<FeedMain1Adapter.CustomViewHolder> {

    private ArrayList<ContentDTO> arrayList;
    private Context context;


    public FeedMain1Adapter(ArrayList<ContentDTO> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    //실제 리스트뷰가 어댑터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_main1, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImageUrl())
                .into(holder.ivFeedPicture);
        holder.tvUserName.setText(arrayList.get(position).getUserid());
        holder.tvLocation.setText(arrayList.get(position).getLatitude());
        holder.tvDate.setText(arrayList.get(position).getTakenDate());
        holder.tvPictireMemo.setText(String.valueOf(arrayList.get(position).getExplain()));
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFeedPicture;
        TextView tvUserName;
        TextView tvLocation;
        TextView tvDate;
        TextView tvPictireMemo;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivFeedPicture = itemView.findViewById(R.id.ivFeedPicture);
            this.tvUserName = itemView.findViewById(R.id.tvUserName);
            this.tvLocation = itemView.findViewById(R.id.tvLocation);
            this.tvDate = itemView.findViewById(R.id.tvDate);
            this.tvPictireMemo = itemView.findViewById(R.id.tvPictureMemo);
        }
    }
}