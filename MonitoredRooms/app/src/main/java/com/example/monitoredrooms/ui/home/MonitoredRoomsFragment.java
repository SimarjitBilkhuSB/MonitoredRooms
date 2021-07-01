package com.example.monitoredrooms.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monitoredrooms.R;
import com.example.monitoredrooms.databinding.FragmentMonitoredRoomsBinding;
import com.example.monitoredrooms.utility.Room;
import com.example.monitoredrooms.utility.RoomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MonitoredRoomsFragment extends Fragment {

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

        loadListView();

    }

    //read sort_rooms.xml menu file and initialize menu
     @Override
     public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
         inflater.inflate(R.menu.sort_rooms, menu);
        super.onCreateOptionsMenu(menu, inflater);

     }

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
            }
        });

        //everything in the section below is present when there are rooms in database
        mAddRoomFloatingButton = view.findViewById(R.id.addRoomFloatingButton);
        mAddRoomFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opens the same dialog fragment as the button
            }
        });

        mRoomListView = view.findViewById(R.id.monitored_room_list_view);
        mRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room room = (Room) parent.getItemAtPosition(position);
                goToRoomProfileActivity(room);

                //for test purposes // remove eventually
                Toast.makeText(getContext(), room.getRoomName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void goToRoomProfileActivity(Room room) {
        //create room profile activity
        //Intent roomProfileIntent = new Intent(MonitoredRoomsFragment.this, RoomProfileActivity.class);
        //roomProfileIntent.putExtra("Room", room);
    }

    //only for test purposes
    public void loadListView(){
        ArrayList<Room> testRoomList = new ArrayList<>();

        //add rooms to list
        for(int i = 0; i < 0; i++){
            String roomName = "Room " + i;
            testRoomList.add(new Room(roomName, 14+i, 14-i, 2*14+i, "Unoccupied", 10));
        }

        //if list not empty, load listView otherwise prompt the user to add/create a room
        if(!testRoomList.isEmpty()){
            RoomAdapter roomAdapter = new RoomAdapter(getContext(), testRoomList);
            mRoomListView.setAdapter(roomAdapter);

            mNoRoomTextView.setVisibility(View.GONE);
            mNoRoomImageView.setVisibility(View.GONE);
            mAddRoomButton.setVisibility(View.GONE);

        }
        else{
            mRoomListView.setVisibility(View.GONE);
            mAddRoomFloatingButton.setVisibility(View.GONE);

            mNoRoomTextView.setVisibility(View.VISIBLE);
            mNoRoomImageView.setVisibility(View.VISIBLE);
            mAddRoomButton.setVisibility(View.VISIBLE);

        }
    }

    //implement later
    private void sortByLowTemperature(){

    }

    private void sortByHighTemperature(){

    }

    private void sortByRoomName(){

    }
}