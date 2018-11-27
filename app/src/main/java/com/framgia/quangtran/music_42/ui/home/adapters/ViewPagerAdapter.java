package com.framgia.quangtran.music_42.ui.home.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.quangtran.music_42.data.model.Track;
import com.framgia.quangtran.music_42.ui.home.HomeFragment;
import com.framgia.quangtran.music_42.ui.personal.PersonalFragment;
import com.framgia.quangtran.music_42.ui.setting.SettingFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final String HOME = "HOME";
    private static final String PERSONAL = "PERSONAL";
    private static final String SETTING = "SETTING";
    private static final String BUNDLE_TRACKS = "com.framgia.quangtran.music_42.ui.genre.BUNDLE_TRACKS";
    private static final int TOTAL_TABS = 3;
    private static final int TAB_HOME = 0;
    private static final int TAB_PERSONAL = 1;
    private static final int TAB_SETTING = 2;
    public Bundle mBundle;
    private TodayAdapter.ClickTrack mContext;

    public ViewPagerAdapter(FragmentManager fm, Bundle bundle, TodayAdapter.ClickTrack clickTrack) {
        super(fm);
        mBundle = bundle;
        mContext = clickTrack;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case TAB_HOME:
                if (mBundle != null) {
                    ArrayList<Track> tracks = mBundle.getParcelableArrayList(BUNDLE_TRACKS);
                    return HomeFragment.newInstance(tracks, mContext);
                }
            case TAB_PERSONAL:
                return PersonalFragment.newInstance();
            case TAB_SETTING:
                return SettingFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_TABS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case TAB_HOME:
                title = HOME;
                break;
            case TAB_PERSONAL:
                title = PERSONAL;
                break;
            case TAB_SETTING:
                title = SETTING;
                break;
        }
        return title;
    }
}
