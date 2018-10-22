package com.aoao.jinian;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by asus on 2018/9/1.
 */

public class BookDialog extends Dialog {
    private TextView mTextView;


    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        void setBookName(String string);
    }
    private PriorityListener listener;

    public BookDialog(final Context context, final PriorityListener listener) {
        super(context);
        this.listener = listener;
        final View layout=View.inflate(context,R.layout.diary_dialog,null);
        setContentView(layout);
        mTextView = (TextView) layout.findViewById(R.id.name_diary);
        Drawable drawable = ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.delete);
        drawable.setBounds(0, 0, 50,50);
        mTextView.setCompoundDrawables( drawable,null, null, null);
        mTextView.setCompoundDrawablePadding(5);
        findViewById(R.id.name_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.name_ok:
                        EditText et = (EditText) layout.findViewById(R.id.create_name);
                        String name = et.getText().toString();
                        listener.setBookName(name);
                        dismiss();
                        break;
                }
            }
        });
        findViewById(R.id.name_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.name_cancel:
                        dismiss();
                        break;
                }
            }
        });
    }
}
