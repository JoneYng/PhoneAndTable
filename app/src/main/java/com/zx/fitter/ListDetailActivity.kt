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
                    MessageScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Preview
@Composable
fun MessageScreen() {
    // 创建一个 ListDetailPaneScaffold 导航器，专为支持消息列表和详情页的导航
    val navigator = rememberListDetailPaneScaffoldNavigator<Message>()

    // 获取当前上下文并尝试将其转换为 Activity，便于在非导航场景下调用 `finish()`
    val context = LocalContext.current
    val activity = context as? Activity

    // 使用 BackHandler 实现返回逻辑
    // 如果 `navigator` 表示可以返回（即当前处于详情页），则执行返回操作
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    // 使用 Column 构建页面的整体布局
    Column {
        // 标题栏部分
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(White), // 背景颜色为白色
            verticalAlignment = Alignment.CenterVertically // 垂直居中
        ) {
            // 返回按钮（左侧箭头）
            Image(
                painter = painterResource(id = R.mipmap.ic_calendar_left_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 30.dp, height = 30.dp) // 图片尺寸
                    .clickable {
                        if (navigator.canNavigateBack()) {
                            // 如果处于详情页，返回至消息列表
                            navigator.navigateBack()
                        } else {
                            // 如果已在消息列表页，直接退出 Activity
                            activity?.finish()
                        }
                    }
            )

            // 动态文本，显示 "详情" 或 "消息列表"
            Text(
                text = if (navigator.canNavigateBack()) "详情" else "消息列表",
                fontSize = 20.sp
            )
        }

        // 核心布局：ListDetailPaneScaffold
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective, // 控制面板切换的指令
            value = navigator.scaffoldValue,         // 当前导航的状态
            // 消息列表面板（左侧/主界面）
            listPane = {
                AnimatedPane {
                    MessageList(
                        // 点击列表项时，切换到详情页
                        onItemClick = { item ->
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                        },
                    )
                }
            },
            // 详情面板（右侧/详情界面）
            detailPane = {
                AnimatedPane {
                    // 如果 `navigator` 当前选中了某个消息，则显示其详情
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
