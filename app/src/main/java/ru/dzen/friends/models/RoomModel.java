package ru.dzen.friends.models;

public class RoomModel {

    public int getId() {
        return id;
    }

    private int id;
    private String roomName;

    public RoomModel(int id, String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }
}
