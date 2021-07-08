package com.example.protott;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class FeedUpdateActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;


    ImageButton btnFeedUpdatePhoto;
    private Uri CaptureUri;
    FirebaseStorage storage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_update);

        storage = FirebaseStorage.getInstance();


        btnFeedUpdatePhoto = findViewById(R.id.btnFeedUpdatePhoto);

        btnFeedUpdatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog;

                DialogInterface.OnClickListener takePictureListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FeedUpdateActivity.this, "Take Picture", Toast.LENGTH_SHORT).show();


                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FeedUpdateActivity.this, "Album", Toast.LENGTH_SHORT).show();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FeedUpdateActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                };
                new AlertDialog.Builder(FeedUpdateActivity.this)
                        .setTitle("Select")
                        .setPositiveButton("Takepicture", takePictureListener)
                        .setNeutralButton("cancel", cancelListener)
                        .setNegativeButton("Album", albumListener)
                        .show();

            }
        });
    }









}





