package tech.android.tcmp13.musicplayerdemo.music_player;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tech.android.tcmp13.musicplayerdemo.MainActivity;
import tech.android.tcmp13.musicplayerdemo.songs.Song;

/**
 * Created by tcmp13-t on 1/18/2017.
 */
public class MusicPlayer {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private List<Song> songs;
    private MediaPlayer mediaPlayer;

    public MusicPlayer() {

        songs = generateDefaultSongs();
    }

    private List<Song> generateDefaultSongs() {

        List<Song> result = new ArrayList<>(4);
        result.add(new Song("http://www.stephaniequinn.com/Music/Allegro%20from%20Duet%20in%20C%20Major.mp3", "Allegro", "Beethoven"));
        result.add(new Song("http://www.stephaniequinn.com/Music/Commercial%20DEMO%20-%2005.mp3", "Cheek to Cheek", "Irving Berlin"));
        result.add(new Song("http://www.stephaniequinn.com/Music/Commercial%20DEMO%20-%2017.mp3", "Ele Chamda Libi", "Jewish Traditional"));
        result.add(new Song("http://www.stephaniequinn.com/Music/Commercial%20DEMO%20-%2013.mp3", "Great Balls of Fire", "Blackwell & Hammer"));
        return result;
    }

    /**
     * Get an immutable list of the songs
     *
     * @return
     */
    public List<Song> getSongs() {

        return new ArrayList<>(songs);
    }

    public String getSongDescription(int position) {

        return songs.get(position).toString();
    }

    public Song getSong(int position) {

        return songs.get(position);
    }

    public boolean isPlaying() {

        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public void play() {

        mediaPlayer.start();
    }

    public void pause() {

        mediaPlayer.pause();
    }

    public void playSongAt(int position, MediaPlayer.OnPreparedListener onPreparedListener) {

        clearMedia();

        Song song = songs.get(position);
        //Remember that the create factory method moves straight to the prepared state
//        this.mediaPlayer = MediaPlayer.create(this, R.raw.kalimba);
//        mediaPlayer.start();

        //Media player moves to Idle
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(onPreparedListener);
        //Media player moves to Initialized
        try {
            mediaPlayer.setDataSource(song.getSource());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            return;
        }
        //Media player moves to Prepared
        mediaPlayer.prepareAsync();
    }

    public void clearMedia() {

        if (mediaPlayer == null)
            return;
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
