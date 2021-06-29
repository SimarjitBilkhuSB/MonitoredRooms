package com.example.monitoredrooms.utility;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {

    private String mRoomName;
    private double mRoomTemperature;
    private double mRoomMinTemperature;
    private double mRoomMaxTemperature;
    private String mRoomOccupancy;
    private int mRoomOccupancyCheckInterval;


    //constructor
    public Room(String RoomName, double RoomTemp, double MinTemp, double MaxTemp, String Occupancy, int OccupancyInterval){
        this.mRoomName = RoomName;
        this.mRoomTemperature = RoomTemp;
        this.mRoomMinTemperature = MinTemp;
        this.mRoomMaxTemperature = MaxTemp;
        this.mRoomOccupancy = Occupancy;
        this.mRoomOccupancyCheckInterval = OccupancyInterval;
    }


    //constructor initializes Room object from Parcel
    private Room(Parcel in) {
        mRoomName = in.readString();
        mRoomTemperature = in.readDouble();
        mRoomMinTemperature = in.readDouble();
        mRoomMaxTemperature = in.readDouble();
        mRoomOccupancy = in.readString();
        mRoomOccupancyCheckInterval = in.readInt();
    }

    //generates instances of Room objects from a parcel
    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    //No file descriptor, return 0.
    @Override
    public int describeContents() {
        return 0;
    }

    //write content of Room object into a parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRoomName);
        dest.writeDouble(mRoomTemperature);
        dest.writeDouble(mRoomMinTemperature);
        dest.writeDouble(mRoomMaxTemperature);
        dest.writeString(mRoomOccupancy);
        dest.writeInt(mRoomOccupancyCheckInterval);
    }

    public String getRoomName() {
        return mRoomName;
    }

    public void setRoomName(String mRoomName) {
        this.mRoomName = mRoomName;
    }

    public double getRoomTemperature() {
        return mRoomTemperature;
    }

    public void setRoomTemperature(double mRoomTemperature) {
        this.mRoomTemperature = mRoomTemperature;
    }

    public double getRoomMinTemperature() {
        return mRoomMinTemperature;
    }

    public void setRoomMinTemperature(double mRoomMinTemperature) {
        this.mRoomMinTemperature = mRoomMinTemperature;
    }

    public double getRoomMaxTemperature() {
        return mRoomMaxTemperature;
    }

    public void setRoomMaxTemperature(double mRoomMaxTemperature) {
        this.mRoomMaxTemperature = mRoomMaxTemperature;
    }

    public String getRoomOccupancy() {
        return mRoomOccupancy;
    }

    public void setRoomOccupancy(String mRoomOccupancy) {
        this.mRoomOccupancy = mRoomOccupancy;
    }

    public int getRoomOccupancyCheckInterval() {
        return mRoomOccupancyCheckInterval;
    }

    public void setRoomOccupancyCheckInterval(int mRoomOccupancyCheckInterval) {
        this.mRoomOccupancyCheckInterval = mRoomOccupancyCheckInterval;
    }
}
