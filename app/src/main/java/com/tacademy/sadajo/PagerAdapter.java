package com.tacademy.sadajo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by EUNZY on 2016. 11. 24..
 */


public class PagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<android.support.v4.app.Fragment> Fragment = new ArrayList<>();
    private final ArrayList<String> tabTitles = new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void appendFragment(Fragment fragment, String title) {
        Fragment.add(fragment);
        tabTitles.add(title);

    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.get(position);
    }

    @Override
    public int getCount() {
        return Fragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}