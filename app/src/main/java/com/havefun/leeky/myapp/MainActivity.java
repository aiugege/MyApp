package com.havefun.leeky.myapp;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.havefun.leeky.myapp.bean.MessageEvent;
import com.havefun.leeky.myapp.bean.Translation;
import com.havefun.leeky.myapp.bean.Translation1;
import com.havefun.leeky.myapp.request_interface.GetRequest_Interface;
import com.havefun.leeky.myapp.request_interface.PostRequest_Interface;
import com.havefun.leeky.myapp.util.DeviceUuidFactory;
import com.havefun.leeky.myapp.util.Logger;
import com.havefun.leeky.myapp.view.CustomCircleProgressBar;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    String currentTime = "";
    private TextView textView;
    private CustomCircleProgressBar progressBar;

    private static final String TAG = "HmsPushLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
//        getRequest();

//        postRequest();

        EventBus.getDefault().register(this);

        textView = findViewById(R.id.my_textview);
        progressBar = (CustomCircleProgressBar) findViewById(R.id.am_progressbar);
        progressBar.setProgress(50);
        currentTime = getNowTime();
        Log.i("Main", "Main--onCreate  " + currentTime);

//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this, EventMessageActivity.class);
//                startActivity(intent);
//            }
//        }, 5000);

        getToken();

        // 获取设备唯一UUID
        Logger.d("DeviceUuid", "获取UUID：" + DeviceUuidFactory.getInstance(this).getDeviceUuid());
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.i("Main", "Main--onStart  " + currentTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.i("Main", "Main--onResume  " + currentTime);
    }

    public String getNowTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    private void getRequest() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")    // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<Translation> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 步骤7：处理返回的数据结果
                response.body().show();
                Log.i("getResult", "status:" + response.body().getStatus()
                        + ", content:" + response.body().getContent().getOut());
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                System.out.println("连接失败");
            }
        });
    }

    private void postRequest() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);

        //对 发送请求 进行封装(设置需要翻译的内容)
        Call<Translation1> call = request.getCall("I love you");

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation1>() {

            //请求成功时回调
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                // 步骤7：处理返回的数据结果：输出翻译的内容
                Log.i("postResult", response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation1> call, Throwable throwable) {
                System.out.println("请求失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventPost(MessageEvent event) {
//        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
        Log.i("Main", "onEventPost  " + currentTime);
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        textView.setText(event.getMessage());
//        EventBus.getDefault().cancelEventDelivery(event);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, EventMessageActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Main", "onDestroy  " + currentTime);
        EventBus.getDefault().unregister(this);
    }

    private void getToken() {
        new Thread() {
            @Override
            public void run() {
                try {
                    // read from agconnect-services.json
                    String appId = AGConnectServicesConfig.fromContext(mContext).getString("client/app_id");
                    String token = HmsInstanceId.getInstance(mContext).getToken(appId, "HCM");
                    Log.i(TAG, "get token:" + token);
                    if(!TextUtils.isEmpty(token)) {
                        sendRegTokenToServer(token);
                    }
                } catch (ApiException e) {
                    Log.e(TAG, "get token failed, " + e);
                }
            }
        }.start();
    }
    private void sendRegTokenToServer(String token) {
        Log.i(TAG, "sending token to server. token:" + token);
    }
}
