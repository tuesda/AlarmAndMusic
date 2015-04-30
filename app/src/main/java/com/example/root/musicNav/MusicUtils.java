package com.example.root.musicNav;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by zhanglei on 15-4-29.
 * Email: zhangleicoding@163.com
 */
public class MusicUtils {
    private Context context;
    private ArrayList<Song> songList = new ArrayList<Song>();
    public MusicUtils(Context context) {
        this.context = context;
    }

    public boolean getSongsFromDevice() {
        boolean result = true;
        ContentResolver musicResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor!=null && musicCursor.moveToFirst()) {

            // get colums
            int titleColum = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColum = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColum = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                String thisTitle = musicCursor.getString(titleColum);
                String thisArtist = musicCursor.getString(artistColum);
                long thisID = musicCursor.getLong(idColum);
                songList.add(new Song(thisID, thisTitle, thisArtist));
            } while (musicCursor.moveToNext());
        } else {
            result = false;
        }
        return result;
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }
}
