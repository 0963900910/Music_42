package com.framgia.quangtran.music_42.ui.home;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.framgia.quangtran.music_42.R;
import com.framgia.quangtran.music_42.ui.home.adapters.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {
    private ViewPager mViewPagerMusic;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        startService();
    }

    private void initUI() {
        Bundle bundle = getIntent().getExtras();
        mViewPagerMusic = findViewById(R.id.view_pager);
        mViewPagerMusic.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), bundle));
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPagerMusic);
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            unbindService(mConnection);
        }
    };

    public void startService() {
    }
}
