package com.android.myapplication.ui.fragmentanalysis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.myapplication.R;
import com.android.myapplication.adapter.ViewPagerAdapter;
import com.android.myapplication.ui.fragment.FragmentComplaint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menginar on 25.09.2017.
 */

public class FragmentStockAnalysis extends Fragment {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_stock_analysis, container, false);
        getInit();

        return view;
    }

    private void getInit() {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentDaily(), "Günlük");
        adapter.addFragment(new FragmentWeekly(), "Haftalık");
        adapter.addFragment(new FragmentMonthly(), "Aylık");
        viewPager.setAdapter(adapter);
    }

}
