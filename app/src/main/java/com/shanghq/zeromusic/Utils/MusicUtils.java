package com.shanghq.zeromusic.Utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import com.shanghq.zeromusic.R;
import com.shanghq.zeromusic.bean.SongBean;

import java.util.ArrayList;
import java.util.List;

public class MusicUtils {

    /**
     * 扫描歌曲
     */
    public static List<SongBean> scanMusic(Context context) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return null;
        }

        List<SongBean> songBeanList = new ArrayList<>();

        while (cursor.moveToNext()) {
            // 是否为音乐
            int isMusic = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_MUSIC));
            if (isMusic == 0) {
                continue;
            }
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            // 标题
            String title = cursor.getString((cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)));
            // 艺术家
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST));
            // 专辑
            String album = cursor.getString((cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM)));
            // 专辑封面id，根据该id可以获得专辑封面图片
            long albumId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID));
            // 持续时间
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            // 音乐文件路径
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA));
            // 音乐文件名
            String fileName = cursor.getString((cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)));
            // 音乐文件大小
            long fileSize = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));


            SongBean songBean = new SongBean();
            songBean.setId(id);
            songBean.setTitle(title);
            songBean.setSinger(artist);
            songBean.setAlbum(album);
            songBean.setAlbumId(albumId);
            songBean.setDuration(duration);
            songBean.setPath(path);
            songBean.setFileName(fileName);
            songBean.setFileSize(fileSize);
            songBeanList.add(songBean);

        }
        cursor.close();

        return songBeanList;
    }

    /**
     * 从媒体库加载封面
     */
    public static Bitmap getAlbumArt(Context context, long album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(Uri.parse(mUriAlbums + "/" + Long.toString(album_id)), projection, null, null, null);
        String album_art = "";
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        Log.d("test456", "" + album_art);
        Bitmap bm = null;
        if (album_art != null) {
            bm = BitmapFactory.decodeFile(album_art);
        } else {
//            bm = BitmapFactory.decodeResource(r);
        }
        return bm;
    }

    //    转换歌曲时间的格式
    public static String formatTime(int time) {
        String tt = "";
        if (time % 60 < 10) {
            if (time / 60 < 10) {
                tt = "0" + time / 60 + ":0" + time % 60;
            } else {
                tt = time / 60 + ":0" + time % 60;
            }
            return tt;
        } else {
            if (time / 60 < 10) {
                tt = "0" + time / 60 + ":" + time % 60;
            } else {
                tt = time / 60 + ":" + time % 60;
            }
            return tt;
        }
    }


    public static MediaMetadataCompat convertToMetaData(SongBean song) {
        MediaMetadataCompat metadata = new MediaMetadataCompat.Builder()
                .putString(MediaMetadata.METADATA_KEY_MEDIA_ID, song.getId()+"")
                .putString(MediaMetadata.METADATA_KEY_TITLE, song.getTitle())//标题
                .putString(MediaMetadata.METADATA_KEY_ARTIST, song.getSinger())//作者
                .putString(MediaMetadata.METADATA_KEY_ALBUM, song.getAlbum())//唱片
                .putLong(MediaMetadata.METADATA_KEY_DURATION, song.getDuration())//媒体时长
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, song.getPath())
                .build();
        return metadata;

    }

    public static MediaMetadataCompat convertToMetaData(MediaSessionCompat.QueueItem queueItem) {
        MediaDescriptionCompat description = queueItem.getDescription();
        MediaMetadataCompat metadata = new MediaMetadataCompat.Builder()
                .putString(MediaMetadata.METADATA_KEY_MEDIA_ID, description.getMediaId())
                .putString(MediaMetadata.METADATA_KEY_TITLE, description.getTitle().toString())//标题
                .putString(MediaMetadata.METADATA_KEY_ARTIST, description.getSubtitle().toString())//作者
                .putString(MediaMetadata.METADATA_KEY_ALBUM, description.getDescription().toString())//唱片
                .putLong(MediaMetadata.METADATA_KEY_DURATION, 0)//媒体时长
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, description.getMediaUri().getPath())
                .build();
        return metadata;
    }





}
