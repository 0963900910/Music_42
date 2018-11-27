package com.framgia.quangtran.music_42.ui.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.quangtran.music_42.R;
import com.framgia.quangtran.music_42.data.model.Track;
import com.framgia.quangtran.music_42.service.MyService;
import com.framgia.quangtran.music_42.service.ServiceManager;
import com.framgia.quangtran.music_42.ui.home.adapters.TodayAdapter;
import com.framgia.quangtran.music_42.ui.home.adapters.ViewPagerAdapter;
import com.framgia.quangtran.music_42.ui.play.PlayActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ServiceConnection,
        TodayAdapter.ClickTrack {
    private int mStartService = 0;
    private int mMarginDefault = 0;
    private int mMarginBottom = 90;
    private ViewPager mViewPagerMusic;
    private TabLayout mTabLayout;
    private ServiceManager mServiceManager;
    private MyService mService;
    private TextView mTextTitle;
    private TextView mTextArtist;
    private ConstraintLayout mMiniPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
    }

    private void initUI() {
        Bundle bundle = getIntent().getExtras();
        mMiniPlayer = findViewById(R.id.mini_player);
        mViewPagerMusic = findViewById(R.id.view_pager);
        mViewPagerMusic.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),
                bundle, this));
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPagerMusic);
        mTextTitle = findViewById(R.id.text_song_name);
        mTextArtist = findViewById(R.id.text_singer_name);
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mService = ((MyService.LocalBinder) iBinder).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
    }

    private void connectService(List<Track> tracks, int position) {
        ServiceConnection connection = this;
        Intent intent = new Intent(this, MyService.class);
        mServiceManager = new ServiceManager(this, intent, connection,
                Context.BIND_AUTO_CREATE);
        startActivity(PlayActivity.getPlayIntent(this,
                (ArrayList<Track>) tracks, position));
        mServiceManager.bindService();
        mMiniPlayer.setVisibility(View.VISIBLE);
        setMargins(mViewPagerMusic, mMarginDefault, mMarginDefault, mMarginDefault, mMarginBottom);
    }

    @Override
    public void onClickTrack(List<Track> tracks, int i) {
        if (mStartService == 0) {
            connectService(tracks, i);
            mStartService++;
        } else {
            mService.play(i);
            mService.start();
            mService.setTrackInfo(mTextTitle, mTextArtist);
        }
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
