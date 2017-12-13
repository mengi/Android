package com.android.myapplication.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.model.Stock;
import com.android.myapplication.ui.fragment.FragmentStockDelUp;
import com.android.myapplication.ui.fragment.FragmentStockInOu;

import java.util.List;

/**
 * Created by menginar on 29.09.2017.
 */

public class StockSearchAdapter extends RecyclerView.Adapter<StockSearchAdapter.StockSearchViewHolder> {

    public List<Stock> stockList;
    Context context;

    public StockSearchAdapter(List<Stock> stocks, Context context) {
        this.stockList = stocks;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    @Override
    public void onBindViewHolder(final StockSearchAdapter.StockSearchViewHolder holder, final int position) {
        try {
            holder.codeTextView.setText(stockList.get(position).getProductCode());
            holder.nameTextView.setText(stockList.get(position).getProductName());
            holder.numberTextView.setText(Integer.toString(stockList.get(position).getProductNumber()));

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                    try {

                        if (ReportType.SEARCHTYPE.equals("fragmentInOu")) {
                            Fragment fragment = new FragmentStockInOu();
                            Bundle bundle = new Bundle();
                            bundle.putString("searchFragmentProCode", stockList.get(position).getProductCode());
                            fragment.setArguments(bundle);

                            appCompatActivity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }

                        if (ReportType.SEARCHTYPE.equals("fragmentDelUp")) {
                            Fragment fragment = new FragmentStockDelUp();
                            Bundle bundle = new Bundle();
                            bundle.putString("searchFragmentProCode", stockList.get(position).getProductCode());
                            fragment.setArguments(bundle);

                            appCompatActivity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public StockSearchAdapter.StockSearchViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item, viewGroup, false);
        StockSearchAdapter.StockSearchViewHolder viewHolder = new StockSearchAdapter.StockSearchViewHolder(view);
        return viewHolder;
    }

    public class StockSearchViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView codeTextView, nameTextView, numberTextView;

        public StockSearchViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            codeTextView = (TextView) view.findViewById(R.id.codeTextView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            numberTextView = (TextView) view.findViewById(R.id.numberTextView);
        }
    }
}
