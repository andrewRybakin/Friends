package ru.dzen.friends;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.dzen.friends.controllers.RemoteController;
import ru.dzen.friends.supervisor.SupervisorController;
import ru.dzen.friends.supervisor.SupervisorService;

public class GameFragment extends Fragment {

    public static final String TAG = "ru.dzen.friends.GameFragment";
    public static final String DEVICE_ACTIVATED = "ru.dzen.friends.DeviceActivated";
    public static final String DEVICE_DEACTIVATED = "ru.dzen.friends.DeviceDeActivated";
    public static final String GAME_STARTED = "ru.dzen.friends.GameStarted";
    public static final String GAME_STOPPED = "ru.dzen.friends.GameStopped";
    private static final BroadcastReceiver GAME_STOPPED_BROADCAST;
    private Intent serviceIntent;

    static {
        GAME_STOPPED_BROADCAST = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                float score = intent.getExtras().getFloat(SupervisorController.EXTRA_SCORE);
                RemoteController.getInstance().sendResult();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        TextView tw = (TextView) v.findViewById(R.id.your_score);
        tw.setText(R.string.game_is_going_on);
        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceIntent = new Intent(getActivity(), SupervisorService.class);
        getActivity().startService(serviceIntent);
        IntentFilter filter = IntentFilter.create(GAME_STOPPED, "text/*");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(GAME_STOPPED_BROADCAST, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(GAME_STOPPED_BROADCAST);
        getActivity().stopService(serviceIntent);
    }
}
