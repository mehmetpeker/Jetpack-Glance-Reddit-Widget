package com.mehmetpeker.glancereddit.ui.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.mehmetpeker.glancereddit.worker.GlanceWorker

private const val GLANCE_WORK_NAME = "glance_work_name"

class UpdateListAction : ActionCallback {

    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val workTag = "glanceWork"
        val glanceWork = OneTimeWorkRequest.Builder(GlanceWorker::class.java)
            .addTag(workTag).build()

        val instanceWorkManager = WorkManager.getInstance(context)
        instanceWorkManager.beginUniqueWork(
            GLANCE_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            glanceWork
        ).enqueue()
    }
}