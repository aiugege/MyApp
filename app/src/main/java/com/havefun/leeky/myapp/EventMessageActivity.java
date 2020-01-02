package com.havefun.leeky.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.havefun.leeky.myapp.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class EventMessageActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventmessage);

    }


    @Override
    public void onClick(View view) {
        EventBus.getDefault().post(new MessageEvent("hello"));
    }
}
