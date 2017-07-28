package com.example.menginar.lonkantas.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.menginar.lonkantas.Adapter.AdapterKategori;
import com.example.menginar.lonkantas.Model.Gida;
import com.example.menginar.lonkantas.R;

/**
 * Created by Menginar on 25.6.2016.
 */
public class FragmentKategori extends Fragment {

    private static final String INDICE_SECCION
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdapterKategori adapterKategori;

    public static FragmentKategori nuevaInstancia(int indiceSeccion) {
        FragmentKategori fragment = new FragmentKategori();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grup_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador.setLayoutManager(layoutManager);

        int indiceSeccion = getArguments().getInt(INDICE_SECCION);

        switch (indiceSeccion) {
            case 0:
                adapterKategori = new AdapterKategori(Gida.YEMEKLER);
                break;
            case 1:
                adapterKategori = new AdapterKategori(Gida.ICECEKLER);
                break;
            case 2:
                adapterKategori = new AdapterKategori(Gida.TATLILAR);
                break;
        }

        reciclador.setAdapter(adapterKategori);

        return view;
    }
}
