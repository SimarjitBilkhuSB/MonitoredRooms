package com.example.monitoredrooms.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monitoredrooms.R;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends ArrayAdapter<Room> {

    private Context mContext;
    private List<Room> mRoomList = new ArrayList<>();

    //Constructor
    public RoomAdapter(@NonNull Context context, ArrayList<Room> roomArrayList){
        super(context, 0, roomArrayList);

        mContext = context;
        mRoomList = roomArrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listElement = convertView;

        //create the view for the elements in the list from the simple_list_item_1.xml file
        if(listElement == null){
            listElement = LayoutInflater.from(mContext).inflate(R.layout.room_list_item, parent, false);
        }

        //get the Room object from the list at the current position/index
        Room currentRoom = mRoomList.get(position);

        //get the TextView for the element in the list that is in the simple_list_item_1.xml file
        TextView Text1 = (TextView) listElement.findViewById(R.id.item_textview1);
        TextView Text2 = (TextView) listElement.findViewById(R.id.item_textview2);
        TextView Text3 = (TextView) listElement.findViewById(R.id.item_textview3);

        //modify the TextView's text to show the Room's name, temperature and occupancy
        Text1.setText(currentRoom.getRoomName().trim()+": ");
        Text2.setText(currentRoom.getRoomTemperature() + "°C");
        Text3.setText(currentRoom.getRoomOccupancy());


        return listElement; //return the view

    }
}
