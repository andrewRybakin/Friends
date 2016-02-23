package ru.dzen.friends.vkauth;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

/**
 * Created by ivan on 30.01.16.
 */
public class Account {
    public String accessToken;
    public long userId;
    private static final String PARAM_TOKEN = "access_token";
    private static final String PARAM_USER_ID = "user_id";

    public void save(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PARAM_TOKEN, accessToken);
        editor.putLong(PARAM_USER_ID, userId);
        editor.commit();
    }

    public void restore(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        accessToken = preferences.getString(PARAM_TOKEN, null);
        userId = preferences.getLong(PARAM_USER_ID, -1);
    }
}
