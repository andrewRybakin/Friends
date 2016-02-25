package ru.dzen.friends;

import android.content.Context;
import android.support.multidex.MultiDex;

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

    //Нада было чтоб на 4.х не падало о_0 Как выяснилось, на андроидах ниже 5 версии падало при инициализации вкапи><
    //Походу они реально там х*як х*як и в продакшн...
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
