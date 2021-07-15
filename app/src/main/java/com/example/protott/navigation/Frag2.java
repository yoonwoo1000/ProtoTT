package com.example.protott.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protott.R;
import com.example.protott.model.ContentDTO;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class Frag2 extends Fragment
{
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();;
    private View view;



    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_feed_main1, container, false);

        return view;
    }

    public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {

        ArrayList<ContentDTO> contentDTOS = new ArrayList<ContentDTO>();

        ArrayList<String> contentUidList = new ArrayList<String>();








        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
