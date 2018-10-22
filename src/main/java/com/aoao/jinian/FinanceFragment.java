package com.aoao.jinian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.aoao.jinian.database.BillBean;
import com.aoao.jinian.database.BillBeen;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by asus on 2018/8/29.
 */

public class FinanceFragment extends Fragment {
    private static final int REQUEST_NAME_FINANCE = 1;
    private static final String FINANCEFRAGMENT = "FINANCEFRAMENT";
    private MyRecyclerView bill_listview;
    private FinanceAdapter mFinanceAdapter;
    private DeleteDialog mDeleteDialog;
    private TextView mExpensesCount;
    private TextView mRevenueCount;
    private TextView mProfitCount;
    private View mView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.financefragment, container, false);
        bill_listview = (MyRecyclerView) view.findViewById(R.id.bill_listview);
        bill_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mView = view.findViewById(R.id.emptyview_finance);
        mExpensesCount = (TextView) view.findViewById(R.id.expenses_count);
        mExpensesCount.setText(BillBeen.getBeen(getActivity()).count("expenses")+"");
        mRevenueCount = (TextView) view.findViewById(R.id.revenue_count);
        mRevenueCount.setText(BillBeen.getBeen(getActivity()).count("revenue")+"");
        mProfitCount = (TextView) view.findViewById(R.id.profit_count);
        mProfitCount.setText(BillBeen.getBeen(getActivity()).count("profit")+"");
        updateUI();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        BillBeen been = BillBeen.getBeen(getActivity());
        List<BillBean> beens = been.getmBiilBeen();
        if (mFinanceAdapter == null) {
            mFinanceAdapter = new FinanceAdapter(beens);
            bill_listview.setAdapter(mFinanceAdapter);
            bill_listview.setEmptyView(mView);
        } else {
            mFinanceAdapter.setBeen(beens);
            mFinanceAdapter.notifyDataSetChanged();
        }
        mExpensesCount.setText(BillBeen.getBeen(getActivity()).count("expenses")+"");
        mRevenueCount.setText(BillBeen.getBeen(getActivity()).count("revenue")+"");
        mProfitCount.setText(BillBeen.getBeen(getActivity()).count("profit")+"");

    }

    public static FinanceFragment newInstance() {
        return new FinanceFragment();
    }

    private class FinanceHolder extends RecyclerView.ViewHolder {
        private TextView mTime;
        private TextView mExpenses;
        private TextView mRevenue;
        private TextView mProfit;
        private BillBean mBillBean;


        public FinanceHolder(LayoutInflater inflater, ViewGroup viewGroup) {//实例化布局item，实例化组件
            super(inflater.inflate(R.layout.finance_items, viewGroup, false));
            mTime = (TextView) itemView.findViewById(R.id.time_finance_error);
            mExpenses = (TextView) itemView.findViewById(R.id.expenses_finance);
            mRevenue = (TextView) itemView.findViewById(R.id.revenue_finance);
            mProfit = (TextView) itemView.findViewById(R.id.profit_finance);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mDeleteDialog = new DeleteDialog(getActivity(), new DeleteDialog.LeaveMyDialogListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.name_ok:
                                    BillBeen.deleteData(mBillBean);
                                    updateUI();
                                    Toast.makeText(getActivity(), "亲爱的，纪年已为您删除此项财务情况", Toast.LENGTH_SHORT).show();
                                    mDeleteDialog.dismiss();
                                    break;
                                case R.id.name_ok_all:
                                    BillBeen.deleteDataAll();
                                    updateUI();
                                    Toast.makeText(getActivity(), "亲爱的，纪年已为您删除所有财务情况", Toast.LENGTH_SHORT).show();
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

        public void bind(BillBean mbillBean) {
            mBillBean = mbillBean;
            mTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(mbillBean.getTime()));
            mExpenses.setText(mbillBean.getExpenses()+"");
            mRevenue.setText(mbillBean.getRevenue()+"");
            mProfit.setText(mbillBean.getProfit()+"");
        }
    }

    private class FinanceAdapter extends RecyclerView.Adapter<FinanceFragment.FinanceHolder> {
        private List<BillBean> mBillBeen;


        public FinanceAdapter(List<BillBean> billBeen) {//bind方法传入been
            mBillBeen = billBeen;
        }

        public void setBeen(List<BillBean> billBeen) {
            mBillBeen = billBeen;
        }

        @Override
        public FinanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FinanceHolder(LayoutInflater.from(getActivity()), parent);
        }

        @Override
        public void onBindViewHolder(FinanceHolder holder, int position) {
            BillBean mbillBean = mBillBeen.get(position);
            holder.bind(mbillBean);

        }


        @Override
        public int getItemCount() {
            return mBillBeen.size();
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
                FragmentManager fm = getFragmentManager();
                FinanceDialogFragment bookDialogFragment = new FinanceDialogFragment();
                bookDialogFragment.setTargetFragment(FinanceFragment.this,REQUEST_NAME_FINANCE);
                bookDialogFragment.show(fm,FINANCEFRAGMENT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != 0x111) {
            return ;
        }
        if (requestCode == REQUEST_NAME_FINANCE) {
            int a= Integer.parseInt(data.getStringExtra(FinanceDialogFragment.EXPENSES));
            int b= Integer.parseInt(data.getStringExtra(FinanceDialogFragment.REVENUE));
            BillBean mBillBean = new BillBean();
            mBillBean.setExpenses(a);
            mBillBean.setRevenue(b);
            mBillBean.setProfit(b-a);
            BillBeen.addData(mBillBean);
            updateUI();
        }
    }

}
