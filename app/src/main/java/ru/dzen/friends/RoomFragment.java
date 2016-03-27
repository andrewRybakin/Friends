package ru.dzen.friends;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.dzen.friends.controllers.RemoteController;
import ru.dzen.friends.models.RoomModel;
import ru.dzen.friends.models.RoomParticipant;

public class RoomFragment extends ListFragment {

    public static final String TAG = "ru.dzen.friends.RoomFragment";
    public static final String ROOM_MODEL = "ru.dzen.friends.RoomModel";
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_room, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.search_fab);
        //Показывать только админу. У остальных игра начинается по команде с сервера
        //TODO: Прятать fab для неадмина
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteController.getInstance().startGame(getActivity(), (RoomModel) getArguments().getParcelable(ROOM_MODEL));
            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new RoomAdapter(getActivity(),
                RemoteController.getInstance().askForParticipants()));
    }

    private class RoomAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private int convertViewResourceId;
        private ArrayList<RoomParticipant> listOfParticipants;

        public RoomAdapter(Context c, ArrayList<RoomParticipant> listOfParticipants) {
            this.convertViewResourceId = R.layout.listitem_fragment_room;
            this.listOfParticipants = listOfParticipants;
            this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return listOfParticipants.size();
        }

        @Override
        public Object getItem(int position) {
            return listOfParticipants.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView == null)
                v = inflater.inflate(convertViewResourceId, parent, false);
            else
                v = convertView;
            TextView name = (TextView) v.findViewById(R.id.name);
            RoomParticipant item = (RoomParticipant) getItem(position);
            name.setText(item.getName());
            return v;
        }
    }
}
