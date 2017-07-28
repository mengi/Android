package com.example.menginar.lonkantas.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.menginar.lonkantas.Model.Gida;
import com.example.menginar.lonkantas.R;

import java.util.List;

/**
 * Created by Menginar on 25.6.2016.
 */
public class AdapterKategori extends RecyclerView.Adapter<AdapterKategori.ViewHolder> {


    private final List<Gida> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView isim;
        public TextView fiyat;
        public ImageView resim;

        public ViewHolder(View v) {
            super(v);

            isim = (TextView) v.findViewById(R.id.text_isim);
            fiyat = (TextView) v.findViewById(R.id.text_fiyat);
            resim = (ImageView) v.findViewById(R.id.image_gida);
        }
    }


    public AdapterKategori(List<Gida> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_liste_kategoriler, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Gida item = items.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.resim);
        viewHolder.isim.setText(item.getIsim());
        viewHolder.fiyat.setText("$" + item.getFiyat());

    }
}
