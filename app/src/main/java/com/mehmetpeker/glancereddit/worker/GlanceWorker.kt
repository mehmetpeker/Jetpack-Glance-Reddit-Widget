package com.mehmetpeker.glancereddit.worker

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mehmetpeker.glancereddit.data.RedditItemModel
import com.mehmetpeker.glancereddit.ui.widget.RedditWidget
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class GlanceWorker(private val appContext: Context, private val params: WorkerParameters) :
    CoroutineWorker(
        appContext,
        params
    ) {
    override suspend fun doWork(): Result {

        val response = getHttpResponse()
        val list = getListFromJson(response)
        updateGlanceWidget(list)
        return Result.success()
    }

    private suspend fun getHttpResponse(): String {
        val httpClient = OkHttpClient()
        val url = "https://www.reddit.com/r/androiddev/top.json?limit=10"
        val request = Request.Builder()
            .url(url)
            .build()
        val response = suspendCoroutine<String> { continuation ->
            httpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful)
                            return
                        continuation.resume(response.body!!.string())
                    }
                }
            })
        }

        return response

    }

    private fun getListFromJson(response: String): MutableList<RedditItemModel> {

        val list = mutableListOf<RedditItemModel>()
        val data = JSONObject(response).getJSONObject("data").getJSONArray("children")
        for (i in 0 until data.length()) {
            val postData = data.getJSONObject(i).getJSONObject("data")

            val item = RedditItemModel(
                postData.getString("title"),
                postData.getString("selftext"),
                postData.getString("url")
            )
            list.add(item)
        }

        return list

    }

    private suspend fun updateGlanceWidget(list: List<RedditItemModel>) {
        val glanceId =
            GlanceAppWidgetManager(appContext).getGlanceIds(RedditWidget::class.java)
                .firstOrNull()
        glanceId?.let { id ->
            RedditWidget(list).update(appContext,id)
        }
    }
}