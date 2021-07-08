package com.example.monitoredrooms.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monitoredrooms.AddRoomDialog;
import com.example.monitoredrooms.R;
import com.example.monitoredrooms.RoomProfileActivity;
import com.example.monitoredrooms.databinding.FragmentMonitoredRoomsBinding;
import com.example.monitoredrooms.utility.DatabaseHelper;
import com.example.monitoredrooms.utility.Room;
import com.example.monitoredrooms.utility.RoomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MonitoredRoomsFragment extends Fragment implements DialogInterface.OnDismissListener {

    private FragmentMonitoredRoomsBinding binding;

    protected ListView mRoomListView;
    protected FloatingActionButton mAddRoomFloatingButton;
    protected TextView mNoRoomTextView;
    protected ImageView mNoRoomImageView;
    protected Button mAddRoomButton;

    //enable options menu for MonitoredRooms Fragment
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onStart() {
        super.onStart();



        //maybe save the choice in database as user preference
        loadListView();

    }

    //read sort_rooms.xml menu file and initialize menu
     @Override
     public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
         inflater.inflate(R.menu.sort_rooms, menu);
        super.onCreateOptionsMenu(menu, inflater);

         DatabaseHelper db = new DatabaseHelper(getContext());
         db.loadSortRoomSetting(menu.getItem(0));
     }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {


        int ID = item.getItemId();
        DatabaseHelper db = new DatabaseHelper(getContext());

        //change between the three sort options and update user settings in database
        if (ID == R.id.action_sort1) {
            if(item.getTitle().toString().equalsIgnoreCase(getString(R.string.by_low_temperature))){
                item.setTitle(getString(R.string.by_high_temperature));
                db.updateSortRoomSetting(getString(R.string.lowest_temperature));
                loadListView();
            }
            else if(item.getTitle().toString().equalsIgnoreCase(getString(R.string.by_high_temperature))){
                item.setTitle(getString(R.string.by_room_name));
                db.updateSortRoomSetting(getString(R.string.highest_temperature));
                loadListView();
            }
            else if(item.getTitle().toString().equalsIgnoreCase(getString(R.string.by_room_name))){
                item.setTitle(getString(R.string.by_low_temperature));
                db.updateSortRoomSetting(getString(R.string.room_name));
                loadListView();
            }

        }

        //necessary to return super or clicking up button will make the app crash
        return super.onOptionsItemSelected(item);
    }

    //define options menu

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentMonitoredRoomsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupUI(root);




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setupUI(View view){


        //only appears when there are no rooms
        mNoRoomTextView = view.findViewById(R.id.noRoomTextView);
        mNoRoomImageView = view.findViewById(R.id.noRoomImageView);
        mAddRoomButton = view.findViewById(R.id.addRoomButton);
        mAddRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opens the same dialog fragment as the floating action button
                AddRoomDialog dialog = new AddRoomDialog();
                dialog.show(getParentFragmentManager(), "Add Room");
            }
        });

        //everything in the section below is present when there are rooms in database
        mAddRoomFloatingButton = view.findViewById(R.id.addRoomFloatingButton);
        mAddRoomFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opens the same dialog fragment as the button
                AddRoomDialog dialog = new AddRoomDialog();
                dialog.show(getParentFragmentManager(), "Add Room");

            }
        });

        mRoomListView = view.findViewById(R.id.monitored_room_list_view);
        mRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room room = (Room) parent.getItemAtPosition(position);
                goToRoomProfileActivity(room);

                //for test purposes // remove eventually
                //Toast.makeText(getContext(), room.getRoomName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void goToRoomProfileActivity(Room room) {
        //create room profile activity
        Intent roomProfileIntent = new Intent(MonitoredRoomsFragment.this.getActivity(), RoomProfileActivity.class);
        roomProfileIntent.putExtra("Room", room); //passing the whole object might not be necessary, only room name
        startActivity(roomProfileIntent);
    }

    public void loadListView(){

        DatabaseHelper db = new DatabaseHelper(getContext());
        ArrayList<Room> roomList = new ArrayList<>();
        RoomAdapter roomAdapter = new RoomAdapter(getContext(), roomList);

        db.hasRooms(mRoomListView, mAddRoomFloatingButton, mNoRoomTextView, mNoRoomImageView, mAddRoomButton);
        db.updateRoomList(roomList, roomAdapter, mRoomListView); //add sort choice
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        //called when DialogInterface is dismissed
        loadListView(); //load with settings from database
    }
}