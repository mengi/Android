package com.example.menginar.lonkantas.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.menginar.lonkantas.Adapter.AdapterBas;
import com.example.menginar.lonkantas.R;

/**
 * Created by Menginar on 25.6.2016.
 */
public class FragmentBaslat extends Fragment {

    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdapterBas adapterBas;

    public FragmentBaslat() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baslat, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);

        adapterBas = new AdapterBas();
        reciclador.setAdapter(adapterBas);
        return view;
    }
}
