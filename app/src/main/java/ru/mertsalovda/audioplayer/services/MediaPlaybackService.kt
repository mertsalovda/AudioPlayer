package ru.mertsalovda.audioplayer.services

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import ru.mertsalovda.audioplayer.MainActivity

private const val MEDIA_ROOT_ID = "media_root_id"
private const val EMPTY_MEDIA_ROOT_ID = "empty_root_id"
private const val LOG_TAG = "MediaPlaybackService"

class MediaPlaybackService : MediaBrowserServiceCompat() {

    private var mediaSession: MediaSessionCompat? = null
    private var stateBuilder = PlaybackStateCompat.Builder()
        .setActions(
            PlaybackStateCompat.ACTION_PLAY
                    or PlaybackStateCompat.ACTION_STOP
                    or PlaybackStateCompat.ACTION_PAUSE
                    or PlaybackStateCompat.ACTION_PLAY_PAUSE
                    or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
        )
    private val mediaSessionCallback = object : MediaSessionCompat.Callback() {
        override fun onPlay() {
        }
        override fun onPause() {
        }
        override fun onStop() {
        }
        override fun onSkipToNext() {
        }
        override fun onSkipToPrevious() {
        }
    }

    override fun onCreate() {
        super.onCreate()
        val activityIntent = Intent(applicationContext, MainActivity::class.java);

        mediaSession = MediaSessionCompat(baseContext, LOG_TAG).apply {
            setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                        or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )
            setPlaybackState(stateBuilder.build())
            setCallback(mediaSessionCallback)
            setSessionToken(sessionToken)
            setSessionActivity(
                PendingIntent.getActivity(applicationContext, 0, activityIntent, 0))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession?.release()
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        if (EMPTY_MEDIA_ROOT_ID == parentId) {
            result.sendResult(null)
            return
        }
        val mediaItems = mutableListOf<MediaBrowserCompat.MediaItem>()

        if (MEDIA_ROOT_ID == parentId) {
        } else {
        }
        result.sendResult(mediaItems)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(MEDIA_ROOT_ID, null)
    }
}
