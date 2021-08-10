package com.example.protott;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.protott.model.Memberinfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemberInitActivity extends AppCompatActivity {

    private Button mb_btn;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);

        mb_btn = findViewById(R.id.mb_btn);
        mb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 화면으로 이동
                profileUpdate();
            }
        });
    }



    private void profileUpdate() {
        String mb_name = ((EditText) findViewById(R.id.mb_name)).getText().toString();
        String mb_phonenum = ((EditText) findViewById(R.id.mb_phonenum)).getText().toString();
        String mb_date = ((EditText) findViewById(R.id.mb_date)).getText().toString();
        String mb_address = ((EditText) findViewById(R.id.mb_address)).getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Memberinfo memberinfo = new Memberinfo(mb_name, mb_phonenum, mb_date, mb_address);

        if (user != null) {
            db.collection("user").document(user.getUid()).set(memberinfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finish();
                            Toast.makeText( MemberInitActivity.this, "회원정보등록 성공", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( MemberInitActivity.this, "회원정보등록 실패", Toast.LENGTH_SHORT).show();

                        }
                    });


        }
    }
}