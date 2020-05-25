package com.shanghq.zeromusic.service;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.media.MediaBrowserServiceCompat;
import androidx.media.session.MediaButtonReceiver;

import com.shanghq.zeromusic.Helper.MediaPlayerHelper;
import com.shanghq.zeromusic.MainActivity;
import com.shanghq.zeromusic.PlayActivity;
import com.shanghq.zeromusic.R;
import com.shanghq.zeromusic.Utils.API;
import com.shanghq.zeromusic.Utils.MusicUtils;
import com.shanghq.zeromusic.bean.SongBean;

import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_MEDIA_NEXT;
import static android.view.KeyEvent.KEYCODE_MEDIA_PREVIOUS;

public class MusicService extends MediaBrowserServiceCompat {

    private MediaSessionCompat mediaSession;

    //自定义的 动作
    //为简单起见  所有操作 都将采用 自定义
    public static final String actionAdd = "add";
    public static final String actionClear = "clear";

    private PlaybackStateCompat.Builder stateBuilder;


    private MediaPlayerHelper mediaPlayerHelper;
    private PlaybackStateCompat playbackState;

    private List<MediaSessionCompat.QueueItem> queueItemList;
    private int position=0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        MediaButtonReceiver.handleIntent(mediaSession, intent);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();


        mediaSession = new MediaSessionCompat(this, "MusicService");
        mediaSession.setCallback(sessionCallback);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        playbackState = stateBuilder.build();

        mediaSession.setPlaybackState(playbackState);
        mediaSession.setActive(true);
        setSessionToken(mediaSession.getSessionToken());

//        mediaSession.setMediaButtonReceiver(mediab);

        mediaPlayerHelper = MediaPlayerHelper.getInstance(this);

