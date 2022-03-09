package com.mehmetpeker.glancereddit

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.mehmetpeker.glancereddit.ui.widget.RedditWidget


class RedditWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = RedditWidget(emptyList())
}