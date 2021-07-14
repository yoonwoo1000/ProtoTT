package com.example.protott;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/*public class PhotoExifActivity extends AppCompatActivity {

    ArrayList<String> mTitleArray;
    ArrayList<String> paths;
    ListView listView;

    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_exif);

        mTitleArray = new ArrayList<String>();
        paths = new ArrayList<String>();

        getImages ();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, // Context
                        android.R.layout.simple_list_item_1, // 연결될 listView의 항목뷰에 대한 resource ID
                        mTitleArray);  // 원본 데이타


        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(paths.get(i)));
            }
        });
        imageView = findViewById(R.id.imageView);
    }

    public void getImages ()
    {
        Cursor mManagedCursor;
        ContentResolver contentResolver = getContentResolver();

        //갤러리의 정보를 저장하고 있는 내부 테이블에서 검색
        mManagedCursor = getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI , null, null, null, null) ;

        if(mManagedCursor != null)
        {
            mManagedCursor.moveToFirst();

            int nSize = mManagedCursor.getColumnCount();

            while (true)
            {
                String result = "";
                String bucket_display_name =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.BUCKET_DISPLAY_NAME)); // 버킷의 이름
                result += bucket_display_name+"|";

                String bucket_id =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.BUCKET_ID)); // 버킷 ID
                result += bucket_id+"|";

                String date_taken =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.DATE_TAKEN)); // 촬영날짜. 1/1000초 단위
                result += date_taken +"|";

                String description =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.DESCRIPTION)); // Image에 대한 설명
                result += description  +"|";

                String is_private =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.IS_PRIVATE)); // 공개 여부
                result += is_private +"|";

                String latitude =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.LATITUDE)); // 위도
                result += latitude +"|";

                String longitude =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.LONGITUDE)); // 경도
                result += longitude +"|";

                String mini_thumb_magic =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.MINI_THUMB_MAGIC)); // 작은 썸네일
                result += mini_thumb_magic +"|";

                String orientation =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.ORIENTATION)); // 사진의 방향. 0, 90, 180, 270
                result += orientation +"|";

                String picasa_id =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.PICASA_ID)); // 피카사에서 매기는 ID
                result += picasa_id +"|";

                String id =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns._ID)); // 레코드의 PK
                result += id +"|";

                String data =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.DATA)); // 데이터 스트림. 파일의 경로
                result += data +"|";


                String title =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.TITLE)); // 제목
                result += title +"|";

                String size =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.SIZE)); // 파일의 크기
                result += size +"|";

                String mime_type =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.MIME_TYPE)); // 마임 타입
                result += mime_type +"|";

                String display_name =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.DISPLAY_NAME)); // 파일 표시명
                result += display_name +"|";

                String date_modified =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(Images.ImageColumns.DATE_MODIFIED)); // 최후 갱신 날짜. 초단위
                result += date_modified +"|";

                String date_added =
                        mManagedCursor.getString(
                                mManagedCursor.getColumnIndex(
                                        Images.ImageColumns.DATE_ADDED)); // 추가 날짜. 초단위
                result += date_added;

                if(latitude != null && !latitude.equals("0") && !longitude.equals("0")) {
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
                }
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_exif, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
*/