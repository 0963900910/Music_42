package com.framgia.quangtran.music_42.mediaplayer;

import android.support.annotation.IntDef;

import com.framgia.quangtran.music_42.data.model.Track;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public interface MediaPlayerInterface {

    void initMediaPlayer();

    void initPlay(int position);

    void start();

    void pause();

    void reset();

    void release();

    void stop();

    void next();

    void previous();

    void seekTo(int msec);

    int getStatus();

    int getDuration();

    int getCurrentPosition();

    void setTrackCurrentPosition(int position);

    int getTrackCurrentPosition();

    void setTracks(List<Track> tracks);

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({StatusPlayerType.IDLE, StatusPlayerType.PREPARING, StatusPlayerType.PLAYING,
            StatusPlayerType.PAUSE, StatusPlayerType.STOP, StatusPlayerType.RELEASE})
    @interface StatusPlayerType {
        int IDLE = 0;
        int PREPARING = 1;
        int PLAYING = 2;
        int PAUSE = 3;
        int STOP = 4;
        int RELEASE = 5;
    }
}
