package com.example.monitoredrooms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class RoomProfileActivity extends AppCompatActivity {
    protected EditText mRoomName;
    protected EditText mRoomTemp;
    protected EditText mRoomOccupancyStatus;
    protected EditText mEditRoomName;
    protected EditText mEditMinTemp;
    protected EditText mEditMaxTemp;
    protected EditText mEditOccupancy;
    protected Button mDeleteRoom;
    protected Button mSaveChanges;
    protected Button mEditRoom;
    protected View mEditRoomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_profile);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        mRoomName = findViewById(R.id.roomName);
        mRoomTemp = findViewById(R.id.roomTemp);
        mRoomOccupancyStatus = findViewById(R.id.roomOccupancyStatus);
        mDeleteRoom = findViewById(R.id.deleteRoom);
        mEditRoom = findViewById(R.id.editRoom);
        mSaveChanges = findViewById(R.id.saveChanges);
        //mEditRoomName = mEditRoomLayout.findViewById(R.id.edit_roomName);
        //mEditMinTemp = mEditRoomLayout.findViewById(R.id.edit_minTemp);
        //mEditMaxTemp = mEditRoomLayout.findViewById(R.id.edit_maxTemp);
        //mEditOccupancy = mEditRoomLayout.findViewById(R.id.edit_occupancy);
        mEditRoomLayout = findViewById(R.id.edit_room_layout);

        /** Assign text values to each of the text fields
         * Take information from the database and set values.
         * Database not yet created so assign values on hold.
         */



        //Make text fields not editable
        mRoomName.setEnabled(false);
        mRoomTemp.setEnabled(false);
        mRoomOccupancyStatus.setEnabled(false);

        mEditRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make Room Profile Layout editable
                mEditRoomLayout.setVisibility(View.VISIBLE);
                mRoomName.setVisibility(View.GONE);
                mRoomOccupancyStatus.setVisibility(View.GONE);
                mRoomTemp.setVisibility(View.GONE);
                //Remove delete room and edit room button from view and display save changes button
                mDeleteRoom.setVisibility(View.GONE);
                mEditRoom.setVisibility(View.GONE);
                mSaveChanges.setVisibility(View.VISIBLE);

            }
        });

        //Save the changes made into the database

        /**
         mSaveChanges.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
        });
         */

        //Delete the room from the database

         mDeleteRoom.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //For Testing, deleting a hardcoded value
            DatabaseReference ref = database.getReference("User").child("Room").child("e");
            ref.removeValue();

            startActivity(new Intent(RoomProfileActivity.this, MonitoredRoomsActivity.class));
        }
        });

    }
}