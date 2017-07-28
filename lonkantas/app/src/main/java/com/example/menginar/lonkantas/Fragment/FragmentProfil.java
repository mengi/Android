package com.example.menginar.lonkantas.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.menginar.lonkantas.Model.Kisi;
import com.example.menginar.lonkantas.R;

/**
 * Created by Menginar on 25.6.2016.
 */
public class FragmentProfil extends Fragment {

    TextView _text_isim, _text_email;

    public FragmentProfil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        _text_isim = (TextView) view.findViewById(R.id.text_isim);
        _text_email = (TextView) view.findViewById(R.id.text_email);

        _text_email.setText(Kisi.KISILER.get(0).getEmail());
        _text_isim.setText(Kisi.KISILER.get(0).getIsim());

        return view;
    }
}
