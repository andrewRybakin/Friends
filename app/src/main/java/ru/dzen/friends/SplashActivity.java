package ru.dzen.friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashActivity";
    private final String THREAD_STARTED = "ru.dzen.friends.isThreadStarted";
    private boolean isThreadStarted;
    private Thread splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        /*String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        Log.d("myLog", Arrays.toString(fingerprints));*/
        if (savedInstanceState != null)
            isThreadStarted = savedInstanceState.getBoolean(THREAD_STARTED, false);
        if (!isThreadStarted) {
            splash = new Thread(new Runnable() {
                @Override
                public void run() {
                    isThreadStarted = true;
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });
            splash.start();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(THREAD_STARTED, isThreadStarted);
    }

    @Override
    protected void onPause() {
        super.onPause();
        splash.interrupt();
        finish();
    }

}
