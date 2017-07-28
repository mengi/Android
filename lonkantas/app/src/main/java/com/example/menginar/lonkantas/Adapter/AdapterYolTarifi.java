package com.example.menginar.lonkantas.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.menginar.lonkantas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Menginar on 24.6.2016.
 */
public class AdapterYolTarifi
        extends RecyclerView.Adapter<AdapterYolTarifi.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView adres;
        public TextView bolum;
        public TextView sehir;
        public TextView telefon_no;

        public ViewHolder(View v) {
            super(v);
            adres = (TextView) v.findViewById(R.id.text_adres);
            bolum = (TextView) v.findViewById(R.id.text_bolum);
            sehir = (TextView) v.findViewById(R.id.text_sehir);
            telefon_no = (TextView) v.findViewById(R.id.text_telefon_no);
        }
    }

    public AdapterYolTarifi() {
    }

    @Override
    public int getItemCount() {
        return Adres.ADRESLER.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_liste_adres, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Adres item = Adres.ADRESLER.get(i);
        viewHolder.adres.setText(item.adres);
        viewHolder.bolum.setText(item.bolum);
        viewHolder.sehir.setText(item.sehir);
        viewHolder.telefon_no.setText(item.telefon_no);
    }

    public static class Adres {
        public String adres;
        public String bolum;
        public String sehir;
        public String telefon_no;

        public Adres(String adres, String bolum,
                         String sehir, String telefon_no) {
            this.adres = adres;
            this.bolum = bolum;
            this.sehir = sehir;
            this.telefon_no = telefon_no;
        }

        public final static List<Adres> ADRESLER = new ArrayList<Adres>();

        static {
            ADRESLER.add(new Adres("Cra 24 #2C-50", "Valle", "Cali", "3459821"));
            ADRESLER.add(new Adres("Calle 100 Trans. 23", "Valle", "Cali", "4992600"));
            ADRESLER.add(new Adres("Ave. 3ra N. #20-10", "Valle", "Cali", "4400725"));
        }
    }


}