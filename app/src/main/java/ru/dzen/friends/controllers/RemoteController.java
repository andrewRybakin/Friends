package ru.dzen.friends.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import ru.dzen.friends.GameFragment;
import ru.dzen.friends.MainActivity;
import ru.dzen.friends.RoomFragment;
import ru.dzen.friends.models.GameModel;
import ru.dzen.friends.models.Greeting;
import ru.dzen.friends.models.RoomModel;
import ru.dzen.friends.models.RoomParticipant;

public class RemoteController {

    private static final String LOG_TAG = "RemoteController";
    private static RemoteController instance;
    public static final String SERVER_OFFLINE_ACTION = "ru.dzen.friends.controllers.RemoteController.serverNotResponding";
    public static final Intent SERVER_OFFLINE_INTENT = new Intent(SERVER_OFFLINE_ACTION);
    public static final String SERVER_ONLINE_ACTION = "ru.dzen.friends.controllers.RemoteController.serverResponding";
    public static final Intent SERVER_ONLINE_INTENT = new Intent(SERVER_ONLINE_ACTION);

    static {
        SERVER_OFFLINE_INTENT.setType("text/*");//Я честно хз зачем, но без этого дерьма фильтр не работает >< Опытным путем выяснено-_- Ну или руки кривые:(
        SERVER_ONLINE_INTENT.setType("text/*");
    }

    /**
     * При первом получении instance пытается подключиться к серверу
     *
     * @return Instance - при удачном подключении
     */
    public static RemoteController getInstance() {
        if (instance == null)
            instance = new RemoteController();
        return instance;
    }

    private RemoteController() {
    }

    /**
     * Залогиниться!
     */
    public void login(String vkId, String gPlusId) {
        //TODO Получить от сервера экземпляр класса RemoteUserModel и засторить в preferences id от сервера. Обращаться к /login?vkId= или /login?email=
    }

    /**
     * Щупалка сервера. Так же служит, как пример подключения к серверу и получения от него данных
     */
    public void canYouFeelMyServer(final Context context) {
        final WeakReference<Context> c = new WeakReference<>(context);
        HttpRequestTask<Greeting> feelTask = new HttpRequestTask<>(Greeting.class, new HttpRequestTask.OnPostExecute<Greeting>() {
            @Override
            public void onPostExecute(Greeting o) {
                Log.d(LOG_TAG, "Сникерс");
                if (o == null) {
                    Log.d(LOG_TAG, "Сникерс не существует:(");
                    LocalBroadcastManager.getInstance(c.get()).sendBroadcast(SERVER_OFFLINE_INTENT);
                } else {
                    LocalBroadcastManager.getInstance(c.get()).sendBroadcast(SERVER_ONLINE_INTENT);
                    Log.d(LOG_TAG, "Сникерс существует!!!");
                }
            }
        });
        feelTask.execute("/greeting");
    }

    /**
     * Запросить у сервера список игр в моей локации
     *
     * @return Список игр
     * @deprecated ПОКА ЧТО С ТЕСТОВОЙ ЗАГЛУШКОЙ
     */
    public ArrayList<GameModel> getGamesAroundMe() {
        // TODO Вытянуть реальный список игр
        ArrayList<GameModel> testArrayList = new ArrayList<>();
        for (int i = 0; i < 1.5 * ((new Random()).nextGaussian()) + 10; i++) {
            testArrayList.add(new GameModel("GameModel " + i, "Some place", (new Random()).nextBoolean()));
        }
        return testArrayList;
    }

    public ArrayList<RoomParticipant> askForParticipants() {
        // TODO Получение списка участников комнаты. Надо придумать способ обновления списков всех..
        ArrayList<RoomParticipant> testArrayList = new ArrayList<>();
        for (int i = 0; i < 1.5 * ((new Random()).nextGaussian()) + 10; i++) {
            testArrayList.add(new RoomParticipant("someemail@gmail.com", "Name " + i));
        }
        return testArrayList;
    }

    public RoomModel createRoom(String roomName, boolean isItOpen) {
        // TODO Создание комнаты на серве
        return new RoomModel(100, "name");
    }

    public RoomModel getRoom(int id) {
        return new RoomModel(id, "aaa");
    }

    public void startGame(Activity mainActivity, RoomModel room) {
        //TODO Команда запуска игры на сервер. Надо его периодически опрашивать об окончании игры. Криво, но на пока сойдет
        //Отправить на серв
        Bundle bndl = new Bundle();
        bndl.putParcelable(RoomFragment.ROOM_MODEL, room);
        ((MainActivity) mainActivity).changeFragment(GameFragment.TAG, bndl);
    }

    public void sendResult() {
        //TODO Отправить свой скор по окончании игры
    }
}