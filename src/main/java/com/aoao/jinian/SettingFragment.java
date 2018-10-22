package com.aoao.jinian;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aoao.jinian.database.Been;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Locale;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

/**
 * Created by asus on 2018/8/29.
 */

public class SettingFragment extends Fragment {
    private File file;
    private boolean isShowOrNot = false;
    private TextView mTextView;
    private CardView mExprot,mAppabout,mUpdate,mAuthor,mGroup,mDevice,mRobot,mFeedBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mExprot = (CardView) view.findViewById(R.id.tv_export_diary_setting);
        mExprot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                Toast.makeText(getContext(), "心情事迹已保存于SD卡根目录的纪年文件夹下", Toast.LENGTH_LONG).show();
            }
        });
        mAppabout = (CardView) view.findViewById(R.id.tv_help_setting);
        mAppabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),HelpAndSupport.class));
            }
        });
        mUpdate= (CardView) view.findViewById(R.id.tv_update_setting);
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUpdateAgent.forceUpdate(getContext());
                BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        if (updateStatus == UpdateStatus.Yes) {

                        } else if (updateStatus == UpdateStatus.No) {
                            Toast.makeText(getActivity(), "版本无更新", Toast.LENGTH_SHORT).show();
                        }
                        else if (updateStatus == UpdateStatus.IGNORED) {
                            Toast.makeText(getActivity(), "已忽略版本更新", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(getActivity(), "网络可能有点问题哦！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mAuthor = (CardView) view.findViewById(R.id.tv_author_setting);
        mAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String qq = "mqqwpa://im/chat?chat_type=wpa&uin=193186337&version=1";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qq)));
                }catch(Exception e)
                {
                    Toast.makeText(getActivity(),"亲爱的，小纪未检测到QQ或当前版本不支持",Toast.LENGTH_LONG).show();
                }


            }
        });
        mGroup = (CardView) view.findViewById(R.id.tv_group_setting);
        mGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String qqgroup = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=765390793&card_type=group&source=qrcode";
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(qqgroup)));
                }catch(Exception e)
                {
                    Toast.makeText(getActivity(),"亲爱的，小纪未检测到QQ或当前版本不支持",Toast.LENGTH_LONG).show();
                }

            }
        });
        mTextView = (TextView) view.findViewById(R.id.tv_phone_info_setting);
        mTextView.setText(getPhoneInfo());
        mDevice = (CardView) view.findViewById(R.id.tv_device_setting);
        mDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowOrNot == false)
                {
                    mTextView.setVisibility(View.VISIBLE);
                    isShowOrNot = true;
                }
                else
                {
                    mTextView.setVisibility(View.GONE);
                    isShowOrNot  = false;
                }

            }
        });


        mRobot = (CardView) view.findViewById(R.id.tv_robot_setting);
        mRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Robot.class));
            }
        });
        mFeedBack = (CardView) view.findViewById(R.id.tv_log_setting);
        mFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Email.class));
            }
        });
        return view;
    }



    private String getPhoneInfo() {
        StringBuilder phoneinfo = new StringBuilder();
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String simState = null;
        if (tm.getSimState() == 0) {
            simState = "未知状态";
        }
        if (tm.getSimState() == 1) {
            simState = "未插sim卡";
        }
        if (tm.getSimState() == 2) {
            simState = "锁定状态，需要用户的PIN码解锁";
        }
        if (tm.getSimState() == 3) {
            simState = "锁定状态，需要用户的PUK码解锁";
        }
        if (tm.getSimState() == 4) {
            simState = "锁定状态，需要网络的PIN码解锁";
        }
        if (tm.getSimState() == 5) {
            simState = "就绪状态";
        }
        phoneinfo.append("设备ID：" + tm.getDeviceId());
        phoneinfo.append("\n软件版本：" + tm.getDeviceSoftwareVersion());
        phoneinfo.append("\n客户标识：" + tm.getSubscriberId());
        phoneinfo.append("\nsim卡状态：" + simState);
        phoneinfo.append("\n当前服务商：" + tm.getSimOperatorName());
        phoneinfo.append("\n当前运营商: " + tm.getNetworkOperator());
        phoneinfo.append("\nsim卡序列号：" + tm.getSimSerialNumber());
        phoneinfo.append( "\n主板信息：" +Build.BOARD);
        phoneinfo.append( "\n引导程序版本号：" + Build.BOOTLOADER);
        phoneinfo.append( "\n系统定制商：" + Build.BRAND);
        phoneinfo.append( "\nCPU指令集：" + Build.CPU_ABI);
        phoneinfo.append( "\nCPU指令集2：" + Build.CPU_ABI2);
        phoneinfo.append( "\n产品名称: " + Build.PRODUCT);
        phoneinfo.append( "\n设备制造商: " + Build.MANUFACTURER);
        phoneinfo.append( "\n设备名称：" + Build.MODEL);
        phoneinfo.append( "\n设备宽高：" + getActivity().getResources().getDisplayMetrics().widthPixels+"×"+getActivity().getResources().getDisplayMetrics().heightPixels);
        phoneinfo.append( "\n设备参数:" + Build.DEVICE);
        phoneinfo.append( "\n设备标签：" + Build.TAGS);
        phoneinfo.append( "\n设备主机地址：" + Build.HOST);
        phoneinfo.append( "\n设备用户名: " + Build.USER);;
        phoneinfo.append( "\n版本号: " + Build.DISPLAY);
        phoneinfo.append( "\n修订版本列表：" + Build.ID);
        phoneinfo.append( "\n安卓版本：" + Build.VERSION.RELEASE);
        phoneinfo.append( "\nAPI级别：" + Build.VERSION.SDK_INT);
        phoneinfo.append( "\n基带版本：" + Build.VERSION.INCREMENTAL);
        phoneinfo.append( "\n当前语言：" + Locale.getDefault().getLanguage());
        phoneinfo.append( "\n唯一识别码：" + Build.FINGERPRINT);

        String phoneInfo=phoneinfo.toString().toLowerCase();
        return phoneInfo;
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    public void initData() {
        file = new File(getSDPath() + "/Jinian");
        makeDir(file);
        try {
            File file2 = new File(file, "diary.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file2, false));
            bw.write(String.valueOf(Been.getBeen(getActivity()).getdiary()));
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;

    }
}

