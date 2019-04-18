package com.ccbfm.android.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ccbfm
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface APermission {

    /**
     * 获取申请权限数组
     *
     * @return 申请权限数组
     */
    String[] permissions();

}
