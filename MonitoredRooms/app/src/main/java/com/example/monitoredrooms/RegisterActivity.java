package com.example.monitoredrooms;

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

import com.example.monitoredrooms.ui.home.MonitoredRoomsFragment;
import com.example.monitoredrooms.utility.AuthenticationHelper;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUI();

    }

    private void setupUI(){

        mFullName = findViewById(R.id.fullNameEditText);
        mEmail = findViewById(R.id.registerEmailEditText);
        mPassword = findViewById(R.id.registerPasswordEditText);
        mRegisterBtn = findViewById(R.id.registerButton);
        mLoginBtn = findViewById(R.id.alreadyRegisteredTextView);
        progressBar = findViewById(R.id.registerProgressBar);

        AuthenticationHelper AuthHelper = new AuthenticationHelper(RegisterActivity.this);
        //check if user is already logged in, if so, send to MainActivity
        if (AuthHelper.userIsLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), MonitoredRoomsActivity.class));
            finish();
        }

        //when register button is clicked, perform operations
        mRegisterBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String fullName = mFullName.getText().toString();//maybe change for username //need to use username
            verifyUserInput(fullName, email, password);


            //Let the user know that the process of registration has started
            progressBar.setVisibility(View.VISIBLE);

            //Register the user in firebase
            AuthHelper.createUser(email, password);
            progressBar.setVisibility(View.GONE);
        });

        //make textview clickable to go to login page if user is not registered
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });
    }

    private void verifyUserInput(String fullName, String email, String password){

        //do something with fullName

        //check if user entered the information
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email is Required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Password is Required.");
            return;
        }

        //password must contain  6+ characters
        if (password.length() < 6) {
            mPassword.setError("Password must contain 6 or more characters");
            return;
        }
    }
}