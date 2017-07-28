package com.example.javier.spoonplayers.Fragments;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javier.spoonplayers.MainActivity;
import com.example.javier.spoonplayers.R;
import com.example.javier.spoonplayers.Tabs.TabsAdapters.TabsViewPagerAdapter;
import com.example.javier.spoonplayers.Tabs.TabsUtils.SlidingTabLayout;

public class FragmentHome extends Fragment {
    View view;
    ViewPager pager;
    TabsViewPagerAdapter tabsViewPagerAdapter;
    SlidingTabLayout tabs;
    CharSequence titles[] = {"Duyurular", "Resimler", "Klipler"};
    int tabNumber = titles.length;
    int tabsPaddingTop;
    TypedValue typedValueToolbarHeight = new TypedValue();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_design, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.home);
        setupTabs();
        return view;
    }

    public void setupTabs() {
        tabsViewPagerAdapter = new TabsViewPagerAdapter(getFragmentManager(), titles, tabNumber);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(tabsViewPagerAdapter);
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(false);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.md_white_1000);
            }
        });

        getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValueToolbarHeight, true);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (Build.VERSION.SDK_INT >= 19) {
                tabsPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics()) + convertToPx(22);
            } else {
                tabsPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics());
            }
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= 21) {
                tabsPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics());
            }
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
                tabsPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics()) + convertToPx(22);
            }
            if (Build.VERSION.SDK_INT < 19) {
                tabsPaddingTop = TypedValue.complexToDimensionPixelSize(typedValueToolbarHeight.data, getResources().getDisplayMetrics());
            }
        }
        tabs.setPadding(convertToPx(42), tabsPaddingTop, convertToPx(14), 0);

        tabs.setViewPager(pager);
    }

    public int convertToPx(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

