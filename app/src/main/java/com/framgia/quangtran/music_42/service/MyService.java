package com.framgia.quangtran.music_42.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.framgia.quangtran.music_42.data.model.Track;
import com.framgia.quangtran.music_42.mediaplayer.TracksPlayerManager;

import java.util.List;

public class MyService extends Service implements ServiceInterface {
    private final IBinder mIBinder = new LocalBinder();
    private TracksPlayerManager mMediaPlayerManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    public class TrackBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayerManager = TracksPlayerManager.getInstance(this);
        mMediaPlayerManager.initMediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void start() {
        mMediaPlayerManager.start();
    }

    @Override
    public void pause() {
        mMediaPlayerManager.pause();
    }

    @Override
    public void next() {
        mMediaPlayerManager.next();
    }

    @Override
    public void previous() {
        mMediaPlayerManager.previous();
    }

    @Override
    public void seekTo(int msec) {
        mMediaPlayerManager.seekTo(msec);
    }

    @Override
    public long getCurrentPosition() {
        return mMediaPlayerManager.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return mMediaPlayerManager.getDuration();
    }

    @Override
    public int getState() {
        return mMediaPlayerManager.getState();
    }

    @Override
    public void setTracks(List<Track> tracks) {
        mMediaPlayerManager.setTracks(tracks);
    }

    @Override
    public void setTrackInfo(TextView title, TextView artist) {
        mMediaPlayerManager.setTrackInfo(title, artist);
    }

    public class LocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    public static Intent getMyServiceIntent(Context context) {
        Intent intent = new Intent(context, MyService.class);
        return intent;
    }

    public void play(int position) {
        mMediaPlayerManager.initMediaPlayer();
        mMediaPlayerManager.setTrackCurrentPosition(position);
        mMediaPlayerManager.initPlay(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
