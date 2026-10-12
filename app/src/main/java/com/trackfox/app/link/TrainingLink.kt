package com.trackfox.app.link

import android.net.Uri

object TrainingLink {
    fun create(id : Long) : Uri =
        Uri.Builder()
            .scheme("trackfox")
            .authority("training")
            .appendPath(id.toString())
            .build()

    fun parse(uri: Uri?) : Long? =
        if (uri == null
            || uri.scheme != "trackfox"
            || uri.authority != "training"
            || uri.pathSegments.size != 1
        ) null else uri.pathSegments.first().toLongOrNull()
}