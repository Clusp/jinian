package com.aoao.jinian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aoao.jinian.database.Bean;
import com.aoao.jinian.database.Been;
import com.aoao.jinian.database.MyDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2018/9/2.
 */

public class BookContentActivity extends AppCompatActivity{

    private MyRecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private BookAdapter mBookAdapter;
    private DeleteDialog mDeleteDialog;
    private View mView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_bookcontent);
        mRecyclerView = (MyRecyclerView) findViewById(R.id.recycler_view_diary);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);
        mView = findViewById(R.id.emptyview_diary);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        updateUI();
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bean bean = new Bean();
                bean.setContent("");
                Been.getBeen(BookContentActivity.this).addData(MyDatabaseHelper.tableName2,bean);
                Intent intent =EditActivity.newIntent(BookContentActivity.this,MyDatabaseHelper.tableName2, bean.getId());
                startActivity(intent);
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    private void updateUI() {
        Been been = Been.getBeen(this);
        List<Bean> beens = been.getmBeen(MyDatabaseHelper.tableName2);
        if (mBookAdapter == null) {
            mBookAdapter = new BookAdapter(beens);
            mRecyclerView.setAdapter(mBookAdapter);
            mRecyclerView.setEmptyView(mView);
        }else {
            mBookAdapter.setBeen(beens);
            mBookAdapter.notifyDataSetChanged();
        }

    }
    private class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookView> {
        private List<Bean> mBeen;
        public BookAdapter(List<Bean> been) {//bind方法传入been
            mBeen = been;
        }
        public void setBeen(List<Bean> been) {
            mBeen = been;
        }

        public class BookView extends RecyclerView.ViewHolder {
            private TextView mTextView;
            private TextView mTextTime;
            private Bean mBean;
            public BookView(LayoutInflater inflater, ViewGroup viewGroup) {
                super(inflater.inflate(R.layout.book_list_item, viewGroup, false));
                mTextView = (TextView) itemView.findViewById(R.id.book_item_text);
                mTextTime = (TextView) itemView.findViewById(R.id.book_item_time);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =EditActivity.newIntent(BookContentActivity.this, MyDatabaseHelper.tableName2,mBean.getId());
                        startActivity(intent);
                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View v) {
                        mDeleteDialog = new DeleteDialog(BookContentActivity.this, new DeleteDialog.LeaveMyDialogListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.name_ok:
                                        Been.deleteData(MyDatabaseHelper.tableName2,mBean);
                                        updateUI();
                                        Snackbar.make(v, "亲爱的，纪年已为您删除此项心情事迹", Snackbar.LENGTH_LONG).show();
                                        mDeleteDialog.dismiss();
                                        break;
                                    case R.id.name_ok_all:
                                        Been.deleteDataAll(MyDatabaseHelper.tableName2);
                                        updateUI();
                                        Snackbar.make(v, "亲爱的，纪年已为您删除所有心情事迹", Snackbar.LENGTH_LONG).show();
                                        mDeleteDialog.dismiss();
                                        break;
                                    case R.id.delete_cancel:
                                        mDeleteDialog.dismiss();
                                        break;
                                    default:
                                        mDeleteDialog.dismiss();
                                        break;
                                }
                            }
                        });
                        mDeleteDialog.show();
                        return true;
                    }
                });

            }
            public void bind(Bean bean) {
                mBean = bean;
                mTextView.setText(mBean.getContent());
                Date date =mBean.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = sdf.format(date);
                mTextTime.setText(dateString);
            }
        }

        @Override
        public BookView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BookView(LayoutInflater.from(BookContentActivity.this), parent);
        }

        @Override
        public void onBindViewHolder(BookView holder, int position) {
            Bean bean = mBeen.get(position);
            holder.bind(bean);

        }


        @Override
        public int getItemCount() {
            return mBeen.size();
        }
    }
}

