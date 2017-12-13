package com.android.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.model.Stock;
import com.android.myapplication.model.StockMov;
import com.android.myapplication.ui.activity.ProductDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by menginar on 09.10.2017.
 */

public class StockDateAdapter extends RecyclerView.Adapter<StockDateAdapter.StockDateViewHolder> {

    public List<StockMov> stockMovList;
    final Context context;

    public  StockDateAdapter(List<StockMov> stockMovs, Context context) {
        this.stockMovList = stockMovs;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return stockMovList.size();
    }

    @Override
    public void onBindViewHolder(StockDateAdapter.StockDateViewHolder holder, final int position) {
        try {
            holder.nameTextView.setText(stockMovList.get(position).getProductName());
            holder.numberTextView.setText(Integer.toString(stockMovList.get(position).getProductNumber()));
            holder.stateTextView.setText(stockMovList.get(position).getProState());
            holder.dateTextView.setText(stockMovList.get(position).getDateInOuHour());
            holder.priceTextView.setText(Double.toString(stockMovList.get(position).getStatePrice()) + " TL");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public StockDateAdapter.StockDateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_five, viewGroup, false);
        StockDateAdapter.StockDateViewHolder viewHolder = new StockDateAdapter.StockDateViewHolder(view);
        return viewHolder;
    }

    public class StockDateViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView nameTextView, numberTextView, stateTextView, dateTextView, priceTextView;

        public StockDateViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            numberTextView = (TextView) view.findViewById(R.id.numberTextView);
            stateTextView = (TextView) view.findViewById(R.id.stateTextView);
            dateTextView = (TextView) view.findViewById(R.id.dateTextView);
            priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        }
    }
}