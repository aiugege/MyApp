package com.havefun.leeky.myapp.service;

import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

/**
 * Created by Leeky on 2019-12-27 16:00
 * Note:
 */
public class MyHmsService extends HmsMessageService {

    private static final String TAG = "HmsPushLog";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "receive token:" + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.i(TAG, "onMessageReceived is called");
        if (remoteMessage == null) {
            Log.e(TAG, "Received message entity is null!");
            return;
        }

        if (remoteMessage.getData().length() > 0) {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }

    @Override
    public void onMessageSent(String s) {
    }

    @Override
    public void onSendError(String s, Exception e) {
    }


}
