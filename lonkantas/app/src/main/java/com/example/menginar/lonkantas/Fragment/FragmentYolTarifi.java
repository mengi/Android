package com.example.menginar.lonkantas.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.menginar.lonkantas.Adapter.AdapterYolTarifi;
import com.example.menginar.lonkantas.R;
import com.example.menginar.lonkantas.ui.Dekorasyon;

/**
 * Created by Menginar on 25.6.2016.
 */
public class FragmentYolTarifi extends Fragment {
    private LinearLayoutManager linearLayout;

    public FragmentYolTarifi() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grup_items, container, false);

        RecyclerView reciclador = (RecyclerView)view.findViewById(R.id.reciclador);
        linearLayout = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(linearLayout);

        AdapterYolTarifi adapter = new AdapterYolTarifi();
        reciclador.setAdapter(adapter);
        reciclador.addItemDecoration(new Dekorasyon(getActivity()));

        return view;
    }

}
