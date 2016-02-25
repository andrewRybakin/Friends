package ru.dzen.friends.models;

public class GameModel {
    private String name;
    private String place;
    private boolean opened;

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public boolean isOpened() {
        return opened;
    }

    /**
     * @param name   - имя игры
     * @param place  - место (кафешка и т.п)
     * @param opened - открыта ли комната
     */
    public GameModel(String name, String place, boolean opened) {
        this.name = name;
        this.place = place;
        this.opened = opened;
    }
}
