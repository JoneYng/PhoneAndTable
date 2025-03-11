package com.zx.fitter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zx.fitter.ui.theme.AppText3
import com.zx.fitter.ui.theme.AppText9
import com.zx.fitter.ui.theme.PhoneAndTableTheme
import kotlinx.parcelize.Parcelize

/**
 * @description:
 * @author: zhouxiang
 * @created: 2025/02/08 15:56
 * @version: V1.0
 */
class ListDetailActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            PhoneAndTableTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SampleListDetailPaneScaffoldFull()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Preview
@Composable
fun SampleListDetailPaneScaffoldFull() {
    val navigator = rememberListDetailPaneScaffoldNavigator<Message>()
    val context = LocalContext.current
    val activity = context as? Activity
    //支持使用系统返回手势或按钮返回
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.mipmap.ic_calendar_left_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 30.dp, height = 30.dp)
                    .clickable {
                        if (navigator.canNavigateBack()) {
                            navigator.navigateBack()
                        } else {
                            activity?.finish()
                        }
                    })
            Text(text = if (navigator.canNavigateBack()) "详情" else "消息列表", fontSize = 20.sp)
        }
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
                    MessageList(
                        onItemClick = { item ->
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                        },
                    )
                }
            },
            detailPane = {
                AnimatedPane {
                    // Show the detail pane content if selected item is available
                    navigator.currentDestination?.content?.let {
                        messageDetails(it)
                    }
                }
            },
        )
    }
}

@Composable
fun MessageList(
    onItemClick: (Message) -> Unit,
) {
    LazyColumn {
        messageList.forEachIndexed { id, item ->
            item {
                ListItem(
                    modifier = Modifier.clickable {
                        onItemClick(Message(id))
                    },
                    headlineContent = {
                        messageItem(item)
                    },
                )
            }
        }
    }
}

@Composable
fun messageItem(message: Message) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)
    ) {
        AvatarView(message.res)
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(text = message.title, fontSize = 18.sp, color = AppText3)
            Text(text = message.content, fontSize = 15.sp, color = AppText9)
        }
    }
}

@Preview
@Composable
fun AvatarView(@DrawableRes id: Int = R.mipmap.ic_launcher) {
    Image(
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = Modifier
            .size(width = 50.dp, height = 50.dp) //指定size为200dp
            .background(White)// 白色背景
            .clip(CircleShape), // 这是为圆形裁剪
        contentScale = ContentScale.FillBounds, // 等价于ImageView的fitXY
        alignment = Alignment.Center,
    ) // 居中显示
}

@Composable
fun messageDetails(item: Message) {
    val messageInfo = messageList[item.id]
    Card {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "${messageInfo.content}",
                fontSize = 24.sp,
            )
            Spacer(Modifier.size(16.dp))
        }
    }
}

@Parcelize
class Message(
    val id: Int = 0, var title: String = "", var content: String = "", @DrawableRes var res: Int = 0
) : Parcelable

val messageList = listOf<Message>(
    Message(title = "系统消息", content = "这是一条系统消息", res = R.mipmap.ic_popular_events),
    Message(title = "热门活动", content = "这是一条热门活动", res = R.mipmap.ic_system_notification)
)
