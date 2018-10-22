package com.aoao.jinian;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by asus on 2018/9/1.
 */

public class FinanceDialogFragment extends DialogFragment {
    public static final String EXPENSES = "com.jinian.finance_expenses";
    public static final String REVENUE = "com.jinian.finance_revenue";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new FinanceDialog(getActivity(), new FinanceDialog.PriorityListener() {


            @Override
            public void setdata(String expenses, String revenue) {
                sendResult(0x111,expenses,revenue);
            }
        });
    }

    private void sendResult(int resultCode, String expenses,String revenue) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXPENSES, expenses);
        intent.putExtra(REVENUE, revenue);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

}
