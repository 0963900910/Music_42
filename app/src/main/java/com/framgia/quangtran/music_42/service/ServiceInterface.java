package com.framgia.quangtran.music_42.service;

import android.widget.TextView;

import com.framgia.quangtran.music_42.data.model.Track;

import java.util.List;

public interface ServiceInterface {
    void start();

    void pause();

    void next();

    void previous();

    void seekTo(int msec);

    long getCurrentPosition();

    long getDuration();

    int getState();

    void setTracks(List<Track> tracks);

    void setTrackInfo(TextView title, TextView artist);
}
