package ru.dzen.friends.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomModel implements Parcelable {

    public int getId() {
        return id;
    }

    private static RoomModel instance;

    private int id;
    private String roomName;

    public RoomModel(int id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
