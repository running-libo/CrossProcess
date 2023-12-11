package com.example.crossprocess;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.RequiresApi;

public class MyApplication extends Application {
    private static final String WEBVIEW_PROCESS = ":webview";
    private static final String AUDIOPLAYER_PROCESS = ":player";

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate() {
        super.onCreate();

        initMultiProcessApplication(getProcessName());  //getProcessName()当前进程名
    }

    /**
     * 选择性初始化application
     * @param processName
     */
    private void initMultiProcessApplication(String processName) {
        String packageName = getPackageName(); //主进程名就是包名

        if (TextUtils.equals(processName, packageName)) {
            //主进程,需要进程初始化
            Log.i("minfo", "init process " + packageName);
        } else if (TextUtils.equals(processName, packageName + WEBVIEW_PROCESS)) {
            //webView进程,不需要进程初始化
            Log.i("minfo", "init process " + WEBVIEW_PROCESS);
            return;
        } else if (TextUtils.equals(processName, packageName + AUDIOPLAYER_PROCESS)) {
            //播放器进程,需要进程初始化
            Log.i("minfo", "init process " + AUDIOPLAYER_PROCESS);
        }

        initApp();
    }

    /**
     * Application初始化
     */
    private void initApp() {

    }
}
