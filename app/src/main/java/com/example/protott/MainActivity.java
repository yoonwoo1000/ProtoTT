package com.example.protott;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.protott.model.ContentDTO;
import com.example.protott.navigation.Frag1;
import com.example.protott.navigation.Frag2;
import com.example.protott.navigation.Frag3;
import com.example.protott.navigation.Frag4;
import com.example.protott.navigation.FeedMain1Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private Frag4 frag4;
    private FeedMain1Adapter feedMain1Adapter;
    ImageButton btnFeedUpdate;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFeedUpdate = findViewById(R.id.btnFeedUpdate);




        btnFeedUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeedUpdateActivity.class);
                startActivity(intent);
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_person:
                        setFrag(0);
                        break;
                    case R.id.action_addPhoto:
                        setFrag(1);
                        break;
                    case R.id.action_baccount:
                        setFrag(2);
                        break;
                    case R.id.action_caccount:
                        setFrag(3);
                        break;


                }

                return true;
            }
        });
        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();
        frag4 = new Frag4();
        setFrag(1);

    }


    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, frag4);
                ft.commit();
                break;


        }
    }

    public static ArrayList<ContentDTO> getAllFromFireStore()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<ContentDTO> contentDTOS = new ArrayList<>();

        db.collection("images")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String imageUrl = document.get("imageUrl") + "";
                                String takenDate = document.get("takenDate") + "";
                                String explain = document.get("explain") + "";
                                String timestamp = document.get("timestamp") + "";
                                String uid = document.get("uid") + "";
                                String userid = document.get("userid") + "";
                                String latitude = document.get("latitude") + "";
                                String longitude = document.get("longitude") + "";

                                // I would like to add these items to an array list and return that array list

                            }
                        }
                        else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return contentDTOS;



    }
}