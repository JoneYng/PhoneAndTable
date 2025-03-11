package com.zx.fitter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zx.fitter.ui.theme.PhoneAndTableTheme

/**
 * @description:
 * @author: zhouxiang
 * @created: 2025/02/08 15:56
 * @version: V1.0
 */
class PaneScaffoldActivity : ComponentActivity() {
    data class VideoItem(val title: String, val url: String)
    val videoList = listOf(
        VideoItem(
            "视频 1",
            "https://www.w3schools.com/html/mov_bbb.mp4"
        ),
        VideoItem(
            "视频 2",
            "https://www.w3schools.com/html/movie.mp4"
        ),
    )
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhoneAndTableTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    SampleSupportingPaneScaffoldSimplified()
                }
            }
        }
    }

    @Preview
    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    @Composable
    fun SampleSupportingPaneScaffoldSimplified() {
        var currentVideoUrl by remember { mutableStateOf(videoList.first().url) }
        val navigator = rememberSupportingPaneScaffoldNavigator()
        BackHandler(navigator.canNavigateBack()) {
            navigator.navigateBack()
        }
        SupportingPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            mainPane = {
                MainPane(
                    videoUrl = currentVideoUrl,
                    shouldShowSupportingPaneButton = navigator.scaffoldValue.secondary == PaneAdaptedValue.Hidden,
                    onNavigateToSupportingPane = { navigator.navigateTo(ThreePaneScaffoldRole.Secondary) }
                )
            },
            supportingPane = {
                SupportingPane(onVideoSelected = { selectedUrl ->
                    currentVideoUrl = selectedUrl
                })
            },
            extraPane = { ExtraPane() },
        )
    }


    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    @Composable
    fun ThreePaneScaffoldScope.SupportingPane(
        modifier: Modifier = Modifier,
        onVideoSelected: (String) -> Unit
    ) {
        AnimatedPane(modifier = modifier.safeContentPadding()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(videoList.size) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onVideoSelected(videoList[index].url) },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.elevatedCardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = videoList[index].title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ThreePaneScaffoldScope.MainPane(
    videoUrl: String,
    shouldShowSupportingPaneButton: Boolean,
    onNavigateToSupportingPane: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedPane(modifier = modifier.safeContentPadding()) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(color = Color.White, text = "$videoUrl")
            if (shouldShowSupportingPaneButton) {
                Button(
                    modifier = Modifier.wrapContentSize().align(Alignment.BottomEnd),
                    onClick = onNavigateToSupportingPane
                ) {
                    Text("显示目录")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ThreePaneScaffoldScope.ExtraPane(
    modifier: Modifier = Modifier,
) {
    AnimatedPane(modifier = modifier.safeContentPadding()) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text("extraPane")
        }
    }
}



