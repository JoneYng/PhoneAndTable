package com.zx.fitter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.zx.fitter.ui.theme.PhoneAndTableTheme

/**
 * @description:
 * @author: zhouxiang
 * @created: 2025/02/08 15:56
 * @version: V1.0
 */
class BoxWithConstraintsActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhoneAndTableTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Card()
                }
            }
        }
    }


    @Preview
    @Composable
    fun Card() {
        BoxWithConstraints {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text("宽度：${this@BoxWithConstraints.maxWidth}.")
                    Text("高度：${this@BoxWithConstraints.maxHeight}")
                    Spacer(Modifier.size(30.dp))
                    if (this@BoxWithConstraints.maxWidth < 600.dp) {
                        // 小屏幕（比如手机）
                        Text("这是一个小屏幕", fontSize = 16.sp)
                    } else {
                        // 大屏幕（比如平板）
                        Text("这是一个大屏幕", fontSize = 24.sp)
                    }
                }
            }
        }
    }
}


