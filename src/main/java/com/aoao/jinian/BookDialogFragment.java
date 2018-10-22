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

public class BookDialogFragment extends DialogFragment {
    public static final String EXTRA_NAME = "com.jinian.booknamae";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BookDialog(getActivity(), new BookDialog.PriorityListener() {
            @Override
            public void setBookName(String string) {
              sendResult(Activity.RESULT_OK,string);
            }
        });
    }

    private void sendResult(int resultCode, String string) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, string);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

}
