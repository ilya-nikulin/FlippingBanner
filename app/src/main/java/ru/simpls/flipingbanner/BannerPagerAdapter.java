package ru.simpls.flipingbanner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by nikulin on 13.10.2014.
 */
public class BannerPagerAdapter extends FragmentStatePagerAdapter {
    public BannerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return BannerPagerFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}