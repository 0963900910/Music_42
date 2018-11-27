package com.framgia.quangtran.music_42.ui.home.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.quangtran.music_42.R;
import com.framgia.quangtran.music_42.data.model.Genre;
import com.framgia.quangtran.music_42.data.model.Track;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ViewHolder> {
    private List<Genre> mGenres;
    private GenreClickListener mListener;
    private LayoutInflater mInflater;

    public GenresAdapter(List<Genre> genres, GenreClickListener listener) {
        this.mListener = listener;
        this.mGenres = genres;
    }

    @NonNull
    @Override
    public GenresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View contactView = mInflater.inflate(R.layout.item_recycler_genres_home, viewGroup,
                false);
        return new GenresAdapter.ViewHolder(contactView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bindData(mGenres.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextGenreName;
        private ImageView mImageGenre;
        private ConstraintLayout mConstraintLayout;
        private Genre mGenre;
        private GenreClickListener mGenreClickListener;

        public ViewHolder(@NonNull View itemView, GenreClickListener genreClickListener) {
            super(itemView);
            mConstraintLayout = itemView.findViewById(R.id.constraint_genre);
            mTextGenreName = itemView.findViewById(R.id.text_name_genre);
            mImageGenre = itemView.findViewById(R.id.image_genre);
            mConstraintLayout.setOnClickListener(this);
            mGenreClickListener = genreClickListener;
        }

        private void bindData(Genre genre) {
            if (genre == null) return;
            mImageGenre.setImageResource(genre.getImageUrl());
            mTextGenreName.setText(genre.getName());
            mGenre = genre;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.constraint_genre:
                    mGenreClickListener.onItemClickGenre(mGenre);
                    break;
            }
        }
    }

    public interface GenreClickListener {
        void onItemClickGenre(Genre genre);
    }
}
