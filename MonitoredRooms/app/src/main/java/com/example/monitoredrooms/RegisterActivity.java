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

import com.example.monitoredrooms.ui.home.MonitoredRoomsFragment;
import com.example.monitoredrooms.utility.AuthenticationHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword, mConfirmPassword;
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
        mConfirmPassword = findViewById(R.id.registerConfirmPasswordEditText);
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
            String confirmPassword = mConfirmPassword.getText().toString().trim();
            String fullName = mFullName.getText().toString();//maybe change for username //need to use username
            verifyUserInput(fullName, email, password, confirmPassword);

            fAuth = FirebaseAuth.getInstance();

            //Let the user know that the process of registration has started
            progressBar.setVisibility(View.VISIBLE);

            //Register the user in firebase
       //   AuthHelper.createUser(email, password, confirmPassword);
            progressBar.setVisibility(View.GONE);

           /* fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                //user created successfully, display message
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                    //send user to main activity (main page)
                    startActivity(new Intent(getApplicationContext(), MonitoredRoomsActivity.class));
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                }
            });*/

        });


        //make textview clickable to go to login page if user is not registered
        mLoginBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
    }

    private void verifyUserInput(String fullName, String email, String password, String confirmPassword){

        //do something with fullName
        if (TextUtils.isEmpty(fullName)) {
            mFullName.setError("Full name is Required.");
           // return;
        }
        //check if user entered the information
    else if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email is Required.");
          //  return;
        }

    else if (TextUtils.isEmpty(password)) {
            mPassword.setError("Password is Required.");
          //  return;
        }

        //password must contain  6+ characters
      else if (password.length() < 6) {
            mPassword.setError("Password must contain 6 or more characters");
          //  return;
        }

      else if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPassword.setError("Must confirm password");
            //return;
        }

        //confirm password
   else if (!password.equals(confirmPassword)){
            mConfirmPassword.setError("Passwords do not match.");

           startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
           // return;
        }
else {
            AuthenticationHelper AuthHelper = new AuthenticationHelper(RegisterActivity.this);
            AuthHelper.createUser(email, password, confirmPassword);
    /*fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                //user created successfully, display message
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                    //send user to main activity (main page)
                    startActivity(new Intent(getApplicationContext(), MonitoredRoomsActivity.class));
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                }
            });*/
        }
    }

}


