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

public class FinanceDialog extends Dialog {
    private TextView mTextView;


    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        void setdata(String expenses,String revenue);
    }
    private PriorityListener listener;

    public FinanceDialog(final Context context, final FinanceDialog.PriorityListener listener) {
        super(context);
        this.listener = listener;
        final View layout=View.inflate(context,R.layout.finance_dialog,null);
        setContentView(layout);
        mTextView = (TextView) layout.findViewById(R.id.name_finance);
        Drawable drawable = ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.delete);
        drawable.setBounds(0, 0, 50,50);
        mTextView.setCompoundDrawables( drawable,null, null, null);
        mTextView.setCompoundDrawablePadding(5);
        findViewById(R.id.name_finance_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.name_finance_ok:
                        EditText et = (EditText) layout.findViewById(R.id.edt_expenses);
                        String expenses = et.getText().toString().trim();
                        EditText et2 = (EditText) layout.findViewById(R.id.edit_revenue);
                        String revenue = et2.getText().toString().trim();
                        if (expenses == null || expenses.length() == 0 || revenue == null || revenue.length() == 0) {
                            Toast.makeText(getContext(), "亲爱的，请输入完整的记录哦", Toast.LENGTH_LONG).show();
                        } else {
                            listener.setdata(expenses,revenue);
                            dismiss();
                        }


                        break;
                }
            }
        });
        findViewById(R.id.name_finance_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.name_finance_cancel:
                        dismiss();
                        break;
                }
            }
        });
    }
}
