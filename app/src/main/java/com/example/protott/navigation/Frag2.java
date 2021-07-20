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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class Frag2 extends Fragment {

    private RecyclerView recyclerView;
    private FeedMain1Adapter adapter;
    private FirebaseFirestore db;

    void DocSnippets(FirebaseFirestore db) {
        this.db = db;
    }

    Boolean isFabOpen = true;

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_feed_main1, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.feedmain1fragment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FeedMain1Adapter();
        db = FirebaseFirestore.getInstance();
        ArrayList<ContentDTO> contentDTOS = new ArrayList<ContentDTO>();

        db.collection("images")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + document.getData() + "|||||" + document.get("imageUrl"));

                            }
                        } else {
                            Log.d(TAG, "Error getting documents : ", task.getException());
                        }

                    }
                });

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



