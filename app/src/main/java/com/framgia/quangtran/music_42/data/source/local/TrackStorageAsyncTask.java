package com.framgia.quangtran.music_42.data.source.local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.framgia.quangtran.music_42.data.model.Track;
import com.framgia.quangtran.music_42.data.source.TrackDataSource;
import com.framgia.quangtran.music_42.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TrackStorageAsyncTask extends AsyncTask<Void, Void, List<Track>> {
    private static final String SQL_SELECTION = "=?";
    private static final String MESSAGE = "GET DATA LOCAL IS FAILURE";
    private TrackDataSource.DataCallback<Track> mCallback;
    private ContentResolver mContentResolver;

    public TrackStorageAsyncTask(ContentResolver contentResolver,
                                 TrackDataSource.DataCallback<Track> callback) {
        this.mContentResolver = contentResolver;
        this.mCallback = callback;
    }

    @Override
    protected List<Track> doInBackground(Void... voids) {
        return loadTracks();
    }

    @Override
    protected void onPostExecute(List<Track> trackList) {
        super.onPostExecute(trackList);
        if (trackList != null) {
            mCallback.onSuccess(trackList);
        } else {
            mCallback.onFailed(MESSAGE);
        }
    }

    private List<Track> loadTracks() {
        List<Track> tracks = new ArrayList<>();
        String[] projections = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID};
        Cursor cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projections, null, null,
                MediaStore.Audio.Media.DISPLAY_NAME);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media
                        ._ID));
                int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media
                        .ALBUM_ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media
                        .DISPLAY_NAME));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media
                        .ARTIST));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media
                        .DATA));
                Track track = new Track();
                track.setTitle(title);
                track.setUserName(artist);
                track.setDownLoadUrl(path);
                track.setStreamUrl(path);
                track.setArtWorkUrl(getAlbumArt(albumId));
                track.setOffline(true);
                tracks.add(track);
            }
        }
        cursor.close();
        return tracks;
    }

    private String getAlbumArt(int albumId) {
        String[] projections = {MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM_ART};
        String selection = StringUtil.append(MediaStore.Audio.Albums._ID,
                SQL_SELECTION);
        String[] selectionArgs = {String.valueOf(albumId)};
        Cursor cursorAlbum = mContentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projections, selection,
                selectionArgs,
                null);
        if (cursorAlbum == null) return null;
        String artwork = null;
        if (cursorAlbum.moveToFirst()) {
            artwork = cursorAlbum.getString(
                    cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
        }
        cursorAlbum.close();
        return artwork;
    }
}
