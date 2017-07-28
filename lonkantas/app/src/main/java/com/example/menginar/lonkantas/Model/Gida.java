package com.example.menginar.lonkantas.Model;

import com.example.menginar.lonkantas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Menginar on 24.6.2016.
 */
public class Gida {
    private float fiyat;
    private String isim;
    private int idDrawable;

    public Gida(float fiyat, String isim, int idDrawable) {
        this.fiyat = fiyat;
        this.isim = isim;
        this.idDrawable = idDrawable;
    }

    public static final List<Gida> POPULER_YEMEKLER = new ArrayList<Gida>();
    public static final List<Gida> ICECEKLER = new ArrayList<Gida>();
    public static final List<Gida> TATLILAR = new ArrayList<Gida>();
    public static final List<Gida> YEMEKLER = new ArrayList<Gida>();

    static {
        POPULER_YEMEKLER.add(new Gida(5, "Deneme", R.drawable.camarones));

        ICECEKLER.add(new Gida(5, "Deneme2", R.drawable.camarones));

        TATLILAR.add(new Gida(3, "Deneme3", R.drawable.cafe));

        YEMEKLER.add(new Gida(2, "Deneme4", R.drawable.postre_vainilla));

    }

    public float getFiyat() {
        return this.fiyat;
    }

    public String getIsim() {
        return this.isim;
    }

    public int getIdDrawable() {
        return idDrawable;
    }
}
