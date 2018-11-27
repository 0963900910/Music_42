package com.framgia.quangtran.music_42.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.framgia.quangtran.music_42.data.model.Track;

import java.io.IOException;
import java.util.List;

public class MediaPlayerManager extends MediaPlayerSetting implements MediaPlayerInterface
        , MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {
    private List<Track> mTracks;
    private static MediaPlayerManager sInstance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private int mTrackCurrentPosition;
    protected Exception mException;

    private MediaPlayerManager(Context context) {
        super();
        mContext = context;
    }

    public static MediaPlayerManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MediaPlayerManager(context);
        }
        return sInstance;
    }

    @Override
    public void initMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
        } else {
            mMediaPlayer = new MediaPlayer();
        }
    }

    @Override
    public void initPlay(int position) {
        if (!mTracks.isEmpty() && position >= 0) {
            Uri uri = Uri.parse(mTracks.get(position).getStreamUrl());
            try {
                mMediaPlayer.setDataSource(mContext, uri);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                mException = e;
            }
        }
    }

    @Override
    public void start() {
        mMediaPlayer.start();
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
    }

    @Override
    public void reset() {
        mMediaPlayer.reset();
    }

    @Override
    public void release() {
        mMediaPlayer.release();
    }

    @Override
    public void stop() {
        mMediaPlayer.stop();
    }

    @Override
    public void next() {
        if (mTrackCurrentPosition < mTracks.size() - 1) {
            setTrackCurrentPosition(mTrackCurrentPosition++);
            initMediaPlayer();
            initPlay(mTrackCurrentPosition);
            start();
        }
    }

    @Override
    public void previous() {
        if (mTrackCurrentPosition > 0) {
            setTrackCurrentPosition(mTrackCurrentPosition--);
            initMediaPlayer();
            initPlay(mTrackCurrentPosition);
            start();
        }
    }

    @Override
    public void seekTo(int msec) {
        mMediaPlayer.seekTo(msec);
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public void setTrackCurrentPosition(int position) {
        mTrackCurrentPosition = position;
    }

    @Override
    public int getTrackCurrentPosition() {
        return mTrackCurrentPosition;
    }

    @Override
    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        switch (getLoopType()) {
            case MediaPlayerSetting.LoopType.NONE:
                next();
                break;
            case MediaPlayerSetting.LoopType.ONE:
                initMediaPlayer();
                initPlay(getTrackCurrentPosition());
                break;
            case MediaPlayerSetting.LoopType.ALL:
                if (getTracksSize() != 0 && getTracksSize() - 1
                        == getTrackCurrentPosition()) {
                    initMediaPlayer();
                    setTrackCurrentPosition(0);
                    initPlay(0);
                } else {
                    next();
                }
                break;
        }
    }

    public int getTracksSize() {
        return mTracks.size();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        start();
    }
}
