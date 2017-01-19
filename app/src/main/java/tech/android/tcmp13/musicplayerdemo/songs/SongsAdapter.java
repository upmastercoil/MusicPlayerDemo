package tech.android.tcmp13.musicplayerdemo.songs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tech.android.tcmp13.musicplayerdemo.R;
import tech.android.tcmp13.musicplayerdemo.SongClickedListener;

/**
 * Created by tcmp13-t on 1/18/2017.
 */
public class SongsAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private SongClickedListener songClickedListener;
    private List<Song> songs;

    public SongsAdapter(SongClickedListener songClickedListener, List<Song> songs) {

        this.songClickedListener = songClickedListener;
        this.songs = songs;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SongViewHolder holder, int position) {

        Song song = songs.get(position);
        holder.songTitle.setText(song.getTitle());
        holder.songArtist.setText(song.getArtist());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getAdapterPosition returns the position in the data set (or at least it should...)
                //getLayoutPosition returns the position in the ui set (Layout Manager)
                songClickedListener.onSongClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
