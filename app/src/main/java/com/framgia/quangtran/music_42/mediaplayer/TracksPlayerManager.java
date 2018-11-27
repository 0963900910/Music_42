package com.framgia.quangtran.music_42.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.TextView;

import com.framgia.quangtran.music_42.data.model.Track;

import java.io.IOException;
import java.util.List;

public class TracksPlayerManager extends TracksPlayerSetting implements ITracksPlayerManager
        , MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {
    private static final int FIRST_POSITION = 0;
    private List<Track> mTracks;
    private static TracksPlayerManager sInstance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private int mTrackCurrentPosition;
    private Exception mException;
    private int mState;

    private TracksPlayerManager(Context context) {
        super();
        mContext = context;
    }

    public static TracksPlayerManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TracksPlayerManager(context);
        }
        return sInstance;
    }

    @Override
    public void initMediaPlayer() {
        if (mMediaPlayer != null && getState() != MediaPayerStates.RELEASE) {
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
                setStatus(MediaPayerStates.PREPARING);
            } catch (IOException e) {
                mException = e;
            }
        }
    }

    private void setStatus(int state) {
        mState = state;
    }

    @Override
    public void start() {
        mMediaPlayer.start();
        setStatus(MediaPayerStates.PLAYING);
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
        setStatus(MediaPayerStates.PAUSE);
    }

    @Override
    public void reset() {
        mMediaPlayer.reset();
        setStatus(MediaPayerStates.IDLE);
    }

    @Override
    public void release() {
        mMediaPlayer.release();
        setStatus(MediaPayerStates.RELEASE);
    }

    @Override
    public void stop() {
        mMediaPlayer.stop();
        setStatus(MediaPayerStates.STOP);
    }

    @Override
    public void next() {
        if (mTrackCurrentPosition < mTracks.size() - 1) {
            setTrackCurrentPosition(++mTrackCurrentPosition);
            initMediaPlayer();
            initPlay(getTrackCurrentPosition());
            start();
        } else {
            setTrackCurrentPosition(FIRST_POSITION);
            initMediaPlayer();
            initPlay(getTrackCurrentPosition());
            start();
        }
    }

    @Override
    public void previous() {
        if (mTrackCurrentPosition > 0) {
            setTrackCurrentPosition(--mTrackCurrentPosition);
            initMediaPlayer();
            initPlay(mTrackCurrentPosition);
            start();
        } else {
            setTrackCurrentPosition(mTracks.size() - 1);
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
    public int getState() {
        return mState;
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
            case TracksPlayerSetting.LoopType.NONE:
                next();
                break;
            case TracksPlayerSetting.LoopType.ONE:
                initMediaPlayer();
                initPlay(getTrackCurrentPosition());
                break;
            case TracksPlayerSetting.LoopType.ALL:
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

    @Override
    public void setTrackInfo(TextView title, TextView artist) {
        title.setText(mTracks.get(getTrackCurrentPosition()).getTitle());
        artist.setText(mTracks.get(getTrackCurrentPosition()).getUserName());
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
