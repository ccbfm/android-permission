package com.ccbfm.android.permission;

import android.app.Activity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author ccbfm
 */
@Aspect
public class PermissionAspect {


    @Around("execution(@com.ccbfm.android.permission.APermission * *(android.app.Activity, ..))")
    public void checkPermission(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (joinPoint.getSignature() instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();

            APermission aPermission = method.getAnnotation(APermission.class);
            String[] permissions = aPermission.permissions();

            if (permissions.length == 0) {
                joinPoint.proceed();
                return;
            }

            Activity activity;
            Object[] object = joinPoint.getArgs();
            if (object[0] instanceof Activity) {
                activity = (Activity) object[0];
            } else {
                throw new NullPointerException("Activity is null!");
            }

            boolean result = AndroidPermission.with(activity)
                    .permission(permissions)
                    .callback(new PermissionCallback() {
                        @Override
                        public void onPermissionGranted(boolean granted) {
                            if (AndroidPermission.getDeniedAdapter() != null) {
                                AndroidPermission.getDeniedAdapter()
                                        .onPermissionDeniedHintHide();
                            }
                            if (granted) {
                                try {
                                    joinPoint.proceed();
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onPermissionsDenied(Activity activity, String[] permission) {
                            if (AndroidPermission.getDeniedAdapter() != null) {
                                AndroidPermission.getDeniedAdapter()
                                        .onPermissionDeniedHintShow(activity, permission);
                            }
                        }
                    }).request();

            if (result) {
                joinPoint.proceed();
            }
        } else {
            joinPoint.proceed();
        }
    }
}
