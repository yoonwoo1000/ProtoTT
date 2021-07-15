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


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private static final int RC_SIGN_IN = 900;


    private FirebaseAuth mAuth;
    EditText etId, etPassword;
    Button btnLogin, btnSignup;
    Button btnFacebookLogin;// 개발용 로그인


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        etId = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnSignin);
        btnSignup = findViewById(R.id.btnSignup);


        btnFacebookLogin = findViewById(R.id.btnFacebookLogin);


        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = etId.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (notEmpty()) {
                    signIn(id, password);

                } else {
                    Toast.makeText(LoginActivity.this, "All fields are Required", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // User Check
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(LoginActivity.this, "onStart", Toast.LENGTH_SHORT).show();
            reload();
        }
    }


    private void signIn(String id, String password) {
        // Signin Email
        mAuth.signInWithEmailAndPassword(id, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    /*private void sendEmailVerification() {
        // Email 濡??뺤씤 硫붿씪

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email 蹂대궡湲?
                    }
                });
    }*/

    private void reload() {
    }

    private boolean notEmpty() {
        Boolean checker;

        String checkId = etId.getText().toString();
        String checkPassword = etPassword.getText().toString();

        if (checkId.isEmpty() || checkPassword.isEmpty()) {
            return false;
        } else {
            checker = true;
        }


        return checker;

    }


}
