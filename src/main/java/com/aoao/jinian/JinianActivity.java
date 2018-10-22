package com.aoao.jinian;

import android.Manifest;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

import static com.ashokvarma.bottomnavigation.BottomNavigationBar.*;

public class JinianActivity extends Permission {
    public static final String DIARY = "DIARY";
    private static final String FINANCE = "FINANCE";
    private static final String NOTE = "NOTE";
    private static final String SETTING = "SETTING";
    private DiaryFragment mDiaryBookFragment;
    private FinanceFragment mFinanceFragment;
    private NoteFragment mNoteFragment;
    private SettingFragment mSettingFragment;
    private BottomNavigationBar bottomNavigationBar;
    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private ActionBar actionBar;
    private long exitTime = 0;
    private static int posi = 0;


    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        switch (requestCode) {
            case 0x0001:
                Toast.makeText(this, "已成功获取权限", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission(new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 0x0001);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.zhu);
        }
        BmobUpdateAgent.setUpdateOnlyWifi(true);
        BmobUpdateAgent.update(this);

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mNoteFragment = (NoteFragment) mFragmentManager.findFragmentByTag(NOTE);
            mFinanceFragment = (FinanceFragment) mFragmentManager.findFragmentByTag(FINANCE);
            mDiaryBookFragment = (DiaryFragment) mFragmentManager.findFragmentByTag(DIARY);
            mSettingFragment = (SettingFragment) mFragmentManager.findFragmentByTag(SETTING);
        } else {
            setClick(0);
        }

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(MODE_SHIFTING)
                .setBackgroundStyle(BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setActiveColor(R.color.buttonbar_active)
                .setBarBackgroundColor(R.color.background)
                .setInActiveColor(R.color.buttonbar_negative);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.note, "备忘"))
                .addItem(new BottomNavigationItem(R.drawable.finance, "财务"))
                .addItem(new BottomNavigationItem(R.drawable.book, "日记"))
                .addItem(new BottomNavigationItem(R.drawable.settings, "设置"))
                .setFirstSelectedPosition(posi)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        setClick(0);
                        posi = 0;
                        break;
                    case 1:
                        setClick(1);
                        posi = 1;
                        break;
                    case 2:
                        setClick(2);
                        posi = 2;
                        break;
                    case 3:
                        setClick(3);
                        posi = 3;
                        break;
                    default:
                        setClick(0);
                        posi = 0;
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void setClick(int type) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        hideFragment(mFragmentTransaction);
        switch (type) {
            case 0:
                if (mNoteFragment == null) {
                    mNoteFragment = mNoteFragment.newInstance();
                    mFragmentTransaction.add(R.id.fragment_container, mNoteFragment, NOTE);
                } else {
                    mFragmentTransaction.show(mNoteFragment);
                }
                break;
            case 1:
                if (mFinanceFragment == null) {
                    mFinanceFragment = mFinanceFragment.newInstance();
                    mFragmentTransaction.add(R.id.fragment_container, mFinanceFragment, FINANCE);
                } else {
                    mFragmentTransaction.show(mFinanceFragment);
                }
                break;
            case 2:
                if (mDiaryBookFragment == null) {
                    mDiaryBookFragment = mDiaryBookFragment.newInstance();
                    mFragmentTransaction.add(R.id.fragment_container, mDiaryBookFragment, DIARY);
                } else {
                    mFragmentTransaction.show(mDiaryBookFragment);
                }
                break;
            case 3:
                if (mSettingFragment == null) {
                    mSettingFragment = mSettingFragment.newInstance();
                    mFragmentTransaction.add(R.id.fragment_container, mSettingFragment, SETTING);
                } else {
                    mFragmentTransaction.show(mSettingFragment);
                }
                break;
        }
        mFragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mNoteFragment != null) {
            fragmentTransaction.hide(mNoteFragment);
        }
        if (mFinanceFragment != null) {
            fragmentTransaction.hide(mFinanceFragment);
        }
        if (mDiaryBookFragment != null) {
            fragmentTransaction.hide(mDiaryBookFragment);
        }
        if (mSettingFragment != null) {
            fragmentTransaction.hide(mSettingFragment);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 800) {
                Toast.makeText(JinianActivity.this, "再按一次退出", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
