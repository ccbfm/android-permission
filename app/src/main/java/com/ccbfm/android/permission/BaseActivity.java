package com.ccbfm.android.permission;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (RestartUtils.getInstance().getAppStatus()) {
            case RestartUtils.STATUS_FORCE_KILLED:
                restartApp();
                break;
            case RestartUtils.STATUS_NORMAL:
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void restartApp() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
