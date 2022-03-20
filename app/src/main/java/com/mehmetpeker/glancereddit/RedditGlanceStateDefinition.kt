package com.mehmetpeker.glancereddit

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import com.mehmetpeker.glancereddit.data.RedditPreferences
import com.mehmetpeker.glancereddit.util.RedditPreferencesSerializer
import java.io.File

const val redditFileName = "app-settings.json"
val Context.redditDataStore by dataStore(redditFileName, RedditPreferencesSerializer)


object RedditGlanceStateDefinition : GlanceStateDefinition<RedditPreferences> {

    override suspend fun getDataStore(
        context: Context,
        fileKey: String
    ): DataStore<RedditPreferences> = context.redditDataStore

    override fun getLocation(context: Context, fileKey: String): File =
        context.dataStoreFile(redditFileName)
}
