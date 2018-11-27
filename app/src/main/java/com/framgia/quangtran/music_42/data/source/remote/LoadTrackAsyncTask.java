package com.framgia.quangtran.music_42.data.source.remote;

import com.framgia.quangtran.music_42.data.model.Track;
import com.framgia.quangtran.music_42.data.source.TrackDataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadTrackAsyncTask extends BaseLoadTracksAsyncTask<Track> {
    public LoadTrackAsyncTask(TrackDataSource.DataCallback<Track> callback) {
        super(callback);
    }

    @Override
    public List<Track> convertJson(String jsonString) {
        List<Track> tracks = new ArrayList<>();
        try {
            JSONObject result = new JSONObject(jsonString);
            JSONArray trackArray = result.getJSONArray(Track.JSONKey.COLLECTION);
            for (int i = 0; i < trackArray.length(); i++) {
                JSONObject trackDetail = trackArray.getJSONObject(i);
                JSONObject trackInfo = trackDetail.getJSONObject(Track.JSONKey.TRACK);
                int id = trackInfo.getInt(Track.JSONKey.ID);
                String tittle = trackInfo.getString(Track.JSONKey.TITLE);
                String artwork = trackInfo.getString(Track.JSONKey.ARTWORK_URL);
                JSONObject userDetail = trackInfo.getJSONObject(Track.JSONKey.KEY_USER);
                String fullname = userDetail.getString(Track.JSONKey.KEY_USER_NAME);
                Track track = new Track();
                track.setId(id);
                track.setTitle(tittle);
                track.setArtWorkUrl(artwork);
                track.setUserName(fullname);
                tracks.add(track);
            }
        } catch (Exception e) {
            mException = e;
        }
        return tracks;
    }
}
