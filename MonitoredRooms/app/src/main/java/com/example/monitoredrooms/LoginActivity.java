package com.example.monitoredrooms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monitoredrooms.utility.AuthenticationHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI();

    }

    private void setupUI(){
        mEmail = findViewById(R.id.loginEmailEditText);
        mPassword = findViewById(R.id.loginPasswordEditText);
        mLoginBtn = findViewById(R.id.LoginButton);
        mCreateBtn = findViewById(R.id.NoAccountRegisterTextView);
        progressBar = findViewById(R.id.loginProgressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                verifyUserInput(email, password);

                //user being logged in
                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user
                AuthenticationHelper AuthHelper = new AuthenticationHelper(LoginActivity.this);
                AuthHelper.login(email, password);

                progressBar.setVisibility(View.GONE);
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MonitoredRoomsActivity.class));
                        }

                        else {
                            Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(), Register.class));
                            progressBar.setVisibility(View.GONE);

                        }

                    }
                });
            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    public void verifyUserInput(String email, String password){

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email is Required.");
            return;
        }
  
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Password is Required.");
            return;
        }

        if (password.length() < 6) {
            mPassword.setError("Password Must be >= 6 Characters");
            return;
        }
    }
}


