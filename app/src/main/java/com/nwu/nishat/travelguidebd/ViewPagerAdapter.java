package com.nwu.nishat.travelguidebd;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mmFragmentTitleList  = new ArrayList<>();
    private final List<String> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mmFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mmFragmentTitleList.add(fragment);
        mFragmentList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position);
    }
}