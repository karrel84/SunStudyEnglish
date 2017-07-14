package com.karrel.sunstudyenglish.base.view;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by rell on 2017-07-14.
 */

public class BaseApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
