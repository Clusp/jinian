package com.aoao.jinian;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.aoao.jinian.database.Bean;
import com.aoao.jinian.database.Been;

import java.util.UUID;

/**
 * Created by asus on 2018/8/30.
 */

public class EditActivity extends AppCompatActivity {
    private static final String EXTRA_BEAN_ID = "com.aoao.jinian.bean_id";
    private static final String TABLE = "com.aoao.jinian.table";

    private FloatingActionButton mfab;
    private EditText editText;
    private Bean mBean;

    private void getBean() {
        UUID uuid = (UUID) getIntent().getSerializableExtra(EXTRA_BEAN_ID);
        String table = getIntent().getStringExtra(TABLE);
        mBean= Been.getBean(table,uuid);
    }
    public static Intent newIntent(Context context, String table, UUID uuid) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(TABLE, table);
        intent.putExtra(EXTRA_BEAN_ID, uuid);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getBean();
        editText = (EditText) findViewById(R.id.container_edit);
        editText.setText(mBean.getContent());
        editText.requestFocus();
        editText.setSelection(mBean.getContent().length());
        mfab = (FloatingActionButton) findViewById(R.id.edit_fab);
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                mBean.setContent(content);
                Been.upData(getIntent().getStringExtra(TABLE),mBean);
                Snackbar.make(editText,"亲爱的，纪年已为您保存心情事迹",Snackbar.LENGTH_LONG).show();
            }
        });

    }


}
