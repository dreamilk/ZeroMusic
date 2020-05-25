package com.shanghq.zeromusic.Helper;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import java.io.IOException;

public class MediaPlayerHelper {

    private static MediaPlayerHelper instance;

    private Context context;
    private MediaPlayer mediaPlayer;

    private String path;

    private onMediaPlayerHelperListener mediaPlayerHelperListener;

    public void setMediaPlayerHelperListener(onMediaPlayerHelperListener mediaPlayerHelperListener) {
        this.mediaPlayerHelperListener = mediaPlayerHelperListener;
    }

    private MediaPlayerHelper(Context context) {
        this.context = context;
        mediaPlayer = new MediaPlayer();
    }

    public static MediaPlayerHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (MediaPlayerHelper.class) {
                if (instance == null) {
                    return new MediaPlayerHelper(context);
                }
            }
        }
        return instance;
    }

    public String getPath() {
        return path;
    }

    public void setPath(Uri url) {
        this.path = url.getPath();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setDataSource(context, url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.prepareAsync();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (mediaPlayerHelperListener != null) {
                    mediaPlayerHelperListener.onMediaPlay(mp);
                }

            }
        });
    }

    public void setPath(String path) {
        this.path = path;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setDataSource(context, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.prepareAsync();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (mediaPlayerHelperListener != null) {
                    mediaPlayerHelperListener.onMediaPlay(mp);
                }

            }
        });
    }


    public void start() {
        if (mediaPlayer.isPlaying()) return;
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void release() {
        mediaPlayer.release();
        mediaPlayer = null;
    }


    public interface onMediaPlayerHelperListener {
        void onMediaPlay(MediaPlayer mp);
    }


}
