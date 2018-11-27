package com.framgia.quangtran.music_42.service;

import com.framgia.quangtran.music_42.data.model.Track;

import java.util.List;

public interface ServiceInterface {
    void start();

    void preparing();

    void pause();

    void next();

    void previous();

    void seekTo(int msec);

    long getCurrentPosition();

    long getDuration();

    void setTracks(List<Track> tracks);
}
