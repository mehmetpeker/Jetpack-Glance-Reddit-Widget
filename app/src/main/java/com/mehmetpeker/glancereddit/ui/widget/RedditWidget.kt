package com.mehmetpeker.glancereddit.ui.widget

import android.content.Context
import android.content.Intent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
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
        val glanceState = currentState<RedditPreferences>()
        val list = glanceState.redditList.toList()


        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(12.dp)
                .appWidgetBackground()
                .background(color = Color.White)
                .padding(8.dp)
        ) {
            Row(
                modifier = GlanceModifier.fillMaxWidth().height(32.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Text(
                    "Last 10 Post",style = TextStyle(color = ColorProvider(R.color.grey_900))
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
            if (list.isEmpty())
                EmptyListContent()
            else
                RedditListContent(list = list)
        }

    }

    @Composable
    fun EmptyListContent() {
        Text("No Data")
    }

    @Composable
    fun RedditListContent(list: List<RedditItemModel>) {
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
        Column(modifier = GlanceModifier.clickable(actionRunCallback<ItemClickAction>())) {
            Text(item.title,style = TextStyle(color = ColorProvider(R.color.grey_900)))
            Spacer(GlanceModifier.height(8.dp))
            Text(item.title,style = TextStyle(color = ColorProvider(R.color.grey_900)))
        }
    }

    @Composable
    fun RedditListItemPreview() {
        val itemModel = RedditItemModel(
            "Any advice for books on modern android testing techniques for both Java and Kotlin?",
            "I'm having real trouble finding info about testing and what platforms and techniques I should be using.",
            "https://www.reddit.com/r/androiddev/comments/tjc0qg/any_advice_for_books_on_modern_android_testing/"
        )
        RedditListItem(item = itemModel)
    }

    private fun getMainActivityIntent(context: Context) = Intent(context, MainActivity::class.java)
}
