package com.example.signgg.signgg.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by xiong on 2017/8/16.
 */

public class MyApplication extends Application {

    public static Context mcontext;

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext=this;
    }
}
