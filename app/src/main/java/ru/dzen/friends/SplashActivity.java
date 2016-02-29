package ru.dzen.friends;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ru.dzen.friends.controllers.RemoteController;

public class SplashActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SplashActivity";
    private final String TAG = "SplashActivity";
    private final String THREAD_STARTED = "ru.dzen.friends.isThreadStarted";
    private boolean isThreadStarted;
    private Thread splash;
    private static boolean dontStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (savedInstanceState != null)
            isThreadStarted = savedInstanceState.getBoolean(THREAD_STARTED, false);
        if (!isThreadStarted) {
            android.os.Handler h = new android.os.Handler();

            splash = new Thread(new Runnable() {
                @Override
                public void run() {
                    isThreadStarted = true;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    Log.d(TAG, "Don't start? " + dontStart);
                    if (!dontStart) {
                        IntentFilter filter = IntentFilter.create(RemoteController.SERVER_OFFLINE_ACTION, "text/*");
                        filter.addAction(RemoteController.SERVER_ONLINE_ACTION);
                        LocalBroadcastManager.getInstance(SplashActivity.this).registerReceiver(new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                if (intent.getAction().equals(RemoteController.SERVER_OFFLINE_ACTION)) {
                                    (new AlertDialog.Builder(SplashActivity.this))
                                            .setTitle(R.string.error)
                                            .setMessage(R.string.server_not_responsing)
                                            .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    finish();
                                                }
                                            })
                                            .setCancelable(false)
                                            .show();
                                } else {
                                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }, filter);
                        RemoteController.getInstance().canYouFeelMyServer(SplashActivity.this);
                    }
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
    protected void onDestroy() {
        super.onDestroy();
        dontStart = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        dontStart = false;
    }
}
