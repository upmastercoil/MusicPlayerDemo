package tech.android.tcmp13.musicplayerdemo.music_player;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import tech.android.tcmp13.musicplayerdemo.songs.Song;

/**
 * Created by tcmp13-t on 1/18/2017.
 */
public class MusicPlayerService extends Service {

    private MusicBinder musicBinder;
    private MusicPlayer musicPlayer;

    @Override
    public void onCreate() {

        super.onCreate();
        musicPlayer = new MusicPlayer();
        musicBinder = new MusicBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return musicBinder;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        musicPlayer.clearMedia();
    }

    public class MusicBinder extends Binder {

        public MusicPlayerService getService() {

            return MusicPlayerService.this;
        }
    }

    public MusicPlayer getMusicPlayer() {

        return musicPlayer;
    }
}
