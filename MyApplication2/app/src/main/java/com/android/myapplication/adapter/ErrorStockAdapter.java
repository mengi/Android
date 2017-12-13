package com.android.myapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.R;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.ErrorStock;
import com.android.myapplication.model.Stock;
import com.android.myapplication.ui.activity.ErrorStockDetailActivity;
import com.android.myapplication.ui.activity.ProductDetailActivity;

import java.util.List;

/**
 * Created by menginar on 07.11.2017.
 */

public class ErrorStockAdapter extends RecyclerView.Adapter<ErrorStockAdapter.ErrorStockViewHolder> {

    public List<ErrorStock> errorStockList;
    private AppDatabase appDatabase;
    final Context context;

    public ErrorStockAdapter(List<ErrorStock> errorStocks, Context context) {
        this.errorStockList = errorStocks;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return errorStockList.size();
    }

    @Override
    public void onBindViewHolder(ErrorStockViewHolder holder, final int position) {
        try {
            holder.codeTextView.setText(errorStockList.get(position).getProductCode());
            holder.nameTextView.setText(errorStockList.get(position).getProductName());
            holder.numberTextView.setText(Integer.toString(errorStockList.get(position).getProductNumber()));
            holder.dateTextView.setText(errorStockList.get(position).getDateInOuHour());

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context, ErrorStockDetailActivity.class);
                        intent.putExtra("proCodeId", errorStockList.get(position).getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
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
    public ErrorStockViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_six, viewGroup, false);
        ErrorStockViewHolder viewHolder = new ErrorStockViewHolder(view);
        return viewHolder;
    }

    public class ErrorStockViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView codeTextView, nameTextView, numberTextView, dateTextView;

        public ErrorStockViewHolder(View itemView) {
            super(itemView);

            view = itemView;

            codeTextView = (TextView) view.findViewById(R.id.codeTextView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            numberTextView = (TextView) view.findViewById(R.id.numberTextView);
            dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        }
    }
}
