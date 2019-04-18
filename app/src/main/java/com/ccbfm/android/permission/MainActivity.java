package com.ccbfm.android.permission;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


/**
 * 杀死java进程 taskkill /im java.exe -f
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick---" + v);
            }
        });


        //initPermission(this, "str");

        Log.d(TAG, "MainActivity-onCreate---");
        //check();
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

    private void check() {
        Log.d(TAG, "check---");
        boolean flag = AndroidPermission.with(this)
                .permission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS})
                .callback(new PermissionCallback() {
                    @Override
                    public void onPermissionGranted(boolean granted) {
                        if(granted) {
                            needRun();
                        }
                    }

                    @Override
                    public void onPermissionsDenied(Activity activity, String[] permissions) {
                        StringBuilder sb = new StringBuilder(permissions.length);
                        for (String str : permissions) {
                            sb.append(" ").append(str).append(" ");
                        }
                        String str = "以下权限授权失败：\n" + sb.toString();
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    }
                }).request();
        if (flag) {
            needRun();
        }
    }

    private void needRun() {
        Toast.makeText(getApplicationContext(), "正常执行。。。", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "MainActivity-onStart---");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(TAG, "MainActivity-onResume---");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(TAG, "MainActivity-onPause---");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(TAG, "MainActivity-onStop---");
    }
}
