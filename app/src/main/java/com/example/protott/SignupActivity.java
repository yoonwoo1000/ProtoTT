package com.example.protott;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

   private FirebaseAuth Auth;

    EditText etSignupEmail,etSignupPassword;
    Button btnSignupOk, btnSignupCancel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Auth = FirebaseAuth.getInstance();

        etSignupEmail = findViewById(R.id.etSignupEmail);
        etSignupPassword = findViewById(R.id.etSignupPassword);
        btnSignupOk = findViewById(R.id.btnSignupOK);
        btnSignupCancel = findViewById(R.id.btnSignupCancel);

        btnSignupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignupOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createAccount();

            }
        });



    }
       private void createAccount() {



       Auth.createUserWithEmailAndPassword(etSignupEmail.getText().toString(),etSignupPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {


                            Toast.makeText(SignupActivity.this, "Success",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = Auth.getCurrentUser();



                        } else {

                            Toast.makeText(SignupActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    }







