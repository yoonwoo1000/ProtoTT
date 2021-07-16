package com.example.protott.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.protott.R;
import com.google.firebase.firestore.FirebaseFirestore;


public class Frag2 extends Fragment {

    private View view;


    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_feed_main1, container, false);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        ;

        return view;
    }

}



