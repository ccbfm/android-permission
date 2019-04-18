android-permission


allprojects {

    repositories {

        maven { url 'https://jitpack.io'}
    
    }
}

dependencies {

    implementation "com.github.ccbfm:android-permission:1.0.1"
}


1.常规使用 AndroidPermission.with(context).permission(permissions).callback(callback).request() 有个返回值 为ture直接进行下一步，否则需要callback中处理

2.使用AOP需要引入Aspectj依赖 ccbfm/android-aspectj-plugin

    // 添加申请权限失败回调
    AndroidPermission.init(new PermissionDeniedHintAdapter() {

        @Override
        public void onPermissionDeniedHintHide() {

        }

        @Override
        public void onPermissionDeniedHintShow(Activity activity, String[] permissions) {

        }
    });
    
    
    //需要申请权限的地方 添加注解并加入所需权限 方法需要第一个参数为Activity
    @APermission(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    private void checkPermission(Activity activity) {
        initViews();
    }
