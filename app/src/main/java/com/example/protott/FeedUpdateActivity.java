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
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedUpdateActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int REQUEST_FROM_CAMERA = 0;
    private static final String TAG = "permission";

    String currentPicturePath;

    ImageButton btnFeedUpdatePhoto;
    ImageButton btnFeedUpdateCheck;
    private static Uri captureUri;  // 사진찍기
    private static Uri imageUri;
    private static Uri photoUri; // 앨범
    private static Uri albumUri;

    FirebaseStorage storage;

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_update);

        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        btnFeedUpdatePhoto = findViewById(R.id.btnFeedUpdatePhoto);
        btnFeedUpdateCheck = findViewById(R.id.btnFeedUpdateCheck);

        imageView = findViewById(R.id.imageView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) // 사진 권한 설정
        {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(FeedUpdateActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        btnFeedUpdateCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        btnFeedUpdatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeDialog();

            }


        });


    }

    public void uploadPicture()
    {

    }

    private void makeDialog() // 다이어 로그 만들기
    {
        AlertDialog.Builder dialog;

        DialogInterface.OnClickListener takePictureListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takePicture();
                Toast.makeText(FeedUpdateActivity.this, "사진찍기", Toast.LENGTH_SHORT).show();


            }
        };
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takeAlbum();
                Toast.makeText(FeedUpdateActivity.this, "앨범에서 선택하기", Toast.LENGTH_SHORT).show();
            }
        };
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(FeedUpdateActivity.this, "취소", Toast.LENGTH_SHORT).show();
            }
        };
        new AlertDialog.Builder(FeedUpdateActivity.this)
                .setTitle("선택")
                .setPositiveButton("사진찍기", takePictureListener)
                .setNeutralButton("취소", cancelListener)
                .setNegativeButton("앨범가기", albumListener)
                .show();
    }

    public void takePicture() // 사진찍기
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.protott", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_FROM_CAMERA);
            }


        }


    }


    public File createImageFile() throws IOException {


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        System.out.println(image + "dsagwermgoerjgoemngo");
        System.out.println(currentPicturePath + " dldldldldl");


        currentPicturePath = image.getAbsolutePath();
        return image;

    }


    public void takeAlbum() // 앨범에서 이미지 가져오기

    {


        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        intent.setType("image/*");

        startActivityForResult(intent, PICK_FROM_ALBUM);

    }


    public void galleryAddPic() {

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        File galleryFile = new File(currentPicturePath);

        Uri contentUri = Uri.fromFile(galleryFile);

        intent.setData(contentUri);

        sendBroadcast(intent);

        Toast.makeText(this, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show();

    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {

            return;
        }

        switch (requestCode) {

            case REQUEST_FROM_CAMERA: {

                if (resultCode == RESULT_OK) {
                    File file = new File(currentPicturePath);
                    Bitmap bitmap;
                    if (Build.VERSION.SDK_INT >= 29) {
                        ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                        try {
                            bitmap = ImageDecoder.decodeBitmap(source);
                            if (bitmap != null) {
                                btnFeedUpdatePhoto.setImageBitmap(bitmap);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                            if (bitmap != null) {
                                btnFeedUpdatePhoto.setImageBitmap(bitmap);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;
            }

            case PICK_FROM_ALBUM:
                {
                    if(data.getData()!=null){

                        try{

                            File albumFile = null;

                            albumFile = createImageFile();


                            photoUri = data.getData();

                            albumUri = Uri.fromFile(albumFile);


                            galleryAddPic();

                            btnFeedUpdatePhoto.setImageURI(photoUri);

                            //cropImage();

                        }catch (Exception e){

                            e.printStackTrace();

                            Log.v("알림","앨범에서 가져오기 에러");

                        }

                    }
                    break;
                }
        }

    }

}










