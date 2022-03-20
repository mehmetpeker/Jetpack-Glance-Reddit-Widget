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
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import com.mehmetpeker.glancereddit.MainActivity
import com.mehmetpeker.glancereddit.R
import com.mehmetpeker.glancereddit.RedditGlanceStateDefinition
import com.mehmetpeker.glancereddit.data.RedditItemModel
import com.mehmetpeker.glancereddit.data.RedditPreferences

class RedditWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<RedditPreferences>
        get() = RedditGlanceStateDefinition
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val glanceState = currentState<RedditPreferences>()
        val list = glanceState.redditList.toList()
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
            if(list.isEmpty())
                EmptyListContent()
            else
                RedditListContent(list =list)
        }

    }

    @Composable
    fun EmptyListContent() {
        Text("No Data")
    }

    @Composable
    fun RedditListContent(list:List<RedditItemModel>) {
        LazyColumn(
            modifier = GlanceModifier.fillMaxSize()
                .padding(8.dp)
        ) {
            items(list) { it ->
                RedditListItem(it)
            }
        }
    }

    @Composable
    fun RedditListItem(item: RedditItemModel) {
        Text(item.title, GlanceModifier.padding(2.dp))
    }

    private fun getMainActivityIntent(context: Context) = Intent(context, MainActivity::class.java)
}
