package com.havefun.leeky.myapp.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.havefun.leeky.myapp.BuildConfig;
import com.havefun.leeky.myapp.annotation.AspectAnalyze;
import com.havefun.leeky.myapp.annotation.AspectTrace;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import cn.jpush.android.api.JPushInterface;

public class MyApp extends Application {

    private static final String TAG = "MyApplication";
    private static final String MIPUSHTAG = "MiPush";

    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()) {
            A();
        }
        JPushInterface.setDebugMode(BuildConfig.DEBUG); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        MiPushClient.registerPush(this, "2882303761518296822", "5551829696822");    // 初始化小米推送，AppID和AppKey
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(MIPUSHTAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(MIPUSHTAG, content);
            }
        };
        Logger.setLogger(this, newLogger);

        AspectTrace.setAspectTraceListener(new AspectTrace.AspectTraceListener() {
            @Override
            public void logger(String tag, String message) {
                Log.e(tag, message);
            }

            @Override
            public void onAspectAnalyze(ProceedingJoinPoint joinPoint, AspectAnalyze aspectAnalyze, MethodSignature methodSignature, long duration) {
                Log.e("onAspectAnalyze", aspectAnalyze.name());
            }
        });
    }


    private void A() {
        Log.i(TAG, getCurrentProcessName() + ",调用A方法");
    }

    /**
     * 获取当前进程名
     */
    private String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }

    /**
     * 包名判断是否为主进程
     *
     * @param
     * @return
     */
    public boolean isMainProcess() {
        return getApplicationContext().getPackageName().equals(getCurrentProcessName());
    }
}
