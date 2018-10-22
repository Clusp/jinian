package com.aoao.jinian;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by asus on 2018/9/8.
 */

public class Robot extends AppCompatActivity {

    private TextView mTextView;
    private SensorManager mSensorManager;
    private Sensor light;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.robot);
        mTextView = (TextView) findViewById(R.id.light_tv);
        mSensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        light = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                mTextView.setText("光线强度："+x);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, light, SensorManager.SENSOR_DELAY_UI);
    }
}
