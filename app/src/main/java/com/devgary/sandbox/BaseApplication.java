package com.devgary.sandbox;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Gary on 2016-11-27.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
