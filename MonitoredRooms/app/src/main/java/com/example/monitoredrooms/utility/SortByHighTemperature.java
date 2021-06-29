package com.example.monitoredrooms.utility;

import java.util.Comparator;

public class SortByHighTemperature implements Comparator<Room> { //STILL NEED TO TEST

    //to sort Room objects by highest temperature
    @Override
    public int compare(Room room1, Room room2) {

        int firstComparison = Double.compare(room1.getRoomTemperature(), room2.getRoomTemperature());

        //if same temperature, then sort by room occupancy
        if(firstComparison == 0){
            String occupancy1 = room1.getRoomOccupancy();
            String occupancy2 = room2.getRoomOccupancy();
            int secondComparison = occupancy1.compareTo(occupancy2); //Room is Free/Unoccupied or Occupied


            //if same occupancy, then sort by room name
            if(secondComparison == 0){
                String name1 = room1.getRoomName();
                String name2 = room2.getRoomName();
                return name1.compareTo(name2);
            }
            else{
                return secondComparison;
            }
        }
        else{
            return firstComparison;
        }

    }
}
