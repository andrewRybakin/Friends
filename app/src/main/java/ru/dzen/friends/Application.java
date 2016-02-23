package ru.dzen.friends;

import com.vk.sdk.VKSdk;

/**
 * Created by ivan on 22.02.16.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
