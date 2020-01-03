package com.havefun.leeky.myapp.receiver;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.havefun.leeky.myapp.R;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.jpush.android.service.PluginXiaomiPlatformsReceiver;

/**
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 XMMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.XMMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、XMMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、XMMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、XMMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、XMMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、XMMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 *
 * Created by Leeky on 2020/1/2.
 * 小米推送消息接收类
 */
public class XMMessageReceiver extends PushMessageReceiver {

    private String mRegId;
    private String mTopic;
    private String mAlias;
    private String mAccount;
    private String mStartTime;
    private String mEndTime;

    private static final String TAG = "XMPushLog";

    final PluginXiaomiPlatformsReceiver receiver = new PluginXiaomiPlatformsReceiver();

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {

        Log.d(TAG, "onReceivePassThroughMessage is called. " + message.toString());
        receiver.onReceivePassThroughMessage(context, message);
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {

        Log.d(TAG, "onNotificationMessageClicked is called. " + message.toString());
        receiver.onNotificationMessageClicked(context, message);
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {

        Log.d(TAG, "onNotificationMessageArrived is called. " + message.toString());
        receiver.onNotificationMessageArrived(context, message);
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {

        Log.d(TAG, "onCommandResult is called. " + message.toString());
        receiver.onCommandResult(context, message);
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {

        Log.d(TAG, "onReceiveRegisterResult is called. " + message.toString());
        receiver.onReceiveRegisterResult(context, message);
    }

    @Override
    public void onRequirePermissions(Context context, String[] permissions) {
        super.onRequirePermissions(context, permissions);

    }

}
