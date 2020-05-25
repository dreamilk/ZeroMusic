package com.shanghq.zeromusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ComponentName;
import android.drm.DrmStore;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.jaeger.library.StatusBarUtil;
import com.shanghq.zeromusic.service.MusicService;

public class PlayActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ImageButton btRepeat;
    private ImageButton btPre;
    private ImageButton btPlay;
    private ImageButton btNext;
    private ImageButton btQueue;

    private SeekBar seekBar;


    private MediaBrowserCompat mediaBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        toolbar = findViewById(R.id.toolbar_play);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) toolbar.getLayoutParams();
        params.topMargin = getStatusBarHeight();
        toolbar.setLayoutParams(params);


        initViews();
        initMediaEvent();


    }

    private void initViews() {
        btNext = findViewById(R.id.bt_play_next);
        btPlay = findViewById(R.id.bt_play_play);
        btPre = findViewById(R.id.bt_play_pre);
        btNext = findViewById(R.id.bt_play_next);
        btQueue = findViewById(R.id.bt_play_queue);

        seekBar = findViewById(R.id.seekBar_play);


    }

    private void initMediaEvent() {

        mediaBrowser = new MediaBrowserCompat(this,
                new ComponentName(this, MusicService.class), connectionCallback, null);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaBrowser.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (MediaControllerCompat.getMediaController(PlayActivity.this) != null) {
            MediaControllerCompat.getMediaController(PlayActivity.this).unregisterCallback(controllerCallback);
        }

        Log.d("PlayActivity", "onStop()");

        mediaBrowser.disconnect();
    }

    // 获取statusBar的高度
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelOffset(resourceId);
        }

        return result;
    }


    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    //    /**
    //     * 连接状态的回调接口，连接成功时会调用onConnected()方法
    //     */
    private MediaBrowserCompat.ConnectionCallback connectionCallback = new MediaBrowserCompat.ConnectionCallback() {
        @Override
        public void onConnected() {

            if (mediaBrowser.isConnected()) {
                // Get the token for the MediaSession
                MediaSessionCompat.Token token = mediaBrowser.getSessionToken();

                // Create a MediaControllerCompat
                MediaControllerCompat mediaController = null;
                try {
                    mediaController = new MediaControllerCompat(PlayActivity.this, // Context
                            token);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                // Save the controller
                MediaControllerCompat.setMediaController(PlayActivity.this, mediaController);

            }

            buildTransportControls();
        }

        @Override
        public void onConnectionFailed() {
            super.onConnectionFailed();
        }
    };

    private void buildTransportControls() {

        //根据已经播放的 设置ui
        int pbState = MediaControllerCompat.getMediaController(PlayActivity.this).getPlaybackState().getState();
        switch (pbState) {
            case PlaybackStateCompat.STATE_PAUSED:

                btPlay.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_blue_700_36dp));
                break;
            case PlaybackStateCompat.STATE_PLAYING:

                btPlay.setImageDrawable(getDrawable(R.drawable.ic_pause_blue_700_36dp));
                break;
            case PlaybackStateCompat.STATE_SKIPPING_TO_NEXT:

                break;
            case PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS:

                break;

        }

        btPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControllerCompat.getMediaController(PlayActivity.this).getTransportControls().skipToPrevious();
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControllerCompat.getMediaController(PlayActivity.this).getTransportControls().skipToNext();
            }
        });


        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pbState = MediaControllerCompat.getMediaController(PlayActivity.this).getPlaybackState().getState();
                if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                    MediaControllerCompat.getMediaController(PlayActivity.this).getTransportControls().pause();
                } else {
                    MediaControllerCompat.getMediaController(PlayActivity.this).getTransportControls().play();
                }
            }
        });

        btPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControllerCompat.getMediaController(PlayActivity.this).getTransportControls().skipToPrevious();
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControllerCompat.getMediaController(PlayActivity.this).getTransportControls().skipToNext();
            }
        });


        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(PlayActivity.this);
        MediaMetadataCompat metadata = mediaController.getMetadata();
        PlaybackStateCompat playbackState = mediaController.getPlaybackState();


//        long currentPosition = (long) (((SystemClock.elapsedRealtime() - playbackState.getLastPositionUpdateTime()) * playbackState.getPlaybackSpeed()) + playbackState.getPosition());
//        seekBar.setProgress(100);

        mediaController.registerCallback(controllerCallback);


    }

    private MediaControllerCompat.Callback controllerCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PAUSED:
                    Log.d("playActivity", "play to pause");
                    btPlay.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_blue_700_36dp));
                    break;
                case PlaybackStateCompat.STATE_PLAYING:
                    Log.d("playActivity", "play to play");
                    btPlay.setImageDrawable(getDrawable(R.drawable.ic_pause_blue_700_36dp));
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_NEXT:
                    Log.d("playActivity", "play to next");
                    break;
                case PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS:
                    Log.d("playActivity", "play to pre");
                    break;

            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
        }
    };


}
