package ru.dzen.friends;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import ru.dzen.friends.controllers.RemoteController;
import ru.dzen.friends.models.GameModel;
import ru.dzen.friends.models.RoomModel;

public class SearchGameFragment extends ListFragment {

    // TODO: Серьезно подумать над технологией обновления списка... GCM - из пушки по воробьям, но на крайний случай сойдет

    public static final String TAG = "ru.dzen.friends.SearchGameFragment";

    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        final EditText editText = new EditText(getActivity());
        fab = (FloatingActionButton) v.findViewById(R.id.search_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new AlertDialog.Builder(getActivity())).setTitle(R.string.set_room_name)
                        .setView(editText).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RoomModel rModel = RemoteController.getInstance().createRoom(editText.getText().toString(), true);
                        Bundle args = new Bundle();
                        args.putParcelable(RoomFragment.ROOM_MODEL, rModel);
                        ((MainActivity) getActivity()).changeFragment(RoomFragment.TAG, args);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new GamesAdapter(getActivity(),
                RemoteController.getInstance().getGamesAroundMe()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private class GamesAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private int convertViewResourceId;
        private ArrayList<GameModel> listOfGames;

        public GamesAdapter(Context c, ArrayList<GameModel> listOfGames) {
            this.convertViewResourceId = R.layout.listitem_fragment_search;
            this.listOfGames = listOfGames;
            this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return listOfGames.size();
        }

        @Override
        public Object getItem(int position) {
            return listOfGames.get(position);
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
            TextView place = (TextView) v.findViewById(R.id.place);
            ImageView status = (ImageView) v.findViewById(R.id.status);
            GameModel item = (GameModel) getItem(position);
            name.setText(item.getName());
            place.setText(item.getPlace());
            status.setImageDrawable(
                    item.isOpened() ?
                            getResources().getDrawable(R.drawable.server_opened_search) :
                            getResources().getDrawable(R.drawable.server_closed_search)
            );
            return v;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.initiateScan();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            Snackbar.make(getView(), "Joining the game with id: " + scanResult.getContents(), Snackbar.LENGTH_SHORT).show();
        }
    }
}
