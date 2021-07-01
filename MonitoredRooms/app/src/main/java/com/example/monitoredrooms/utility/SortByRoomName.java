package com.example.monitoredrooms.utility;

import java.util.Comparator;

public class SortByRoomName implements Comparator<Room> { //STILL NEED TO TEST
    @Override
    public int compare(Room room1, Room room2) {
        return room1.getRoomName().compareTo(room2.getRoomName());
    }
}
