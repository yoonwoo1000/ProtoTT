package com.example.protott.navigation;

import android.net.Uri;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Frag2 extends Fragment {

    private RecyclerView recyclerView;
    private FeedMain1Adapter adapter;
    private FirebaseFirestore db;
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference = firebaseStorage.getReference();


    private ArrayList<ContentDTO> contentDTOS = new ArrayList<>();
    private ArrayList<String> uriList = new ArrayList<>();
    ContentDTO contentDTO;



    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_feed_main1, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.feedmain1fragment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        db = FirebaseFirestore.getInstance();


        db.collection("images").orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {


                Log.d(TAG,"uriuriuriuriuriuri" + uriList.toString());


                contentDTOS = (ArrayList<ContentDTO>) value.toObjects(ContentDTO.class);


            }
        });


        adapter = new FeedMain1Adapter(contentDTOS);


        recyclerView.setAdapter(adapter);


        Log.e("Frag", "FeedMain1Adapter");

        return rootView;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.feedmain1fragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager((view.getContext())));


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



