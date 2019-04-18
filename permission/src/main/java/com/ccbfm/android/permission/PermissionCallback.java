package com.ccbfm.android.permission;


import android.app.Activity;

/**
 * @author ccbfm
 */
public interface PermissionCallback {

    /**
     * 权限通过
     *
     * @param granted true 权限通过; false 伪成功，可处理UI显示
     */
    void onPermissionGranted(boolean granted);

    /**
     * 拒绝权限申请处理
     */
    void onPermissionsDenied(Activity activity, String[] permissions);
}
