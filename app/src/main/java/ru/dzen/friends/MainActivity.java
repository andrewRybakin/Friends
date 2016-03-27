package ru.dzen.friends;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_FRAGMENT_KEY = "currentFragmentTag";
    private static final String LOG_TAG = "MainActivity";

    private String currentFragmentTag;
    private FrameLayout fl;
    private FragmentManager fm;

    private Fragment createProperFragment(String fragmentTag) {
        switch (fragmentTag) {
            case SearchGameFragment.TAG:
                return new SearchGameFragment();
            case RoomFragment.TAG:
                return new RoomFragment();
            case GameFragment.TAG:
                return new GameFragment();
            default:
                return new SearchGameFragment();
        }
    }

    public void changeFragment(String fragmentTag) {
        Fragment f = fm.findFragmentByTag(fragmentTag);
        if (f == null)
            f = createProperFragment(fragmentTag);
        fm.beginTransaction().replace(fl.getId(), f, fragmentTag).commit();
        currentFragmentTag = fragmentTag;
    }

    public void changeFragment(String fragmentTag, Bundle bundle) {
        Fragment f = fm.findFragmentByTag(fragmentTag);
        if (f == null)
            f = createProperFragment(fragmentTag);
        f.setArguments(bundle);
        fm.beginTransaction().replace(fl.getId(), f, fragmentTag).commit();
        currentFragmentTag = fragmentTag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fl = new FrameLayout(this);
        fl.setId(R.id.main_frame_layout);
        if (savedInstanceState != null)
            currentFragmentTag = savedInstanceState.getString(CURRENT_FRAGMENT_KEY);
        else
            currentFragmentTag = SearchGameFragment.TAG;
        fm = getFragmentManager();
        changeFragment(SearchGameFragment.TAG);
        setContentView(fl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "Destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_FRAGMENT_KEY, currentFragmentTag);
    }
}