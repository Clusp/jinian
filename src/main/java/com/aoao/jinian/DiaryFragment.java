package com.aoao.jinian;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by asus on 2018/8/29.
 */

public class DiaryFragment extends Fragment {
    private static final int REQUEST_NAME = 0;
    private static final String DIALOG_NAME = "DialogName";
    private static final String BOOK_NAME = "BName";
    private static TextView mTextView;
    private ImageView mImageView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diaryfragment, container, false);
        mTextView = (TextView) view.findViewById(R.id.book_name);
        String text = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(BOOK_NAME, "年少风流付与君");
        mTextView.setText(text);
        mImageView = (ImageView) view.findViewById(R.id.book_image);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BookContentActivity.class));

            }
        });
        return view;

    }

    public static DiaryFragment newInstance() {
        return new DiaryFragment();
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
                FragmentManager fm = getFragmentManager();
                BookDialogFragment bookDialogFragment = new BookDialogFragment();
                bookDialogFragment.setTargetFragment(DiaryFragment.this,REQUEST_NAME);
                bookDialogFragment.show(fm,DIALOG_NAME);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return ;
        }
        if (requestCode == REQUEST_NAME) {
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString(BOOK_NAME, data.getStringExtra(BookDialogFragment.EXTRA_NAME)).apply();
            mTextView.setText(data.getStringExtra(BookDialogFragment.EXTRA_NAME));
        }
    }


}