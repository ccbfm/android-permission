package com.ccbfm.android.permission;

import android.app.Application;
import android.util.Log;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        AndroidPermissionTool.init();
    }


}
