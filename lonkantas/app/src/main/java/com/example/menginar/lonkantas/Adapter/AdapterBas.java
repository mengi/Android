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

/**
 * Created by Menginar on 25.6.2016.
 */
public class AdapterBas extends RecyclerView.Adapter<AdapterBas.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView isim;
        public TextView fiyat;
        public ImageView resim;

        public ViewHolder(View v) {
            super(v);
            isim = (TextView) v.findViewById(R.id.isim_gida);
            fiyat = (TextView) v.findViewById(R.id.fiyat_gida);
            resim = (ImageView) v.findViewById(R.id.resim_gida);
        }
    }

    public AdapterBas() {
    }

    @Override
    public int getItemCount() {
        return Gida.POPULER_YEMEKLER.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_liste_baslat, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Gida item = Gida.POPULER_YEMEKLER.get(i);

        float Fiyat = item.getFiyat();
        String isim = item.getIsim();

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.resim);
        viewHolder.isim.setText(item.getIsim());
        viewHolder.fiyat.setText("$" + item.getFiyat());

    }
}
