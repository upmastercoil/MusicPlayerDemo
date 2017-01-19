package tech.android.tcmp13.musicplayerdemo;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;

import java.util.List;

import tech.android.tcmp13.musicplayerdemo.music_player.MusicPlayer;
import tech.android.tcmp13.musicplayerdemo.music_player.MusicPlayerService;
import tech.android.tcmp13.musicplayerdemo.songs.Song;
import tech.android.tcmp13.musicplayerdemo.songs.SongsAdapter;

public class MainActivity extends AppCompatActivity implements SongClickedListener, MediaPlayer.OnPreparedListener {

    //Music member variables
    private MusicPlayerService musicPlayerService;
    private boolean serviceBound;
    private MusicServiceConnection musicServiceConnection;

    //Ui Member Variables
    private ProgressDialog progressDialog;
    private View controlPanel;
    private ImageButton playButton;
    private AnimatedVectorDrawable playToPause;
    private AnimatedVectorDrawable pauseToPlay;
    private RecyclerView songsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUi();
        setupSongsRecyclerView();
        setupMusicPlayerService();
        setupPlayButton();
    }

    private void setupUi() {

        controlPanel = findViewById(R.id.musicControlPanel);
        playButton = (ImageButton) findViewById(R.id.playButton);
        playToPause = (AnimatedVectorDrawable) getDrawable(R.drawable.animated_play_to_pause);
        pauseToPlay = (AnimatedVectorDrawable) getDrawable(R.drawable.animated_pause_to_play);
    }

    private void setupMusicPlayerService() {

        serviceBound = false;
        musicServiceConnection = new MusicServiceConnection();
        Intent musicPlayerIntent = new Intent(this, MusicPlayerService.class);
        //Now the service is started - it is independent of the activity's existence
        startService(musicPlayerIntent);
        //Now the service is connected (using the binder and the connection listener) to the client
        bindService(musicPlayerIntent, musicServiceConnection, BIND_AUTO_CREATE);
    }

    private void setupPlayButton() {

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!serviceBound)
                    return;
                MusicPlayer musicPlayer = musicPlayerService.getMusicPlayer();
                AnimatedVectorDrawable drawable = musicPlayer.isPlaying() ? pauseToPlay : playToPause;
                playButton.setImageDrawable(drawable);
                drawable.start();
                if (musicPlayer.isPlaying())
                    musicPlayer.pause();
                else
                    musicPlayer.play();
            }
        });
    }

    private void setupSongsRecyclerView() {

        songsRecyclerView = (RecyclerView) findViewById(R.id.songsRecyclerView);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onSongClicked(int position) {

        MusicPlayer musicPlayer = musicPlayerService.getMusicPlayer();
        progressDialog = ProgressDialog.show(this, getString(R.string.fetching_song), musicPlayer.getSongDescription(position));
        if (!musicPlayer.isPlaying()) {
            playButton.setImageDrawable(playToPause);
            playToPause.start();
        }
        musicPlayer.playSongAt(position, this);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        revealControlPanel();
        //MediaPlayer is now prepared, we can start playing
        mediaPlayer.start();
        progressDialog.dismiss();
    }

    private void revealControlPanel() {

        if (controlPanel.getVisibility() != View.INVISIBLE)
            return;

        //Set the animation's init state
        controlPanel.setTranslationY(controlPanel.getHeight() + 24);
        controlPanel.setVisibility(View.VISIBLE);

        //Base class for animator - the most customizable but we have to use callbacks (without on updated there won't be an animation)
//        ValueAnimator
        ObjectAnimator revealAnimator = ObjectAnimator.ofFloat(controlPanel,//The object (not just view) that will be affected by the animator
                "translationY", //The property that is affected by the changed values
                controlPanel.getHeight() + 24, //The starting point, relative to the origin
                0);//The ending point, relative to the origin
        revealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        revealAnimator.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
        revealAnimator.start();

        //If we want a coordinated animation use animator set
//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator backgroundColor = ObjectAnimator.ofArgb(controlPanel, "backgroundColor", getColor(android.R.color.black, getColor(android.R.color.black));
//        animatorSet.play(revealAnimator).before(backgroundColor);
//        animatorSet.start();
    }

    private class MusicServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            musicPlayerService = ((MusicPlayerService.MusicBinder) iBinder).getService();
            serviceBound = true;
            List<Song> songs = musicPlayerService.getMusicPlayer().getSongs();
            songsRecyclerView.setAdapter(new SongsAdapter(MainActivity.this, songs));
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            serviceBound = false;
        }
    }
}
