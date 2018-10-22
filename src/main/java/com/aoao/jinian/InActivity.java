package com.aoao.jinian;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by asus on 2018/8/28.
 */

public class InActivity extends AppCompatActivity {

    private ImageView launcherimg;
    private TextView launchertv;
    private Handler mHandler;
    private CountDownTimer mCountDownTimer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launchermain);
         Bmob.initialize(this, "453e84fede0fe7933da8a36d1650ea3c");
        launcherimg = (ImageView) findViewById(R.id.launcher_image);
        launchertv = (TextView) findViewById(R.id.launcher_tv);
        mHandler = new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                mCountDownTimer=new CountDownTimer(3850,10) {
                    @Override
                    public void onTick(long l) {
                        launchertv.setText(l/1000+"秒");
                    }
                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(InActivity.this,JinianActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.launcher_in, R.anim.launcher_out);
                        finish();
                    }
                };
                mCountDownTimer.start();
            }
        };
        mHandler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }


}
