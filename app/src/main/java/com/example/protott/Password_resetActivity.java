package com.example.protott;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Password_resetActivity extends AppCompatActivity {


    private EditText et_passwordemail;
    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        Button btn_send = findViewById(R.id.btn_send);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                send();
            }
        });
    }

    private void send()
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String EMAIL = ((EditText)findViewById(R.id.et_passwordemail)).getText().toString();
        auth.sendPasswordResetEmail(EMAIL).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                { Toast.makeText(Password_resetActivity.this, "메일을 확인해주세요.", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(Password_resetActivity.this, "메일전송에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}