package com.example.monitoredrooms.ui.home;

import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.monitoredrooms.AddRoomDialog;
import com.example.monitoredrooms.R;
import com.example.monitoredrooms.databinding.FragmentFeature1Binding;
import com.example.monitoredrooms.databinding.FragmentMonitoredRoomsBinding;
import com.example.monitoredrooms.ui.feature1.Feature1Fragment;
import com.example.monitoredrooms.utility.Room;
import com.example.monitoredrooms.utility.RoomAdapter;
import com.example.monitoredrooms.utility.SortByHighTemperature;
import com.example.monitoredrooms.utility.SortByLowTemperature;
import com.example.monitoredrooms.utility.SortByRoomName;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

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
        loadListView(getString(R.string.default_choice));

    }

    //read sort_rooms.xml menu file and initialize menu
     @Override
     public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
         inflater.inflate(R.menu.sort_rooms, menu);
        super.onCreateOptionsMenu(menu, inflater);

     }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {


        int ID = item.getItemId();

        //String choice = item.getTitle().toString();
        //String previousChoice --> from database

        //implement once settings table is set in database
        /**if(ID == R.id.action_sort1){
            loadListView(item.getTitle().toString());

        }
        else if(ID == R.id.action_sort2){

        }
        else if(ID == R.id.action_sort3){

        }*/



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
    public void loadListView(String sortByChoice){
        ArrayList<Room> testRoomList = new ArrayList<>();

        //add rooms to list
        for(int i = 0; i < 10; i++){
            String roomName = "Room " + i;
            testRoomList.add(new Room(roomName, 14+i, 14-i, 2*14+i, "Unoccupied", 10));
        }

        //if the string is empty for some reason, set it to default
        if(sortByChoice == null){
            sortByChoice = getString(R.string.default_choice);
        }

        //changes the order in the list based on the criterion selected
        if(sortByChoice.equalsIgnoreCase("lowest temperature")){
            sortByLowTemperature(testRoomList); //eventually change for list from database
        }
        else if(sortByChoice.equalsIgnoreCase("highest temperature")){
            sortByHighTemperature(testRoomList); //eventually change for list from database
        }
        else{
            sortByRoomName(testRoomList); //default option
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

    private void sortByLowTemperature(ArrayList<Room> roomList){
        Collections.sort(roomList, new SortByLowTemperature());
    }

    private void sortByHighTemperature(ArrayList<Room> roomList){
        Collections.sort(roomList, new SortByHighTemperature());
    }

    private void sortByRoomName(ArrayList<Room> roomList){
        Collections.sort(roomList, new SortByRoomName());
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        //called when DialogInterface is dismissed
        loadListView("default"); //load with settings from database
    }
}