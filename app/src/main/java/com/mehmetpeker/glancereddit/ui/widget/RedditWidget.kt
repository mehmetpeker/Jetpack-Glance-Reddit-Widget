package com.mehmetpeker.glancereddit.ui.widget

import android.content.Context
import android.content.Intent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.layout.*
import androidx.glance.text.Text
import com.mehmetpeker.glancereddit.MainActivity
import com.mehmetpeker.glancereddit.data.RedditItemModel

class RedditWidget(private val list: List<RedditItemModel>) : GlanceAppWidget() {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(12.dp)
                .appWidgetBackground()
                .background(color = MaterialTheme.colors.background)
                .padding(8.dp)
        ) {
            Row(
                modifier = GlanceModifier.fillMaxWidth().height(32.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Image(
                    provider = ImageProvider(R.drawable.ic_android_black_24dp),
                    contentDescription = "",
                    modifier = GlanceModifier.size(32.dp)
                        .clickable(actionStartActivity(getMainActivityIntent(context)))
                )
                Row(GlanceModifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Image(
                        provider = ImageProvider(R.drawable.ic_baseline_refresh_24),
                        contentDescription = "",
                        modifier = GlanceModifier.size(32.dp)
                            .clickable(actionRunCallback<UpdateListAction>())
                    )
                }
            }
            if (list.isEmpty()) {
                EmptyListContent()
            } else {
                UniversityListContent()
            }
        }
    }

    @Composable
    fun EmptyListContent() {
        Text("No Data")
    }

    @Composable
    fun UniversityListContent() {
        LazyColumn(
            modifier = GlanceModifier.fillMaxSize()
                .padding(8.dp)
        ) {
            items(list) { it ->
                UniversityListItem(it)
            }
        }
    }

    @Composable
    fun UniversityListItem(item: RedditItemModel) {
        Text(item.title, GlanceModifier.padding(2.dp))
    }

    private fun getMainActivityIntent(context: Context) = Intent(context, MainActivity::class.java)
}