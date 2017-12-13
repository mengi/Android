package com.android.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.myapplication.R;
import com.android.myapplication.model.Stock;
import com.android.myapplication.model.StockMov;
import com.android.myapplication.ui.activity.ProductDetailActivity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by menginar on 01.10.2017.
 */

public class StockReportAdapter extends RecyclerView.Adapter<StockReportAdapter.StockReportViewHolder> {

    public List<StockMov> stockMovList;
    final Context context;

    public StockReportAdapter(List<StockMov> stocks, Context context) {
        this.stockMovList = stocks;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return stockMovList.size();
    }

    @Override
    public void onBindViewHolder(StockReportAdapter.StockReportViewHolder holder, final int position) {
        try {
            holder.numberTextView.setText(Integer.toString(stockMovList.get(position).getProductNumber()));
            holder.stateTextView.setText(stockMovList.get(position).getProState());
            holder.dateTextView.setText(stockMovList.get(position).getDateInOuHour());
            holder.priceTextView.setText(Double.toString(stockMovList.get(position).getStatePrice()) + " TL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public StockReportAdapter.StockReportViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_three, viewGroup, false);
        StockReportAdapter.StockReportViewHolder viewHolder = new StockReportAdapter.StockReportViewHolder(view);
        return viewHolder;
    }

    public class StockReportViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView numberTextView, stateTextView, dateTextView, priceTextView;

        public StockReportViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            numberTextView = (TextView) view.findViewById(R.id.numberTextView);
            stateTextView = (TextView) view.findViewById(R.id.stateTextView);
            dateTextView = (TextView) view.findViewById(R.id.dateTextView);
            priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        }
    }
}
