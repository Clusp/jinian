package com.aoao.jinian;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

/**
 * Created by asus on 2018/9/1.
 */

public class DeleteDialog extends Dialog implements android.view.View.OnClickListener{

    private TextView mTextView,mDeletAll,mDeletitem,mCancele;
    private Context mContext;
    private LeaveMyDialogListener listener;


    @Override
    public void onClick(View v) {
        listener.onClick(v);


    }

    public interface LeaveMyDialogListener{
        public void onClick(View view);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.delet_dialog);
        mTextView = (TextView)findViewById(R.id.delete_dialogt1);
        Drawable drawable = ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.delete);
        drawable.setBounds(0, 0, 50,50);
        mTextView.setCompoundDrawables( drawable,null, null, null);
        mTextView.setCompoundDrawablePadding(5);
        mDeletAll = (TextView) findViewById(R.id.name_ok_all);
        mDeletitem = (TextView) findViewById(R.id.name_ok);
        mCancele = (TextView) findViewById(R.id.delete_cancel);
        mDeletitem.setOnClickListener(this);
        mDeletAll.setOnClickListener(this);
        mCancele.setOnClickListener(this);
    }

    public DeleteDialog(Context context,LeaveMyDialogListener listener) {
        super(context);
        this.mContext= context;
        this.listener = listener;
    }

}
