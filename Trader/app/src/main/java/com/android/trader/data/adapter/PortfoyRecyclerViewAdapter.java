package com.android.trader.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.trader.R;
import com.android.trader.data.model.Item;
import com.android.trader.util.DisplayNumberFormat;

import java.util.List;

/**
 * Created by menginar on 11.01.2018.
 */

public class PortfoyRecyclerViewAdapter extends RecyclerView.Adapter<PortfoyRecyclerViewAdapter.PortfoyViewHolder> {

    public List<Item> itemList;
    final Context context;

    public PortfoyRecyclerViewAdapter(List<Item> items, Context context) {
        this.itemList = items;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(PortfoyRecyclerViewAdapter.PortfoyViewHolder holder, final int position) {
        try {
            holder.txtMovable.setText(itemList.get(position).getSymbol());
            holder.txtQuantityT2.setText(DisplayNumberFormat.displayNumberQty(itemList.get(position).getQtyT2()));
            holder.txtPrice.setText(DisplayNumberFormat.displayNumberDoubleLastPx(itemList.get(position).getLastPx()));
            holder.txtTotal.setText(DisplayNumberFormat
                    .displayNumberDoubleTotal(itemList.get(position).getQtyT2() * itemList.get(position).getLastPx()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PortfoyRecyclerViewAdapter.PortfoyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item, viewGroup, false);
        PortfoyRecyclerViewAdapter.PortfoyViewHolder viewHolder = new PortfoyRecyclerViewAdapter.PortfoyViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class PortfoyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView txtMovable, txtQuantityT2, txtPrice, txtTotal;

        public PortfoyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            txtMovable = (TextView) view.findViewById(R.id.textViewMovable);
            txtQuantityT2 = (TextView) view.findViewById(R.id.textViewQuantityT2);
            txtPrice = (TextView) view.findViewById(R.id.textViewPrice);
            txtTotal = (TextView) view.findViewById(R.id.textViewTotal);
        }
    }
}