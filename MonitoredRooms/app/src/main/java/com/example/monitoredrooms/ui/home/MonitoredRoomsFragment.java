package com.example.monitoredrooms.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.monitoredrooms.R;
import com.example.monitoredrooms.databinding.FragmentMonitoredRoomsBinding;
import com.example.monitoredrooms.utility.Room;
import com.example.monitoredrooms.utility.RoomAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MonitoredRoomsFragment extends Fragment {

    private FragmentMonitoredRoomsBinding binding;

    protected ListView mRoomListView;

    //enable options menu for MonitoredRooms Fragment
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

        mRoomListView = view.findViewById(R.id.monitored_room_list_view);
        mRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        loadListView();
    }

    //only for test purposes
    public void loadListView(){
        List<Room> testRoomList = new ArrayList<>();

        //add rooms to list
        for(int i = 0; i < 15; i++){
            String roomName = "Room " + i;
            testRoomList.add(new Room(roomName, 14+i, 14-i, 2*14+i, "Unoccupied", 10));
        }

        //if list not empty, load listView
        if(!testRoomList.isEmpty()){
            RoomAdapter roomAdapter = new RoomAdapter(getContext(), (ArrayList<Room>) testRoomList);
            mRoomListView.setAdapter(roomAdapter);

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