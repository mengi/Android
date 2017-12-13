package com.android.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.model.Stock;
import com.android.myapplication.ui.activity.ProductDetailActivity;

import java.util.List;

/**
 * Created by menginar on 02.10.2017.
 */

public class StockDataAdapter extends RecyclerView.Adapter<StockDataAdapter.StockViewHolder> {

    public List<Stock> stockList;
    final Context context;

    public StockDataAdapter(List<Stock> stocks, Context context) {
        this.stockList = stocks;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    @Override
    public void onBindViewHolder(final StockDataAdapter.StockViewHolder holder, final int position) {
        try {
            holder.codeTextView.setText(stockList.get(position).getProductCode());
            holder.nameTextView.setText(stockList.get(position).getProductName());
            holder.numberTextView.setText(Integer.toString(stockList.get(position).getProductNumber()));

            holder.chkSelected.setChecked(ReportType.isChecked[position]);

            holder.chkSelected.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (holder.chkSelected.isChecked()) {
                        ReportType.isChecked[position] = true;
                    }

                    if (!holder.chkSelected.isChecked()) {
                        ReportType.isChecked[position] = false;
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public StockDataAdapter.StockViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_four, viewGroup, false);
        StockDataAdapter.StockViewHolder viewHolder = new StockDataAdapter.StockViewHolder(view);
        return viewHolder;
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView codeTextView, nameTextView, numberTextView;
        public CheckBox chkSelected;

        public StockViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            codeTextView = (TextView) view.findViewById(R.id.codeTextView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            numberTextView = (TextView) view.findViewById(R.id.numberTextView);
            chkSelected = (CheckBox) view.findViewById(R.id.chkSelected);
        }
    }
}
