package com.android.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.database.AppDatabase;
import com.android.myapplication.model.StockMov;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by menginar on 29.09.2017.
 */

public class StockMovDailyAdapter extends RecyclerView.Adapter<StockMovDailyAdapter.StockMovDailyViewHolder> {

    public AppDatabase appDatabase;
    public List<String> codeList;
    Context context;

    public StockMovDailyAdapter(List<String> strings, Context context) {
        this.codeList = strings;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return codeList.size();
    }

    @Override
    public void onBindViewHolder(StockMovDailyAdapter.StockMovDailyViewHolder holder, int position) {
        try {
            getAppDBaseConnect(this.context);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateDaily = dateFormat.format(new Date());

            int numberInput = 0;
            int numberOutput = 0;
            String proName = "";

            List<StockMov> stockMovs = new ArrayList<>();
            stockMovs = appDatabase.stockMovDao().getStockMovDailyCodeDate(this.codeList.get(position), dateDaily);

            for (StockMov stockMov : stockMovs) {
                if (stockMov.getProState().equals(ReportType.OUTPUT)) {
                    numberOutput += stockMov.getProductNumber();
                }

                if (stockMov.getProState().equals(ReportType.INPUT)) {
                    numberInput += stockMov.getProductNumber();
                }

                proName = stockMov.getProductName();
            }

            holder.nameTextView.setText(proName);
            holder.inNumberTextView.setText(Integer.toString(numberInput));
            holder.ounumberTextView.setText(Integer.toString(numberOutput));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public StockMovDailyAdapter.StockMovDailyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_two, viewGroup, false);
        StockMovDailyAdapter.StockMovDailyViewHolder viewHolder = new StockMovDailyAdapter.StockMovDailyViewHolder(view);
        return viewHolder;
    }

    public class StockMovDailyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView nameTextView, inNumberTextView, ounumberTextView;

        public StockMovDailyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            inNumberTextView = (TextView) view.findViewById(R.id.inNumberTextView);
            ounumberTextView = (TextView) view.findViewById(R.id.ounumberTextView);
        }
    }

    private void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
