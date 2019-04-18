package com.ccbfm.android.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

public class StartActivity extends BaseActivity {
    private static final String TAG = "StartActivity";
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPermission(this, "str");
    }

    @APermission(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS})
    private void initPermission(Activity activity, String str) {
        Log.d(TAG, "initPermission---");
        initPermission2(activity, str);
    }

    @APermission(permissions = {Manifest.permission.CAMERA})
    private void initPermission2(Activity activity, String str) {
        Log.d(TAG, "initPermission2---");
        needRun();
    }

    private void needRun() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                flag = true;
            }
        }, 3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (flag) {
            finish();
        }
    }
}
