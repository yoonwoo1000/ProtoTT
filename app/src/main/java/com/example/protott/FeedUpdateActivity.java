package com.example.protott;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedUpdateActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private static final String TAG = "permission";

    String currentPicturePath;

    private static final int REQUEST_TAKE_PHOTO = 1;

    ImageButton btnFeedUpdatePhoto;
    private static Uri captureUri;
    FirebaseStorage storage;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_update);

        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        btnFeedUpdatePhoto = findViewById(R.id.btnFeedUpdatePhoto);



        btnFeedUpdatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog;

                DialogInterface.OnClickListener takePictureListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        takePicture();
                        Toast.makeText(FeedUpdateActivity.this, "Take Picture", Toast.LENGTH_SHORT).show();


                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        takeAlbum();
                        Toast.makeText(FeedUpdateActivity.this, "Album", Toast.LENGTH_SHORT).show();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            { Log.d(TAG, "권한 설정 완료"); } else { Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(FeedUpdateActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1); }
        }

    }

    public void takePicture()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);



        if(intent.resolveActivity(getPackageManager()) !=null)
        {
            File photoFIle = null;
            try {
                photoFIle = createImageFile();
            }catch (IOException ex)
            {

            }

            if (photoFIle != null)
            {
                captureUri = FileProvider.getUriForFile(this,"com.example.protott",photoFIle);


            }

            intent.putExtra(MediaStore.EXTRA_OUTPUT, captureUri);

            startActivityForResult(intent,PICK_FROM_CAMERA);
        }


    }

    public void takeAlbum() // 앨범에서 이미지 가져오기

    {


        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);

        startActivityForResult(intent, PICK_FROM_ALBUM);

    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            btnFeedUpdatePhoto.setImageBitmap(imageBitmap);
        }


        if (resultCode != RESULT_OK)

            return;


        switch (requestCode) {

            case PICK_FROM_ALBUM: {
                captureUri = data.getData();
                Log.d("Camera", captureUri.getPath().toString());

            }


         /*   case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(CaptureUri, "image/*");



                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;

            }*/
            case CROP_FROM_IMAGE: {

                if (resultCode != RESULT_OK) {

                    return;

                }


                final Bundle extras = data.getExtras();


                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +

                        "/SmartWheel/" + System.currentTimeMillis() + ".jpg";


                if (extras != null) {

                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP

                    btnFeedUpdatePhoto.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌




                    currentPicturePath = filePath;

                    break;


                }

                // 임시 파일 삭제

                File file = new File(captureUri.getPath());

                if (file.exists()) {

                    file.delete();

                }

            }

        }










    }


    private File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);

        currentPicturePath = image.getAbsolutePath();




        return image;
    }
}









