package com.framgia.quangtran.music_42.mediaplayer;

import android.support.annotation.IntDef;
import android.widget.TextView;

import com.framgia.quangtran.music_42.data.model.Track;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public interface ITracksPlayerManager {

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

    int getState();

    int getDuration();

    int getCurrentPosition();

    void setTrackCurrentPosition(int position);

    int getTrackCurrentPosition();

    void setTracks(List<Track> tracks);

    void setTrackInfo(TextView title, TextView artist);

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MediaPayerStates.IDLE, MediaPayerStates.PREPARING, MediaPayerStates.PLAYING,
            MediaPayerStates.PAUSE, MediaPayerStates.STOP, MediaPayerStates.RELEASE})
    @interface MediaPayerStates {
        int IDLE = 0;
        int PREPARING = 1;
        int PLAYING = 2;
        int PAUSE = 3;
        int STOP = 4;
        int RELEASE = 5;
    }
}
