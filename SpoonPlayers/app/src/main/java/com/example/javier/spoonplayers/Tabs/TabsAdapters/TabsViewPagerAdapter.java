package com.example.javier.spoonplayers.Tabs.TabsAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.javier.spoonplayers.Tabs.TabsViews.TabNotice;
import com.example.javier.spoonplayers.Tabs.TabsViews.TabPicture;
import com.example.javier.spoonplayers.Tabs.TabsViews.TabVideo;

public class TabsViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int tabNumber;

    // Constructor
    public TabsViewPagerAdapter(FragmentManager fragmentManager, CharSequence titles[], int tabNumber) {
        super(fragmentManager);

        this.titles = titles;
        this.tabNumber = tabNumber;

    }

    // Return Fragment for each position
    @Override
    public Fragment getItem(int position) {
        TabNotice tabNotice = new TabNotice();
        TabPicture tabPicture = new TabPicture();
        TabVideo tabVideo = new TabVideo();
        switch (position) {
            case 0:
                return tabNotice;
            case 1:
                return tabPicture;
            case 2:
                return tabVideo;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    // Return tab number.
    @Override
    public int getCount() {
        return tabNumber;
    }
}