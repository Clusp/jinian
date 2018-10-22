package com.aoao.jinian;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by asus on 2018/9/7.
 */

public class HelpAndSupport extends AppCompatActivity {
    private TextView mSahre_ok;
    private TextView mPay_ok;
    private ImageView mPayImg;
    private CardView mCardView;
    private Boolean isShowOrNot=false;
    private ActionBar actionBar;
    private PackageManager mPackageManager;
    private PackageInfo mPackageInfo;
    private ApplicationInfo mApplicationInfo;
    private final static String mapp = "com.aoao.jinian";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_support);
        actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.zhu );
        }
        mSahre_ok = (TextView) findViewById(R.id.share_ok);
        mSahre_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mPackageManager = getPackageManager();
                            mPackageInfo = mPackageManager.getPackageInfo(mapp, 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        File apkfile = new File(mPackageInfo.applicationInfo.sourceDir);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("*/*");
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(apkfile));
                        startActivity(intent);
                    }
                }).start();
            }
        });
        mPay_ok = (TextView) findViewById(R.id.donate_ok);
        mPayImg = (ImageView) findViewById(R.id.pay_img);
        mPay_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowOrNot == false)
                {
                    // 设置显示
                    mPayImg.setVisibility(View.VISIBLE);
                    isShowOrNot = true;
                }
                else
                {
                    // 设置隐藏
                    mPayImg.setVisibility(View.GONE);
                    isShowOrNot  = false;
                }

            }
        });
        mCardView = (CardView) findViewById(R.id.version);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mCardView, "纪年当前版本号为：1.0", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