        mediaPlayerHelper.setMediaPlayerHelperListener(new MediaPlayerHelper.onMediaPlayerHelperListener() {
            @Override
            public void onMediaPlay(MediaPlayer mp) {

//                Log.d("music service", "media onPrepare");
                initNotificationView();

                mediaPlayerHelper.start();
                playbackState = stateBuilder.
                        setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f).
                        build();

                mediaSession.setPlaybackState(playbackState);


            }
        });


        queueItemList = new ArrayList<>();
        position = 0;
        mediaSession.setQueue(queueItemList);

    }


    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        // (Optional) Control the level of access for the specified package name.
        // You'll need to write your own logic to do this.
        if (clientPackageName.equals("PlayActivity")) {
            // Returns a root ID that clients can use with onLoadChildren() to retrieve
            // the content hierarchy.
            return new BrowserRoot("PlayMediaId", null);
        } else {
            // Clients can connect, but this BrowserRoot is an empty hierachy
            // so onLoadChildren returns nothing. This disables the ability to browse for content.
            return new BrowserRoot("MainMediaId", null);
        }
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {


        List<MediaBrowserCompat.MediaItem> mediaItemList = new ArrayList<>();
        MediaMetadataCompat metadata = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "晴天")
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "周杰伦")
                .build();
        mediaItemList.add(new MediaBrowserCompat.MediaItem(metadata.getDescription(),
                MediaBrowserCompat.MediaItem.FLAG_PLAYABLE));

        result.sendResult(mediaItemList);
    }


    private MediaSessionCompat.Callback sessionCallback = new MediaSessionCompat.Callback() {


        @Override
        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {

//            Log.d("test",mediaButtonEvent.getExtras().toString());
            KeyEvent keyEvent = (KeyEvent) mediaButtonEvent.getExtras().get(Intent.EXTRA_KEY_EVENT);
            switch (keyEvent.getKeyCode()) {
                case KEYCODE_MEDIA_NEXT:

                    onSkipToNext();

                    break;
                case KEYCODE_MEDIA_PREVIOUS:

                    onSkipToPrevious();

                    break;
            }

            return super.onMediaButtonEvent(mediaButtonEvent);
        }

        @Override
        public void onPrepare() {

            MediaSessionCompat.QueueItem item = null;
            if (queueItemList.size() != 0) {
                item = queueItemList.get(position);

            } else {
                return;
            }

            mediaSession.setMetadata(MusicUtils.convertToMetaData(item));

            mediaPlayerHelper.setPath(item.getDescription().getMediaUri());


        }


        @Override
        public void onCustomAction(String action, Bundle extras) {
            switch (action) {
                case actionAdd:


                    SongBean song = new SongBean();
                    song.setId(extras.getLong("id"));
                    song.setTitle(extras.getString("title"));
                    song.setAlbum(extras.getString("album"));
                    song.setAlbumId(extras.getLong("albumId"));
                    song.setDuration(extras.getInt("duration"));
                    song.setSinger(extras.getString("singer"));
                    song.setPath(extras.getString("path"));


                    MediaMetadataCompat metadata = MusicUtils.convertToMetaData(song);

                    queueItemList.add(new MediaSessionCompat.QueueItem(metadata.getDescription(), 1));

                    mediaSession.setQueue(queueItemList);


                    break;


                case actionClear:
//                    queueItemList.clear();
//                    mediaSession.setQueue(queueItemList);


                    break;
            }
        }


        @Override
        public void onPlay() {


            Log.d("test Service Session", "onPlay()");

            if (mediaPlayerHelper.getPath() == null) {

                onPrepare();

                return;
            }

            initNotificationView();

            mediaPlayerHelper.start();
            playbackState = stateBuilder.
                    setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f).
                    build();

            mediaSession.setPlaybackState(playbackState);

        }


        @Override
        public void onPause() {
            Log.d("test Service Session", "onPause()");

            mediaPlayerHelper.pause();

            playbackState = stateBuilder.
                    setState(PlaybackStateCompat.STATE_PAUSED, 0, 1.0f).
                    build();

            mediaSession.setPlaybackState(playbackState);
        }


        @Override
        public void onSkipToNext() {
            Log.d("test Service Session", "onSkipToNext()");

            if (queueItemList.size() == 0 || queueItemList.size() <= position + 1) {
                return;
            }

            playbackState = stateBuilder.
                    setState(PlaybackStateCompat.STATE_SKIPPING_TO_NEXT, 0, 1.0f).
                    build();

            mediaSession.setPlaybackState(playbackState);


            position++;
            onPrepare();


        }

        @Override
        public void onSkipToPrevious() {
            Log.d("test Service Session", "onSkipToPrevious()");

            if (queueItemList.size() == 0 || position == 0) {
                return;
            }

            playbackState = stateBuilder.
                    setState(PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS, 0, 1.0f).
                    build();

            mediaSession.setPlaybackState(playbackState);

            position--;
            onPrepare();

        }

        @Override
        public void onStop() {
            Log.d("test Service Session", "onStop()");


            playbackState = stateBuilder.
                    setState(PlaybackStateCompat.STATE_STOPPED, 0, 1.0f).
                    build();

            mediaSession.setPlaybackState(playbackState);
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("ServiceMusic", "onDestroy()");

        mediaPlayerHelper.release();

        stopForeground(true);

    }

    //在播放器开始播放时构建并显示通知
    private void initNotificationView() {

        MediaControllerCompat controller = mediaSession.getController();
        MediaMetadataCompat mediaMetadata = controller.getMetadata();
        MediaDescriptionCompat description = mediaMetadata.getDescription();

        Context context = getApplicationContext();
        String channelI = getString(R.string.channel_id);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelI);

        builder
                // Add the metadata for the currently playing track
                .setContentTitle(description.getTitle())
                .setContentText(description.getSubtitle())
                .setSubText(description.getDescription())
                .setLargeIcon(description.getIconBitmap())

                // Enable launching the player by clicking the notification
                .setContentIntent(controller.getSessionActivity())

                // Stop the service when the notification is swiped away
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                        PlaybackStateCompat.ACTION_STOP))

                // Make the transport controls visible on the lockscreen
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

                // Add an app icon and set its accent color
                // Be careful about the color
                .setSmallIcon(R.drawable.ic_music_note_red_500_24dp)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))

                // Add a pause button
                .addAction(new NotificationCompat.Action(
                        R.drawable.ic_skip_previous_black_24dp, getString(R.string.pause),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)))
                // Add a pause button
                .addAction(new NotificationCompat.Action(
                        R.drawable.ic_pause_black_24dp, getString(R.string.pause),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                                PlaybackStateCompat.ACTION_PLAY_PAUSE)))
                // Add a pause button
                .addAction(new NotificationCompat.Action(
                        R.drawable.ic_skip_next_black_24dp, getString(R.string.pause),
                        MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT)))

                // Take advantage of MediaStyle features
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.getSessionToken())
                        .setShowActionsInCompactView(1)

                        // Add a cancel button
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                                PlaybackStateCompat.ACTION_STOP)));

        // Display the notification and place the service in the foreground
        startForeground(110, builder.build());
    }
}
