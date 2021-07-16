package com.example.monitoredrooms.utility;

import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monitoredrooms.RoomProfileActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {

    private DatabaseReference mDatabaseReference;
    private Context mContext;

    /** Apart from addRoom, deleteRoom methods are activity-specific */

    //Constructor - add user name to constructor later
    public DatabaseHelper(@Nullable Context context){
        this.mContext = context;
        AuthenticationHelper AuthHelper = new AuthenticationHelper(context);
        //get instance of DatabaseReference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(AuthHelper.getUserID());
    }

    public void addRoom(Room room){

        ValueEventListener addRoomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                boolean isNameUnique = true;
                //loop1: go through each element in the room list
                for(DataSnapshot roomSnapshot : snapshot.getChildren()){
                    //if the name is not unique then break loop
                    if(!isNameUnique){
                        break;
                    }
                    //loop2: go through each element in the room
                    for(DataSnapshot roomElementSnapshot : roomSnapshot.getChildren()){
                        String roomInfoKey = roomElementSnapshot.getKey();
                        //if the name is not unique then break loop
                        if(!isNameUnique){
                            break;
                        }
                        //if the information label is "room name"
                        else if(roomInfoKey.equalsIgnoreCase("roomName")){
                            //get value of the room name, i.e. the actual room name
                            String roomInfo = roomElementSnapshot.getValue(String.class);
                            //if the room name is found in the database
                            if(roomInfo.equalsIgnoreCase(room.getRoomName())){
                                isNameUnique = false;

                            }

                        }
                    }
                }
                if(isNameUnique){
                    //add room if name is not already in the database
                    snapshot.getRef().push().setValue(room);
                    Toast.makeText(mContext, room.getRoomName() + " successfully added.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(mContext, "Room name must be unique! Please choose another name!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(mContext, "Database operation failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        //need to change Jordan for the current user's username
        mDatabaseReference.child("Rooms").addListenerForSingleValueEvent(addRoomListener);

    }

    public void deleteRoom(String roomName){

        ValueEventListener deleteRoomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //loop1: go through each element in the room list
                for(DataSnapshot roomSnapshot : snapshot.getChildren()){
                    //loop2: go through each element in the room
                    for(DataSnapshot roomElementSnapshot : roomSnapshot.getChildren()){
                        String roomInfoKey = roomElementSnapshot.getKey();
                        //if the information label is "room name"
                        if(roomInfoKey.equalsIgnoreCase("roomName")){
                           //get the value of the room name, i.e. the actual room name
                            String roomInfo = roomElementSnapshot.getValue(String.class);
                            //if the room name of the current element is equal to the name of the room to be deleted
                            if(roomInfo.equalsIgnoreCase(roomName)){
                                //get the reference in which the room is saved, delete room
                                roomSnapshot.getRef().removeValue();
                                Toast.makeText(mContext, roomName + " deleted successfully.", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(mContext, "Database operation failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        //need to change Jordan for the current user's username
        mDatabaseReference.child("Rooms").addListenerForSingleValueEvent(deleteRoomListener);
    }

    /** Two Methods below are used specifically in room profile activity */

    public void editRoom(Room oldRoom, Room newRoom){

        ValueEventListener roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                boolean isNameUnique = true;
                DatabaseReference roomRef = null;
                //loop1: go through each element of the room list
                for(DataSnapshot roomSnapshot : snapshot.getChildren()){
                    //if the new room name is not unique break loop
                    if(!isNameUnique){
                        break;
                    }
                    //loop2: go through each element in the room
                    for(DataSnapshot roomElementSnapshot : roomSnapshot.getChildren()){
                        String roomInfoKey = roomElementSnapshot.getKey();
                        //if the information label is "roomName"
                        if(roomInfoKey.equalsIgnoreCase("roomName")){
                            String roomInfo = roomElementSnapshot.getValue(String.class);
                            //if the room name of the current element is equal to the name of the new room
                            if(roomInfo.equalsIgnoreCase(newRoom.getRoomName())){
                                //if the old room and the new room are the same, update the room
                                if(oldRoom.getRoomName().equalsIgnoreCase(newRoom.getRoomName())){

                                    roomRef = roomSnapshot.getRef();
                                    Map<String,Object> roomElements = new ArrayMap<>();
                                    roomElements.put("roomMaxTemperature", newRoom.getRoomMaxTemperature());
                                    roomElements.put("roomMinTemperature", newRoom.getRoomMinTemperature());
                                    roomElements.put("roomOccupancyCheckInterval", newRoom.getRoomOccupancyCheckInterval());
                                    roomRef.updateChildren(roomElements).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(mContext, oldRoom.getRoomName() + " was successfully edited!", Toast.LENGTH_LONG).show();

                                            //restart profile activity
                                            Intent activityIntent = new Intent(mContext, RoomProfileActivity.class);
                                            //the activity will be restarted with the new intent
                                            activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            activityIntent.putExtra("Room", newRoom);
                                            mContext.startActivity(activityIntent);
                                        }
                                    });
                                    //clear reference
                                    roomRef = null;
                                }
                                else{
                                    //a different room in the database already using the name chosen to edit the old room
                                    isNameUnique = false;
                                    //need break or will continue
                                    break;
                                }
                            }
                            //should be if the newRoom name is not found in the database
                            else if(roomInfo.equalsIgnoreCase(oldRoom.getRoomName()) && isNameUnique){
                                //save/record oldRoom reference
                                roomRef = roomSnapshot.getRef();
                            }

                        }
                    }
                }
                //if the new name is not already in the database, and new name is unique and different than old name
                //update the room
                if(isNameUnique && roomRef != null){

                    Map<String,Object> roomElements = new ArrayMap<>();
                    roomElements.put("roomMaxTemperature", newRoom.getRoomMaxTemperature());
                    roomElements.put("roomMinTemperature", newRoom.getRoomMinTemperature());
                    roomElements.put("roomName", newRoom.getRoomName());
                    roomElements.put("roomOccupancyCheckInterval", newRoom.getRoomOccupancyCheckInterval());
                    roomRef.updateChildren(roomElements).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            String message = oldRoom.getRoomName() + " was successfully changed to " + newRoom.getRoomName() + ".";
                            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

                            //restart profile activity
                            Intent activityIntent = new Intent(mContext, RoomProfileActivity.class);
                            //the activity will be restarted with the new intent
                            activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activityIntent.putExtra("Room", newRoom);
                            mContext.startActivity(activityIntent);

                        }
                    });
                }
                //if the new name is already in use in the database by another room which is not the old room
                //and the room reference has been cleared
                else if(!isNameUnique){
                    Toast.makeText(mContext, "Room name must be unique! Please choose another name!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(mContext, "Database operation failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        mDatabaseReference.child("Rooms").addListenerForSingleValueEvent(roomListener);
    }


    //show changes in roomTemp and roomOccupancy of the room
    public void readRoom(Room oldRoom, EditText roomTempET, EditText roomOccupancyET, EditText minTempET, EditText maxTempET, EditText occupIntervalET){

        ValueEventListener roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //loop1: go through each element in the room list
                for(DataSnapshot roomSnapshot : snapshot.getChildren()){
                    //loop2: go through each element of the room
                    for(DataSnapshot roomElementSnapshot : roomSnapshot.getChildren()){
                        String roomInfoKey = roomElementSnapshot.getKey();
                        //if the information label is "room name"
                        if(roomInfoKey.equalsIgnoreCase("roomName")){
                            //get the value of the room name, i.e. the actual room name
                            String roomInfo = roomElementSnapshot.getValue(String.class);
                            //if the room name of the current element is equal to the name of the old room
                            if(roomInfo.equalsIgnoreCase(oldRoom.getRoomName())){
                                Room newRoom = roomSnapshot.getValue(Room.class);
                                //update oldRoom info with new room info
                                roomTempET.setText(String.valueOf(newRoom.getRoomTemperature()));
                                roomOccupancyET.setText(newRoom.getRoomOccupancy());
                                minTempET.setText(String.valueOf(newRoom.getRoomMinTemperature()));
                                maxTempET.setText(String.valueOf(newRoom.getRoomMaxTemperature()));
                                occupIntervalET.setText(String.valueOf(newRoom.getRoomOccupancyCheckInterval()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(mContext, "Database operation failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        //need to change Jordan for the current user's username
        mDatabaseReference.child("Rooms").addValueEventListener(roomListener);

    }


    /** Everything below is used specifically in monitored rooms fragment */

    //takes a list of rooms and a room adapter, updates the rooms list and notify to refresh view
    public void updateRoomList(List<Room> roomList, RoomAdapter roomAdapter, ListView roomListView){

        //to update list when rooms are added or changed, also reads rooms if they exist
        //make sure the element is not already in the list somehow
        ChildEventListener roomListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Room room = snapshot.getValue(Room.class);
                //isRoomNameUnique(roomList);
                roomList.add(room);
                readSortRoomSetting(roomList, roomAdapter, roomListView);//sort room by user setting and notify to refresh view
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Room newRoom = snapshot.getValue(Room.class);
                for(int i = 0; i < roomList.size(); i++){
                    Room oldRoom = roomList.get(i);
                    if (oldRoom.getRoomName().equalsIgnoreCase(newRoom.getRoomName())) {
                        roomList.set(i, newRoom);
                    }
                }
                readSortRoomSetting(roomList, roomAdapter, roomListView);
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Room newRoom = snapshot.getValue(Room.class);
                for(int i = 0; i < roomList.size(); i++){
                    Room oldRoom = roomList.get(i);
                    if (oldRoom.getRoomName().equalsIgnoreCase(newRoom.getRoomName())) {
                        roomList.remove(i);
                    }
                }
                readSortRoomSetting(roomList, roomAdapter, roomListView);
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(mContext, "Database operation failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        //need to change Jordan for the current user's username
        this.mDatabaseReference.child("Rooms").addChildEventListener(roomListener);
    }


    public void hasRooms(ListView listView, FloatingActionButton floatingButton, TextView textView,
                         ImageView imageView, Button button){

        //to notify if database contains room for current user
        ValueEventListener roomValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //if snapshot exists, there are rooms in the database, true
                if(snapshot.exists()){
                    listView.setVisibility(View.VISIBLE);
                    floatingButton.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);

                }
                else{
                    listView.setVisibility(View.GONE);
                    floatingButton.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                //Show message if error
                Toast.makeText(mContext, "Database operation failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        //need to change Jordan for the current user's username
        this.mDatabaseReference.child("Rooms").addValueEventListener(roomValueListener);
    }

    //loads room settings and load the menu item
    public void loadSortRoomSetting(MenuItem item){

        ValueEventListener settingListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String setting = snapshot.getValue(String.class);

                    //order of appearance for the sort setting menu item --> room name, lowest temp, highest temp
                    if(setting.equalsIgnoreCase("room name")){
                        item.setTitle("By Lowest Temperature");
                    }
                    else if(setting.equalsIgnoreCase("lowest temperature")){
                        item.setTitle("By Highest Temperature");
                    }
                    else if(setting.equalsIgnoreCase("highest temperature")){
                        item.setTitle("By Room Name");
                    }
                    else {
                        item.setTitle("By Room Name");
                    }
                }
                else{
                    //no setting for sorting rooms found in database
                    item.setTitle("Room Name");
                    updateSortRoomSetting("Room Name"); //default: sort by room name
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(mContext, "Database operation failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        mDatabaseReference.child("Settings").child("Sort Rooms By").addListenerForSingleValueEvent(settingListener);


    }

    public void updateSortRoomSetting(String setting){

        if(setting.isEmpty()){
            setting = "Room Name";
        }
        //adds the settings for how the data is sorted, also overwrites/updates the settings if exists
        //don't change reference except Jordan should be change with current user's username
        mDatabaseReference.child("Settings").child("Sort Rooms By").setValue(setting)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(mContext, "Database operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    //read user settings and sort rooms
    private void readSortRoomSetting(List<Room> roomList, RoomAdapter roomAdapter, ListView roomListView){

        ValueEventListener settingListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String setting = snapshot.getValue(String.class);
                sortRooms(roomList, setting);
                roomListView.setAdapter(roomAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(mContext, "Database operation failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        this.mDatabaseReference.child("Settings").child("Sort Rooms By").addValueEventListener(settingListener);
    }

    private void sortRooms(List<Room> roomList, String setting) {

        if(setting.equalsIgnoreCase("lowest temperature")){
            Collections.sort(roomList, new SortByLowTemperature());
        }
        else if(setting.equalsIgnoreCase("highest temperature")){
            Collections.sort(roomList, new SortByHighTemperature());
        }
        else if(setting.equalsIgnoreCase("room name")){
            Collections.sort(roomList, new SortByRoomName());
        }
        else{
            Collections.sort(roomList, new SortByRoomName());
        }

    }




}
