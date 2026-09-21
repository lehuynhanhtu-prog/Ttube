package vn.ttube.audio

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata

object Catalog {
    const val ROOT_ID = "root"
    const val PREFS = "ttube_audio"
    const val KEY_TITLE = "custom_title"
    const val KEY_URL = "custom_url"
    const val ACTION_RELOAD = "vn.ttube.audio.RELOAD"

    fun root(): MediaItem = MediaItem.Builder().setMediaId(ROOT_ID)
        .setMediaMetadata(MediaMetadata.Builder().setTitle("TTube Audio").setIsBrowsable(true).build()).build()

    fun items(context: Context): List<MediaItem> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val title = prefs.getString(KEY_TITLE, null)
        val url = prefs.getString(KEY_URL, null)
        if (title.isNullOrBlank() || url.isNullOrBlank()) return emptyList()
        return listOf(playable("custom", title, url))
    }

    fun playable(id: String, title: String, url: String): MediaItem = MediaItem.Builder()
        .setMediaId(id).setUri(Uri.parse(url))
        .setMediaMetadata(MediaMetadata.Builder().setTitle(title).setIsPlayable(true).build()).build()
}
