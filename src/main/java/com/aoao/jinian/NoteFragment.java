package com.aoao.jinian;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aoao.jinian.database.Bean;
import com.aoao.jinian.database.Been;
import com.aoao.jinian.database.MyDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2018/8/29.
 */

public class NoteFragment extends Fragment {
    private MyRecyclerView mRecyclerView;
    private NoteAdapter mNoteAdapter;
    private DeleteDialog mDeleteDialog;
    private View mEmptyView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notefragment, container, false);
        mRecyclerView = (MyRecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mEmptyView = view.findViewById(R.id.emptyview);
        updateUI();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {//获取数据池中的所有表为tableName的bean数据，传入NoteAdapter
        Been been = Been.getBeen(getActivity());
        List<Bean> beens = been.getmBeen(MyDatabaseHelper.tableName);
        if (mNoteAdapter == null) {
            mNoteAdapter = new NoteAdapter(beens);
            mRecyclerView.setAdapter(mNoteAdapter);
            mRecyclerView.setEmptyView(mEmptyView);
        }else {
            mNoteAdapter.setBeen(beens);
            mNoteAdapter.notifyDataSetChanged();
        }

    }
    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    private class NoteHolder extends MyRecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mTimeTextView;
        private Bean mBean;

        public NoteHolder(LayoutInflater inflater,ViewGroup viewGroup) {//实例化布局item，实例化组件
            super(inflater.inflate(R.layout.note_list_item,viewGroup,false));
            mTitleTextView = (TextView) itemView.findViewById(R.id.note_title);
            Drawable drawable = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.firsticon);
            drawable.setBounds(0, 0, 40,40);
            mTitleTextView.setCompoundDrawables( drawable,null, null, null);
            mTitleTextView.setCompoundDrawablePadding(20);
            mTimeTextView = (TextView) itemView.findViewById(R.id.note_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =EditActivity.newIntent(getActivity(),MyDatabaseHelper.tableName,mBean.getId());
                    startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                     mDeleteDialog = new DeleteDialog(getActivity(), new DeleteDialog.LeaveMyDialogListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.name_ok:
                                    Been.deleteData(MyDatabaseHelper.tableName,mBean);
                                    updateUI();
                                    Toast.makeText(getActivity(),"亲爱的，纪年已为您删除此项心情事迹",Toast.LENGTH_SHORT).show();
                                    mDeleteDialog.dismiss();
                                    break;
                                case R.id.name_ok_all:
                                    Been.deleteDataAll(MyDatabaseHelper.tableName);
                                    updateUI();
                                    Toast.makeText(getActivity(),"亲爱的，纪年已为您删除所有心情事迹",Toast.LENGTH_SHORT).show();
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

        public void bind(Bean bean) {//通过位置获取的子数据的标题和时间，设置视图
            mBean = bean;
            mTitleTextView.setText(mBean.getContent());
            Date date =mBean.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = sdf.format(date);
            mTimeTextView.setText(dateString);
        }
    }

    private class NoteAdapter extends MyRecyclerView.Adapter<NoteHolder> {
        private List<Bean> mBeen;

        public NoteAdapter(List<Bean> been) {//bind方法传入been
            mBeen = been;
        }

        public void setBeen(List<Bean> been) {
            mBeen = been;
        }
        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {//new一个线性布局管理器，调用NoteHolder方法
            return new NoteHolder(LayoutInflater.from(getActivity()), parent);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {//获取的数据集，通过位置得到子数据赋值，调用bind方法绑定数据。
            Bean bean = mBeen.get(position);
            holder.bind(bean);

        }


        @Override
        public int getItemCount() {
            return mBeen.size();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.note_add_menu,menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_note_add:
                Bean bean = new Bean();
                bean.setContent("");
                Been.addData(MyDatabaseHelper.tableName,bean);
                Intent intent = EditActivity.newIntent(getActivity(),MyDatabaseHelper.tableName, bean.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
