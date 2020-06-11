package com.example.gamerapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> frglist = new ArrayList<>();
    private final List<String> frgtitle = new ArrayList<>();
    public TabViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return frglist.get(position);
    }

    @Override
    public int getCount() {
        return frgtitle.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return frgtitle.get(position);
    }
    public void AddFragment(Fragment frg,String title)
    {
        frgtitle.add(title);
        frglist.add(frg);
    }
}
