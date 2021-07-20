package com.example.protott.navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protott.R;
import com.example.protott.model.ContentDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;


public class Frag2 extends Fragment {

    private RecyclerView recyclerView;
    private FeedMain1Adapter adapter;

    Boolean isFabOpen = true;

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_feed_main1, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.feedmain1fragment_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FeedMain1Adapter();

        adapter.addItem(new ContentDTO());


        recyclerView.setAdapter(adapter);








        Log.e("Frag", "FeedMain1Adapter");

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void onStop() {
        super.onStop();

    }


}



