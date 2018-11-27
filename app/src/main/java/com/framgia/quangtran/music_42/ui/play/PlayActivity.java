package com.framgia.quangtran.music_42.ui.play;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.framgia.quangtran.music_42.R;
import com.framgia.quangtran.music_42.data.model.Track;
import com.framgia.quangtran.music_42.mediaplayer.ITracksPlayerManager;
import com.framgia.quangtran.music_42.service.MyService;
import com.framgia.quangtran.music_42.service.ServiceManager;
import com.framgia.quangtran.music_42.util.UtilTime;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {
    private static final String BUNDLE_TRACKS = "com.framgia.quangtran.music_42.ui.genre.BUNDLE_TRACKS";
    private static final String EXTRA_POSITION = "com.framgia.quangtran.music_42.ui.genre.EXTRA_POSITION";
    private static final int PROGRESS_START = 0;
    private static final int PROGRESS_MAX = 100;
    private static final int DELAY_TIME = 1000;
    private ImageView mImageShuffle;
    private ImageView mImagePrevious;
    private ImageView mImageNext;
    private ImageView mImagePlay;
    private ImageView mImageLoop;
    private SeekBar mSeekBar;
    private TextView mCurrentTime;
    private TextView mTotalTime;
    private TextView mTextTitle;
    private TextView mTextArtist;
    private MyService mService;
    private UtilTime mUtilTime;
    private android.os.Handler mHandler = new android.os.Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_music);
        startService();
        initUI();
    }

    public static Intent getPlayIntent(Context context, ArrayList<Track> tracks, int i) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putParcelableArrayListExtra(BUNDLE_TRACKS, tracks);
        intent.putExtra(EXTRA_POSITION, i);
        return intent;
    }

    private void initUI() {
        mImageShuffle = findViewById(R.id.image_shuffle);
        mImagePrevious = findViewById(R.id.image_previous);
        mImagePlay = findViewById(R.id.image_play);
        mImageNext = findViewById(R.id.image_next);
        mImageLoop = findViewById(R.id.image_loop);
        mTextTitle = findViewById(R.id.text_title);
        mTextArtist = findViewById(R.id.text_artist);
        mSeekBar = findViewById(R.id.seek_bar_track);
        mSeekBar.setProgress(PROGRESS_START);
        mSeekBar.setMax(PROGRESS_MAX);
        mSeekBar.setOnSeekBarChangeListener(this);
        mCurrentTime = findViewById(R.id.text_current_time);
        mTotalTime = findViewById(R.id.text_total_time);
        setListener();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.LocalBinder binder = (MyService.LocalBinder) iBinder;
            mService = binder.getService();
            startMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            unbindService(mConnection);
        }
    };

    public void startService() {
        Intent startService = new Intent(this, MyService.class);
        ServiceManager serviceManager = new ServiceManager(this, startService,
                mConnection, Context.BIND_AUTO_CREATE);
        serviceManager.bindService();
        startService(startService);
        Intent serviceIntent = MyService.getMyServiceIntent(PlayActivity.this);
        if (mService == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        }
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

    void startMusic() {
        List<Track> tracks = getIntent().getParcelableArrayListExtra(BUNDLE_TRACKS);
        if (tracks != null) {
            mService.setTracks(tracks);
            mService.play(getIntent().getIntExtra(EXTRA_POSITION, 0));
            mService.start();
            mService.setTrackInfo(mTextTitle, mTextArtist);
            mHandler.postDelayed(mUpdateTimes, DELAY_TIME);
        }
    }

    public void setListener() {
        mImageShuffle.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        mImageNext.setOnClickListener(this);
        mImageLoop.setOnClickListener(this);
        mCurrentTime.setOnClickListener(this);
        mTotalTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_shuffle:
            case R.id.image_previous:
                mService.previous();
                mImagePlay.setImageResource(R.drawable.ic_pause);
                mService.setTrackInfo(mTextTitle, mTextArtist);
                break;
            case R.id.image_play:
                changePlayPauseState();
                break;
            case R.id.image_next:
                mService.next();
                mService.setTrackInfo(mTextTitle, mTextArtist);
                mImagePlay.setImageResource(R.drawable.ic_pause);
                break;
            case R.id.image_loop:
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimes);
        if (mService != null) {
            int totalDuration = (int) mService.getDuration();
            int progress = mSeekBar.getProgress();
            int current = UtilTime.progressToTimer(progress, totalDuration);
            mService.seekTo(current);
            mHandler.postDelayed(mUpdateTimes, DELAY_TIME);
        }
    }

    public void changePlayPauseState() {
        if (mService.getState() == ITracksPlayerManager.MediaPayerStates.PAUSE) {
            mService.start();
            mImagePlay.setImageResource(R.drawable.ic_pause);
        } else {
            mService.pause();
            mImagePlay.setImageResource(R.drawable.ic_play_button);
        }
    }

    private Runnable mUpdateTimes = (new Runnable() {
        @Override
        public void run() {
            mUtilTime = new UtilTime();
            long currentTime = mService.getCurrentPosition();
            long totalTime = mService.getDuration();
            mCurrentTime.setText(mUtilTime.TotalTime(currentTime));
            mTotalTime.setText(mUtilTime.TotalTime(totalTime));
            int progress = (int) (UtilTime.getProgressPercentage(currentTime, totalTime));
            mSeekBar.setProgress(progress);
            if (mSeekBar.getProgress() == mSeekBar.getMax()) {
                mService.next();
            }
            mHandler.postDelayed(this, PROGRESS_MAX);
        }
    });
}
