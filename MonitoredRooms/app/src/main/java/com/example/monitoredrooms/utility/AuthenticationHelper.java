package com.example.monitoredrooms.utility;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monitoredrooms.LoginActivity;
import com.example.monitoredrooms.MonitoredRoomsActivity;
import com.example.monitoredrooms.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationHelper {

    private FirebaseAuth mFirebaseAuth;
    private Context mContext;

    //Constructor
    public AuthenticationHelper(@Nullable Context context){
        mContext = context;
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    //when a user is created, it is automatically signed in //need to add username or full name to account
    public void createUser(String email, String password, String confirmPassword){

        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            //user created successfully, display message
            if (task.isSuccessful()) {
                Toast.makeText(mContext, "User Created.", Toast.LENGTH_SHORT).show();
                //send user to main activity (main page)
                mContext.startActivity(new Intent(mContext, MonitoredRoomsActivity.class));
            }
            else {
                Toast.makeText(mContext, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void login(String email, String password){

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(mContext, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    mContext.startActivity(new Intent(mContext, MonitoredRoomsActivity.class));
                }

                else {
                    Toast.makeText(mContext, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean userIsLoggedIn(){

        if(mFirebaseAuth.getCurrentUser() != null){
            return true;
        }
        else{
            return false;
        }
    }

    //future
    public void removeUser(String email, String password){

    }

    public void logout(){
        try {
            mFirebaseAuth.signOut();
        } catch (Exception e) {
            Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    public String getUserID(){
        return mFirebaseAuth.getCurrentUser().getUid();
    }



}
