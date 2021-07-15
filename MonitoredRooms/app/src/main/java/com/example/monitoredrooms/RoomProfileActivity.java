package com.example.monitoredrooms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.monitoredrooms.utility.DatabaseHelper;
import com.example.monitoredrooms.utility.Room;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        mRoomName = findViewById(R.id.roomName);
        mRoomTemp = findViewById(R.id.roomTemp);
        mRoomOccupancyStatus = findViewById(R.id.roomOccupancyStatus);
        mDeleteRoom = findViewById(R.id.deleteRoom);
        mEditRoom = findViewById(R.id.editRoom);

        mEditRoomName = findViewById(R.id.edit_roomName);
        mEditMinTemp = findViewById(R.id.edit_minTemp);
        mEditMaxTemp = findViewById(R.id.edit_maxTemp);
        mEditOccupancy = findViewById(R.id.edit_occupancy);
        mEditRoomLayout = findViewById(R.id.edit_room_layout);
        mSaveChanges = findViewById(R.id.saveChanges);

        /** Assign text values to each of the text fields
         * Take information from the database and set values.
         * Database not yet created so assign values on hold.
         */

        //when used the passed room, the values are not changing, should be using database directly
        Room room = getIntent().getParcelableExtra("Room");
        mRoomName.setText(room.getRoomName());
        DatabaseHelper db = new DatabaseHelper(RoomProfileActivity.this);
        db.readRoom(room, mRoomTemp, mRoomOccupancyStatus);
        //displays the changes in room temperature and room occupancy
        //to show change in room name, update EditText after pressing save changes



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

            }
        });

        //Save the changes made into the database and restart roomProfileActivity
        mSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String roomName = mEditRoomName.getText().toString();
                double minTemp = Double.parseDouble(mEditMinTemp.getText().toString());
                double maxTemp = Double.parseDouble(mEditMaxTemp.getText().toString());
                int interval = Integer.parseInt(mEditOccupancy.getText().toString());

                Room newRoom = new Room(roomName, minTemp, maxTemp, interval);
                DatabaseHelper db = new DatabaseHelper(RoomProfileActivity.this);

                db.editRoom(room, newRoom);

            }
        });


        //Delete the room from the database

        mDeleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //For Testing, deleting a hardcoded value
                DatabaseHelper db = new DatabaseHelper(RoomProfileActivity.this);
                db.deleteRoom(room.getRoomName());
                Intent monitoredRoomsIntent = new Intent(RoomProfileActivity.this, MonitoredRoomsActivity.class);
                monitoredRoomsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //activities on top will be closed
                startActivity(monitoredRoomsIntent);
            }
        });
    }

    private boolean roomDataValidation(){

        //implements if statements to verify that the data entered is valid
        //need to know sensors limitations to set min and/or max values for each sensor
        //need to know limit for the name size, for now set it to 8 characters

        return true;
    }
}