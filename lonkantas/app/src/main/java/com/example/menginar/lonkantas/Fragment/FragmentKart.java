package com.example.menginar.lonkantas.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.menginar.lonkantas.R;

/**
 * Created by Menginar on 25.6.2016.
 */
public class FragmentKart extends Fragment {
    public FragmentKart() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kartlar, container, false);
    }
}
