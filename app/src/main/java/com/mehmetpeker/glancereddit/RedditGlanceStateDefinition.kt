package com.mehmetpeker.glancereddit

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import com.mehmetpeker.glancereddit.data.RedditItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.io.File

const val redditDataStoreKey = "redditDataStore"

fun Context.widgetDataStoreFile(): File =
    this.dataStoreFile(redditDataStoreKey)

suspend fun Context.getWidgetDataStore(): DataStore<RedditStateGlance> {
    val context = this
    val store = runBlocking {
        RedditGlanceStateDefinition.getDataStore(context, redditDataStoreKey)
    }
    return store
}

object RedditGlanceStateDefinition : GlanceStateDefinition<RedditStateGlance> {

    override suspend fun getDataStore(
        context: Context,
        fileKey: String
    ): DataStore<RedditStateGlance> = RedditDataStore()

    override fun getLocation(context: Context, fileKey: String): File =
        context.widgetDataStoreFile()
}

data class RedditStateGlance(
    val list: List<RedditItemModel>
) {
    companion object {
        val EMPTY = RedditStateGlance(
            emptyList()
        )
    }
}

class RedditDataStore : DataStore<RedditStateGlance> {
   private val dataFlow = MutableStateFlow(RedditStateGlance.EMPTY)

    override val data: Flow<RedditStateGlance> = flow {
        emit(dataFlow.value)
    }

    override suspend fun updateData(transform: suspend (t: RedditStateGlance) -> RedditStateGlance): RedditStateGlance  {

        // I want to update data flow update
        return dataFlow.value
    }
}