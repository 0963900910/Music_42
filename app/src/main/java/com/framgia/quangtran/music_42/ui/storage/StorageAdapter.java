package com.framgia.quangtran.music_42.ui.storage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.quangtran.music_42.R;
import com.framgia.quangtran.music_42.data.model.Track;

import java.util.List;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolder> {
    private List<Track> mTracks;
    private LayoutInflater mInflater;
    private StorageClickListener mListener;

    public StorageAdapter(List<Track> tracks, StorageClickListener clickListener) {
        this.mListener = clickListener;
        this.mTracks = tracks;
    }

    @NonNull
    @Override
    public StorageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View contactView = mInflater.inflate(R.layout.item_recycler_music_personal, viewGroup,
                false);
        return new StorageAdapter.ViewHolder(contactView, mTracks, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bindData(mTracks.get(i), i);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextTrackName;
        private TextView mTextSingerName;
        private View mViewTrack;
        private ImageView mImageFavorite;
        private ImageView mImageDelete;
        private StorageClickListener mListener;
        private List<Track> mTracks;

        public ViewHolder(@NonNull View itemView, List<Track> tracks, StorageClickListener storageClickListener) {
            super(itemView);
            mListener = storageClickListener;
            mViewTrack = itemView.findViewById(R.id.view_track);
            mTextTrackName = itemView.findViewById(R.id.text_name_track);
            mTextSingerName = itemView.findViewById(R.id.text_name_singer);
            mImageFavorite = itemView.findViewById(R.id.image_heart);
            mImageDelete = itemView.findViewById(R.id.image_delete);
            mImageFavorite.setOnClickListener(this);
            mImageDelete.setOnClickListener(this);
            mViewTrack.setOnClickListener(this);
            mTracks = tracks;
        }

        public void bindData(final Track track, final int i) {
            mViewTrack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClickPlayMusic(mTracks, i);
                }
            });
            mTextTrackName.setText(track.getTitle());
            mTextSingerName.setText(track.getUserName());
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.view_track:
                    break;
                default:
                    break;
            }
        }

    }

    public interface StorageClickListener {
        void onClickPlayMusic(List<Track> tracks, int i);
    }
}
