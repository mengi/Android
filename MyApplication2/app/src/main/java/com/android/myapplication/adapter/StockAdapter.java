package com.android.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.myapplication.R;
import com.android.myapplication.model.Stock;
import com.android.myapplication.ui.activity.MainActivity;
import com.android.myapplication.ui.activity.ProductDetailActivity;
import com.android.myapplication.ui.fragment.FragmentStockSave;

import java.util.List;

/**
 * Created by menginar on 26.09.2017.
 */

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    public List<Stock> stockList;
    final Context context;

    public StockAdapter(List<Stock> stocks, Context context) {
        this.stockList = stocks;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, final int position) {
        try {
            holder.codeTextView.setText(stockList.get(position).getProductCode());
            holder.nameTextView.setText(stockList.get(position).getProductName());
            holder.numberTextView.setText(Integer.toString(stockList.get(position).getProductNumber()));

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("proCodeList", stockList.get(position).getProductCode());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item, viewGroup, false);
        StockViewHolder viewHolder = new StockViewHolder(view);
        return viewHolder;
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView codeTextView, nameTextView, numberTextView;

        public StockViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            codeTextView = (TextView) view.findViewById(R.id.codeTextView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            numberTextView = (TextView) view.findViewById(R.id.numberTextView);
        }
    }
}
