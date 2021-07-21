package com.example.protott.navigation;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.lang.String.valueOf;

public class FeedMain1Adapter extends RecyclerView.Adapter<FeedMain1Adapter.CustomViewHolder> {

    private List<ContentDTO> contentDTOS = new ArrayList<ContentDTO>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    ContentDTO contentDTO = new ContentDTO();

    List<String> imageUriList = new ArrayList<>();


    @NonNull
    @Override

    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_feed_main1, parent, false);

        db = FirebaseFirestore.getInstance();



//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("images").orderBy("timestamp").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {


                        list.add(document.toString());

                        contentDTO.setImageUrl(document.get("imageUrl").toString());

                        contentDTO.setUid(document.get("uid").toString());

                        contentDTO.setExplain(document.get("explain").toString());

                        contentDTO.setUserid(document.get("userid").toString());



                        contentDTOS.add(contentDTO);




                        for (int i = 0; i< list.size(); i++)
                        {
                            Log.d(TAG,list.get(i));

                            Log.d(TAG,"dkdkdkdkdkdkdkdkdkk" + String.valueOf(contentDTOS.get(i)));



                        }

                        Log.d(TAG, list.toString());
                    }



                } else {
                    Log.d(TAG, "Error getting documents : ", task.getException());
                }

            }
        });



        return new CustomViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        for(int i = 0; i < contentDTOS.size(); i++) {

            ContentDTO contentDTO = contentDTOS.get(i);

            Glide.with(holder.itemView)
                    .load(contentDTOS.get(i).getImageUrl())
                    .into(holder.ivFeedPicture);

            holder.tvUserName.setText(contentDTOS.get(i).getUserid());
            holder.tvLocation.setText(contentDTOS.get(i).getLatitude());
            holder.tvDate.setText(contentDTOS.get(i).getTakenDate());
            holder.tvPictureMemo.setText(valueOf(contentDTOS.get(i).getExplain()));



        }


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

    public void addItem(ContentDTO contentDTO) {
        contentDTOS.add(contentDTO);
    }

    public void setItems(ArrayList<ContentDTO> contentDTOS) {
        this.contentDTOS = contentDTOS;
    }

    public ContentDTO getItem(int position) {
        return contentDTOS.get(position);
    }

    public void setItem(int position, ContentDTO contentDTO) {
        contentDTOS.set(position, contentDTO);
    }

}