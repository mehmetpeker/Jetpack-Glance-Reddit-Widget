package com.mehmetpeker.glancereddit.util

import androidx.datastore.core.Serializer
import com.mehmetpeker.glancereddit.data.RedditPreferences
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object RedditPreferencesSerializer : Serializer<RedditPreferences> {
    override val defaultValue: RedditPreferences
        get() = RedditPreferences()

    override suspend fun readFrom(input: InputStream): RedditPreferences {
        return try {
            Json.decodeFromString(
                deserializer = RedditPreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: RedditPreferences, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = RedditPreferences.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}