package com.ccbfm.android.permission;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * @author ccbfm
 */
public class PermissionFragment extends Fragment {

    private static final String KEY_PERMISSIONS = "key_permissions";
    private static final int REQUEST_CODE = 0x1222;

    private PermissionCallback mPermissionCallback;
    private boolean mRequestFinish = true;

    static PermissionFragment instance() {
        return new PermissionFragment();
    }

    void setPermissionCallback(String[] permissions, PermissionCallback permissionCallback) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(KEY_PERMISSIONS, permissions);
        this.setArguments(bundle);
        mPermissionCallback = permissionCallback;

        if (getActivity() != null) {
            requestPermissions();
        }
    }

    public PermissionFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        requestPermissions();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mRequestFinish) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                String[] requestPermissions = bundle.getStringArray(KEY_PERMISSIONS);

                if (requestPermissions != null && requestPermissions.length != 0) {
                    if (mPermissionCallback != null) {
                        mPermissionCallback.onPermissionGranted(false);
                    }
                    mRequestFinish = false;

                    requestPermissions(requestPermissions, REQUEST_CODE);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != REQUEST_CODE || permissions == null || grantResults == null) {

            return;
        }
        mRequestFinish = true;
        handleResult(permissions, grantResults);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void handleResult(String[] permissions, int[] grantResults) {
        ArrayList<String> listPermission = null;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (listPermission == null) {
                    listPermission = new ArrayList<>();
                }
                listPermission.add(permissions[i]);
            }
        }

        if (mPermissionCallback != null) {
            if (listPermission == null) {
                getArguments().remove(KEY_PERMISSIONS);
                mPermissionCallback.onPermissionGranted(true);
            } else {
                mPermissionCallback.onPermissionsDenied(getActivity(), listPermission.toArray(new String[0]));
            }
        }
    }

}
