package com.example.protott;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class FeedActivity extends FragmentActivity {
    Button btnplay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.frag1);

        btnplay = findViewById(R.id.btnPlay);

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(FeedActivity.this, "dldldld", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
