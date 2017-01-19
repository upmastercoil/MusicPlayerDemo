package tech.android.tcmp13.musicplayerdemo.songs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tech.android.tcmp13.musicplayerdemo.R;

/**
 * Created by noynngrisaru on 17/01/2017.
 */
public class SongViewHolder extends RecyclerView.ViewHolder {

	public TextView songTitle;
	public TextView songArtist;

	public SongViewHolder(View itemView) {

		super(itemView);
		songTitle = (TextView) itemView.findViewById(R.id.songItemTitle);
		songArtist = (TextView) itemView.findViewById(R.id.songItemArtist);
	}
}
