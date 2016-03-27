package ru.dzen.friends.supervisor;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import ru.dzen.friends.GameFragment;

public class SupervisorController {
    public static final String EXTRA_SCORE = "superVisorControllerScore";

    private static SupervisorController ourInstance = new SupervisorController();
    private float score;
    private long lastTimeActive, lastTimePassive;

    public static SupervisorController getInstance() {
        return ourInstance;
    }

    private SupervisorController() {
        score = 0;
        lastTimeActive = 0;
        lastTimePassive = 0;
    }

    public void onDeviceActivated(long time, Context c) {
        Log.d("ПриветКрл1", "Девайс активирован");
        lastTimeActive = time;
        score = countScore(score, lastTimeActive, lastTimePassive);
        LocalBroadcastManager.getInstance(c).sendBroadcast(
                (new Intent()).setAction(GameFragment.DEVICE_ACTIVATED).putExtra(EXTRA_SCORE, score).setType("text/*")
        );
    }

    private float countScore(float score, long lastTimeActive, long lastTimePassive) {
        return 0;
    }

    public void onDeviceDeActivated(long time, Context c) {
        Log.d("ПриветКрл1", "Девайс ДЕактивирован");
        lastTimePassive = time;
        if (lastTimeActive != 0)
            score = countScore(score, lastTimeActive, lastTimePassive);
        LocalBroadcastManager.getInstance(c).sendBroadcast(
                (new Intent()).setAction(GameFragment.DEVICE_DEACTIVATED).putExtra(EXTRA_SCORE, score).setType("text/*")
        );
    }

    public void onGameStart(long time, Context c) {
        Log.d("ПриветКрл1", "Игра запущена");
        score = 0;
        lastTimeActive = 0;
        lastTimePassive = 0;
        LocalBroadcastManager.getInstance(c).sendBroadcast(
                (new Intent()).setAction(GameFragment.GAME_STARTED).setType("text/*")
        );
    }

    public void onGameEnd(long time, Context c) {
        Log.d("ПриветКрл1", "Игра остановлена");
        LocalBroadcastManager.getInstance(c).sendBroadcast(
                (new Intent()).setAction(GameFragment.GAME_STOPPED).putExtra(EXTRA_SCORE, score).setType("text/*")
        );
    }
}
