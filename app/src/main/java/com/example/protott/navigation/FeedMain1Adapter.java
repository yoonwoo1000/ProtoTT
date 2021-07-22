package com.example.protott.navigation;

import android.net.Uri;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FeedMain1Adapter extends RecyclerView.Adapter<FeedMain1Adapter.CustomViewHolder> {

    private List<ContentDTO> contentDTOS = new ArrayList<ContentDTO>();
    private List<String> contentUidList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference = firebaseStorage.getReference();
    private static Uri storageUri;

    private int position;

    public FeedMain1Adapter(ArrayList<ContentDTO> contentDTOS1) {
        this.contentDTOS = contentDTOS1;
    }


    @NonNull
    @Override

    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_feed_main1, parent, false);


        return new CustomViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return contentDTOS.size();
        //return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFeedPicture;
        TextView tvUserName;
        TextView tvLocation;
        TextView tvDate;
        TextView tvPictureMemo;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivFeedPicture = itemView.findViewById(R.id.ivFeedPicture);
            this.tvUserName = itemView.findViewById(R.id.tvUserName);
            this.tvLocation = itemView.findViewById(R.id.tvLocation);
            this.tvDate = itemView.findViewById(R.id.tvDate);
            this.tvPictureMemo = itemView.findViewById(R.id.tvPictureMemo);


        }

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {


        storageReference.child(contentDTOS.get(position).getImageUrl().toString().trim())
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG,"TASK UriTASK UriTASK UriTASK Uri" + storageUri.toString());

                            Glide.with(holder.itemView)
                                    .load(storageUri)
                                    .centerCrop()
                                    .into(holder.ivFeedPicture);
                        }
                        else {

                        }

                    }
                });


       /* Glide.with(holder.itemView)
                .load(String.valueOf(contentDTOS.get(position).getImageUrl()))
                .into(holder.ivFeedPicture);*/

        holder.tvUserName.setText(contentDTOS.get(position).getUserid());

        holder.tvDate.setText(contentDTOS.get(position).getTakenDate());
        holder.tvPictureMemo.setText(contentDTOS.get(position).getExplain());
    }




}