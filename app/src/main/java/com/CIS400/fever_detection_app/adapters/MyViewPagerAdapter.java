package com.CIS400.fever_detection_app.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private String[] titles;
    private Fragment currentFragment;
    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> list, String[] titles){
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, Object object) {
        currentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
