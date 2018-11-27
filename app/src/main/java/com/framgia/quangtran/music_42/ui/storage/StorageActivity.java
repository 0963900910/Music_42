package com.framgia.quangtran.music_42.ui.storage;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.quangtran.music_42.R;
import com.framgia.quangtran.music_42.data.model.Track;
import com.framgia.quangtran.music_42.data.repository.TrackRepository;
import com.framgia.quangtran.music_42.data.source.local.TrackLocalDataSource;
import com.framgia.quangtran.music_42.data.source.remote.TrackRemoteDataSource;
import com.framgia.quangtran.music_42.ui.play.PlayActivity;
import com.framgia.quangtran.music_42.ui.storage.contract.StorageStyle;

import java.util.ArrayList;
import java.util.List;

public class StorageActivity extends AppCompatActivity
        implements StorageContract.View, StorageAdapter.StorageClickListener {
    private StorageContract.Presenter mPresenter;
    private ContentResolver mResolver;
    private TextView mTextTitle;
    private String mKey = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_personal);
        initUI();
    }

    public static Intent getStorageIntent(Context context, String key) {
        Intent intent = new Intent(context, StorageActivity.class);
        intent.putExtra(StorageStyle.StorageKey.KEY, key);
        return intent;
    }

    private void initUI() {
        mKey = getIntent().getStringExtra(StorageStyle.StorageKey.KEY);
        mTextTitle = findViewById(R.id.text_title);
        mTextTitle.setText(mKey);
        mResolver = getApplication().getContentResolver();
        TrackRepository repository = TrackRepository.getInstance(TrackRemoteDataSource
                .getInstance(), TrackLocalDataSource.getInstance(mResolver));
        mPresenter = new StoragePresenter(repository, mResolver);
        mPresenter.setView(this);
        getTracks();
    }

    private void getTracks() {
        if (mKey.equals(StorageStyle.StorageKey.LOCAL)) {
            mPresenter.loadOfflineMusic();
        } else if (mKey.equals(StorageStyle.StorageKey.DOWNLOAD)) {
            mPresenter.getDownloadTrack();
        } else {
            mPresenter.getFavoriteTrack();
        }
    }

    private void initRecycler(List<Track> tracks) {
        RecyclerView mRecyclerLocal = findViewById(R.id.recycler_music);
        mRecyclerLocal.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        StorageAdapter storageAdapter = new StorageAdapter(tracks, this);
        mRecyclerLocal.setAdapter(storageAdapter);
    }

    @Override
    public void onSuccess(List<Track> data) {
        if (data != null) {
            initRecycler(data);
        }
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickPlayMusic(List<Track> tracks, int i) {
        startActivity(PlayActivity.getPlayIntent(this, (ArrayList<Track>) tracks,i));
    }
}
