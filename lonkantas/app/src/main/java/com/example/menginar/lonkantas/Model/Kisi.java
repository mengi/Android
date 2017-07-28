package com.example.menginar.lonkantas.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Menginar on 1.7.2016.
 */
public class Kisi {

    private String Isim;
    private String Email;
    private String Sifre;

    public Kisi (String isim, String email, String sifre) {
        this.Isim = isim;
        this.Email = email;
        this.Sifre = sifre;
    }

    public static final List<Kisi> KISILER = new ArrayList<Kisi>();

    static {
        KISILER.add(new Kisi("Muhammet Enginar", "muhammet.enginar@bil.omu.edu.tr", "12345"));
    }

    public String getIsim () {
        return this.Isim;
    }

    public String getEmail () {
        return this.Email;
    }

    public String getSifre () {
        return this.Sifre;
    }

}
