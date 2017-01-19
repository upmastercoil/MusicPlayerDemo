package tech.android.tcmp13.musicplayerdemo.songs;

/**
 * Created by noynngrisaru on 17/01/2017.
 */

public class Song {

	//Source can be any uri/url to make it easy to fetch songs from different
	// locations (one source is loaded at a time to the mediaPlayer)
	private String source;
	private String title;
	private String artist;

	public Song(String source, String title, String artist) {

		this.source = source;
		this.title = title;
		this.artist = artist;
	}

	public String getSource() {

		return source;
	}

	public void setSource(String source) {

		this.source = source;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getArtist() {

		return artist;
	}

	public void setArtist(String artist) {

		this.artist = artist;
	}

	@Override
	public String toString() {

		return title +  " by: " + artist;
	}
}
