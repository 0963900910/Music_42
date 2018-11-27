package com.framgia.quangtran.music_42.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.framgia.quangtran.music_42.data.model.Track;
import com.framgia.quangtran.music_42.mediaplayer.MediaPlayerManager;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service implements ServiceInterface {
    private final IBinder mIBinder = new LocalBinder();
    private MediaPlayerManager mMediaPlayerManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayerManager = MediaPlayerManager.getInstance(this);
        mMediaPlayerManager.initMediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void start() {
        play(mMediaPlayerManager.getTrackCurrentPosition());
        mMediaPlayerManager.start();
    }

    @Override
    public void preparing() {

    }

    @Override
    public void pause() {

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
    public void setTracks(List<Track> tracks) {
        mMediaPlayerManager.setTracks((ArrayList<Track>) tracks);
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
