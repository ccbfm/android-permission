package com.ccbfm.android.permission;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.util.Log;

/**
 * @author ccbfm
 */
public class AndroidPermission {
    private static final String TAG_FRAGMENT = "permission_fragment";
    private FragmentManager mFragmentManager;
    private String[] mPermissions;
    private PermissionCallback mPermissionCallback;

    private static PermissionDeniedHintAdapter sDeniedAdapter;

    public static void init(PermissionDeniedHintAdapter callback) {
        sDeniedAdapter = callback;
    }

    static PermissionDeniedHintAdapter getDeniedAdapter() {
        return sDeniedAdapter;
    }

    private AndroidPermission(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public static AndroidPermission with(Activity activity) {
        return new AndroidPermission(activity.getFragmentManager());
    }

    public AndroidPermission permission(String[] permissions) {
        this.mPermissions = permissions;
        return this;
    }

    public AndroidPermission callback(PermissionCallback callback) {
        this.mPermissionCallback = callback;
        return this;
    }

    public boolean request() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || mPermissions == null || mPermissions.length == 0) {
            return true;
        }
        Fragment fragment = mFragmentManager.findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null) {
            fragment = PermissionFragment.instance();
            mFragmentManager.beginTransaction()
                    .add(fragment, TAG_FRAGMENT)
                    .commitAllowingStateLoss();
        }
        Log.d("wds", "request---"+fragment);
        if (fragment instanceof PermissionFragment) {
            ((PermissionFragment) fragment).setPermissionCallback(
                    mPermissions, mPermissionCallback);
        }
        return false;
    }
}
