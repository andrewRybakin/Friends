package ru.dzen.friends;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.dzen.friends.supervisor.SupervisorService;

public class GameFragment extends Fragment {

    public static final String TAG = "ru.dzen.friends.GameFragment";
    public static final String DEVICE_ACTIVATED = "ru.dzen.friends.DeviceActivated";
    public static final String DEVICE_DEACTIVATED = "ru.dzen.friends.DeviceDeActivated";
    public static final String GAME_STARTED = "ru.dzen.friends.GameStarted";
    public static final String GAME_STOPPED = "ru.dzen.friends.GameStarted";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        TextView tw = (TextView) v.findViewById(R.id.your_score);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().startService(new Intent(getActivity(), SupervisorService.class));
    }
}
