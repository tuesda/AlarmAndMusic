package com.example.root.musicNav;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zhanglei on 15-3-29.
 * Email: zhangleicoding@163.com
 */
public class MusicService extends Service implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer player;
    private int songPson;
    private ArrayList<Song> songList;
    private final IBinder musicBinder = new MusicBinder();





    public class MusicBinder extends Binder {
        public MusicService getMusicService() {
            return MusicService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    public void setSongList(ArrayList<Song> songs) {
        songList = songs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        songPson = 0;
        player = new MediaPlayer();
        initMusicPlayer();


    }

    private void initMusicPlayer() {
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnErrorListener(this);
        player.setOnCompletionListener(this);
    }

    public void playMusic() {
        player.reset();
        Song songPlaying = songList.get(songPson);
        long curSong = songPlaying.getId();
        Uri songUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, curSong);

        try {
            player.setDataSource(getApplicationContext(), songUri);
        } catch (IOException e) {
            Log.e("SLEEP MUSIC", "Error setting data source", e);
        }
        player.prepareAsync();

    }

    public void stopMusic() {
        player.stop();
    }

    public void nextMusic() {
        songPson++;
        if (songPson >= songList.size()) songPson = 0;
        playMusic();
    }

    public void lastMusic() {
        songPson--;
        if (songPson < 0) songPson = songList.size() - 1;
        playMusic();
    }

    public int getCurSong() {
        return songPson;
    }




    // following is with MediaPlayer


    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.reset();
        nextMusic();

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}

