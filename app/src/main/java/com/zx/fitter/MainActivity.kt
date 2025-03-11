package com.zx.fitter

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zx.fitter.ui.theme.PhoneAndTableTheme

class MainActivity : ComponentActivity() {
    val exampleList = listOf(
        "普通 Activity" to GeneralActivity::class.java,
        "Compose Activity" to ComposeCalendarActivity::class.java,
        "列表详情（消息中心）" to ListDetailActivity::class.java,
        "视频播放 PaneScaffold" to PaneScaffoldActivity::class.java,
        "自适应布局 BoxWithConstraints" to BoxWithConstraintsActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            PhoneAndTableTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SimpleListView()
                }
            }
        }
    }
    @Composable
    fun SimpleListView() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(exampleList.size) { index->
                    ElevatedButton(modifier = Modifier.fillMaxWidth(), onClick = {
                        val intent = Intent(this@MainActivity, exampleList[index].second)
                        startActivity(intent)
                    }) {
                        Text(exampleList[index].first)
                    }
                }
            }
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PhoneAndTableTheme {
            SimpleListView()
        }
    }
}

