package com.framgia.quangtran.music_42.ui.search;

import com.framgia.quangtran.music_42.data.model.Track;
import com.framgia.quangtran.music_42.data.repository.TrackRepository;
import com.framgia.quangtran.music_42.data.source.TrackDataSource;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private TrackRepository mRepository;

    public SearchPresenter(TrackRepository repository, SearchContract.View view) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void searchTracks(String api) {
        mRepository.searchTracks(api, new TrackDataSource.DataCallback<Track>() {
            @Override
            public void onSuccess(List<Track> datas) {
                mView.showResult(datas);
            }

            @Override
            public void onFailed(String message) {
                mView.showNoResult(message);
            }
        });
    }
}
