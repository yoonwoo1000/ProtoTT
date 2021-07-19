package com.example.protott;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.protott.model.ContentDTO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.grpc.Metadata;


public class FeedUpdateActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int REQUEST_FROM_CAMERA = 0;
    private static final String TAG = "permission";

    String currentPicturePath;

    ImageButton btnFeedUpdatePhoto;
    ImageButton btnFeedUpdateCheck;
    EditText etMemo;

    TextView tvPlace, tvPictureDate;

    String latitude;
    String longitude;
    String takeDate;


    private static Uri contentUri;  // 사진찍기
    private static Uri imageUri;
    private static Uri photoUri; // 앨범
    private static Uri albumUri;

    FirebaseStorage storage;

    private FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        photoUri = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_update);

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        btnFeedUpdatePhoto = findViewById(R.id.btnFeedUpdatePhoto);
        btnFeedUpdateCheck = findViewById(R.id.btnFeedUpdateCheck);
        etMemo = findViewById(R.id.etMemo);

        tvPictureDate = findViewById(R.id.tvPictureDate);
        tvPlace = findViewById(R.id.tvPlace);


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


                uploadFeed();


            }
        });


        btnFeedUpdatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeDialog();

            }


        });


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
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_FROM_CAMERA);
            }


        }


    }

    public void uploadFeed() {
        if (photoUri != null) {


            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            String imageFileName = "JPEG_" + timeStamp + "_,png";


            StorageReference storageReference = storage.getReference().child("images").child(imageFileName);

            storageReference.putFile(photoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                @Override
                public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {



                    String uri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();


                    ContentDTO contentDTO = new ContentDTO();



                    contentDTO.setImageUrl(uri.toString());

                    contentDTO.setUid(auth.getCurrentUser().getUid());

                    contentDTO.setExplain(etMemo.getText().toString());

                    contentDTO.setUserid(auth.getCurrentUser().getEmail());


                    contentDTO.setTimestamp(System.currentTimeMillis());

                    contentDTO.setLatitude(latitude);

                    contentDTO.setLongitude(longitude);

                    contentDTO.setTakenDate(takeDate);

                    firestore.collection("images").document().set(contentDTO);

                    setResult(Activity.RESULT_OK);
                    finish();


                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FeedUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else if (contentUri != null) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            String imageFileName = "JPEG_" + timeStamp + "_,png";

            StorageReference storageReference = storage.getReference().child("images").child(imageFileName);
            storageReference.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                @Override
                public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    String uri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                    ContentDTO contentDTO = new ContentDTO();

                    contentDTO.setImageUrl(uri.toString());

                    contentDTO.setUid(auth.getCurrentUser().getUid());

                    contentDTO.setExplain(etMemo.getText().toString());

                    contentDTO.setUserid(auth.getCurrentUser().getEmail());

                    contentDTO.setTimestamp(System.currentTimeMillis());

                    contentDTO.setLatitude(latitude);

                    contentDTO.setLongitude(longitude);

                    contentDTO.setTakenDate(takeDate);

                    firestore.collection("images").document().set(contentDTO);

                    setResult(Activity.RESULT_OK);
                    finish();


                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FeedUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(this, "사진 필요", Toast.LENGTH_SHORT).show();
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


        System.out.println(image + "image");
        System.out.println(currentPicturePath + " currentPicPath");
        System.out.println(photoUri + "photoURI");


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

        contentUri = Uri.fromFile(galleryFile);

        intent.setData(contentUri);

        sendBroadcast(intent);

        Toast.makeText(this, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show();

    }

    public void getImageinfo() {
        Cursor mManagedCursor;
        ContentResolver contentResolver = getContentResolver();
        mManagedCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        if (mManagedCursor != null) {
            mManagedCursor.moveToFirst();
            int nSize = mManagedCursor.getColumnCount();

            while (true) {
                String result = "";

                String date_taken =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        MediaStore.Images.ImageColumns.DATE_TAKEN)); // 촬영날짜. 1/1000초 단위
                result += date_taken + "|";
                String latitude =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        MediaStore.Images.ImageColumns.LATITUDE)); // 위도
                result += latitude + "|";

                String longitude =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        MediaStore.Images.ImageColumns.LONGITUDE)); // 경도
                result += longitude + "|";

             /*   if(latitude != null && !latitude.equals("0") && !longitude.equals("0")) {
                    mTitleArray.add(title + " " + latitude + "," + longitude);

                    paths.add(data);
                    Log.d("TAG", result);
                }
                if (mManagedCursor.isLast())
                {
                    break;
                }
                else
                {
                    mManagedCursor.moveToNext();
                }*/

            }

        }

    }

    private void showExif(ExifInterface exif) {


        takeDate = getTagString(ExifInterface.TAG_DATETIME, exif);

        tvPictureDate.setText(takeDate);

        longitude = getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);

        latitude = getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);

        tvPlace.setText(latitude);
    }

    private String getTagString(String TAG, ExifInterface exif) {
        return (exif.getAttribute(TAG));
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
                                galleryAddPic();

                                try {
                                    ExifInterface exif = new ExifInterface(file);
                                    showExif(exif);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                System.out.println(currentPicturePath + " currentPicPath");
                                System.out.println(photoUri + "photoURI");


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                            if (bitmap != null) {
                                btnFeedUpdatePhoto.setImageBitmap(bitmap);
                                galleryAddPic();

                                System.out.println(currentPicturePath + " currentPicPath");
                                System.out.println(photoUri + "photoURI");

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
                break;
            }

            case PICK_FROM_ALBUM: {


                if (data.getData() != null) {

                    try {


                        //File albumFile = null;

                        // albumFile = createImageFile();


                        photoUri = data.getData();

                        //  albumUri = Uri.fromFile(albumFile);


                        //  galleryAddPic();

                        btnFeedUpdatePhoto.setImageURI(photoUri);


                        System.out.println(currentPicturePath + " currentPicPath");
                        System.out.println(photoUri + "photoURI");


                        //cropImage();

                    } catch (Exception e) {

                        e.printStackTrace();

                        Log.v("알림", "앨범에서 가져오기 에러");

                    }

                }
                break;
            }


        }

    }

}










