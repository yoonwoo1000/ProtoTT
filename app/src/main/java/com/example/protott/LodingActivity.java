package com.example.protott;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LodingActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);


            }
        },3000);


    }
}

