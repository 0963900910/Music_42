package com.framgia.quangtran.music_42.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.framgia.quangtran.music_42.R;
import com.framgia.quangtran.music_42.data.model.Genre;
import com.framgia.quangtran.music_42.data.model.Track;
import com.framgia.quangtran.music_42.data.repository.GenreRepository;
import com.framgia.quangtran.music_42.data.source.genre.GenreLocalDataSource;
import com.framgia.quangtran.music_42.ui.DisableScrollLinearLayout;
import com.framgia.quangtran.music_42.ui.genre.GenreActivity;
import com.framgia.quangtran.music_42.ui.home.adapters.GenresAdapter;
import com.framgia.quangtran.music_42.ui.home.adapters.TodayAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment
        implements GenresAdapter.GenreClickListener, HomeContract.View {
    private static final String ARGUMENT_TRACKS = "ARGUMENT_TRACKS";
    private List<Track> mTracks;
    private HomeContract.Presenter mHomePresenter;
    private View mView;
    private static TodayAdapter.ClickTrack sClickTrack;

    public static HomeFragment newInstance(ArrayList<Track> tracks,
                                           TodayAdapter.ClickTrack clickTrack) {
        sClickTrack = clickTrack;
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGUMENT_TRACKS, tracks);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        if (mTracks == null) mTracks = getArguments().getParcelableArrayList(ARGUMENT_TRACKS);
        initView();
        if (mTracks != null) {
            initRecyclerToday(mTracks);
        }
        return mView;
    }

    private void initView() {
        GenreRepository repository = GenreRepository.getInstance(GenreLocalDataSource.getInstance());
        mHomePresenter = new HomePresenter(repository, this);
        mHomePresenter.getGenres();
    }

    private void initRecyclerToday(List<Track> tracks) {
        RecyclerView mRecyclerToday = mView.findViewById(R.id.recycler_recent);
        mRecyclerToday.setLayoutManager(new DisableScrollLinearLayout(getContext()));
        TodayAdapter todayAdapter = new TodayAdapter(tracks,sClickTrack);
        mRecyclerToday.setAdapter(todayAdapter);
    }

    private void initRecyclerGenre(List<Genre> genres) {
        RecyclerView mRecyclerGenres = mView.findViewById(R.id.recycler_genres);
        mRecyclerGenres.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        GenresAdapter genresAdapter = new GenresAdapter(genres, this);
        mRecyclerGenres.setAdapter(genresAdapter);
    }

    @Override
    public void onItemClickGenre(Genre genre) {
        startActivity(GenreActivity.getGenreIntent(getActivity(), genre));
    }

    @Override
    public void onSuccess(List<Genre> genres) {
        initRecyclerGenre(genres);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
