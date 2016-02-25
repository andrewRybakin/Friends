package ru.dzen.friends.controllers;

import java.util.ArrayList;
import java.util.Random;

import ru.dzen.friends.models.GameModel;

public class RemoteController {

    private static RemoteController instance;

    /**
     * При первом получении instance пытается подключиться к серверу
     *
     * @return Instance - при удачном подключении
     * null - при неудачном подключении
     */
    public static RemoteController getInstance() {
        if (instance == null)
            instance = new RemoteController();
        return instance;
    }

    private RemoteController() {
        /*
        Попытаться подключиться к серверу
         */
    }

    /**
     * Залогиниться!
     *
     * @return true - если залогинился|
     * false - если не залогинился
     */
    public boolean login() {
        return true;
    }

    /**
     * Запросить у сервера список игр в моей локации
     *
     * @return Список игр
     * @deprecated ПОКА ЧТО С ТЕСТОВОЙ ЗАГЛУШКОЙ
     */
    public ArrayList<GameModel> getGamesAroundMe() {
        ArrayList<GameModel> testArrayList = new ArrayList<>();
        for (int i = 0; i < 1.5 * ((new Random()).nextGaussian()) + 10; i++) {
            testArrayList.add(new GameModel("GameModel " + i, "Some place", (new Random()).nextBoolean()));
        }
        return testArrayList;
    }

}
