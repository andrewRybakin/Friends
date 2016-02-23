package ru.dzen.friends;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_FRAGMENT_KEY = "currentFragmentTag";

    private String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout fl = new FrameLayout(this);
        fl.setId(R.id.main_frame_layout);
        if (savedInstanceState != null)
            currentFragmentTag = savedInstanceState.getString(CURRENT_FRAGMENT_KEY);
        else
            currentFragmentTag = SearchGameFragment.TAG;
        FragmentManager fm = getFragmentManager();
        Fragment f = fm.findFragmentByTag(currentFragmentTag);
        if (f == null)
            switch (currentFragmentTag) {
                case SearchGameFragment.TAG:
                    fm.beginTransaction()
                            .replace(fl.getId(), new SearchGameFragment())
                            .commit();
                    setTitle(R.string.searching_game);
                    break;
                //Далее будет добавлено
            }
        setContentView(fl);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_FRAGMENT_KEY, currentFragmentTag);
    }

}
