package com.zx.fitter.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role


/**
 * @description:防止重复点击(有的人可能会手抖连点两次,造成奇怪的bug)
 * @author: zhouxiang
 * @created: 2024/08/13 10:32
 * @version: V1.0
 */
/**
 * creator: lt  2021/4/13  lt.dygzs@qq.com
 * effect : 修饰符扩展函数
 * warning:
 */

const val VIEW_CLICK_INTERVAL_TIME = 800

/**
 *
 */
@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
inline fun Modifier.click(
    time: Int = VIEW_CLICK_INTERVAL_TIME,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = LocalIndication.current,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    crossinline onClick: () -> Unit
): Modifier {
    var lastClickTime by rememberMutableStateOf { 0L }
    return clickable(interactionSource, indication, enabled, onClickLabel, role) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - time >= lastClickTime) {
            onClick()
            lastClickTime = currentTimeMillis
        }
    }
}

/**
 * 防止重复点击,比如用在Button时直接传入onClick函数
 */
@Composable
inline fun composeClick(
    time: Int = VIEW_CLICK_INTERVAL_TIME,
    crossinline onClick: () -> Unit
): () -> Unit {
    var lastClickTime by rememberMutableStateOf { 0L }
    return {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - time >= lastClickTime) {
            onClick()
            lastClickTime = currentTimeMillis
        }
    }
}
@Composable
inline fun composeTextClick(
    time: Int = VIEW_CLICK_INTERVAL_TIME,
    crossinline onClick: () -> Unit
): (Int) -> Unit {
    var lastClickTime by rememberMutableStateOf { 0L }
    return {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - time >= lastClickTime) {
            onClick()
            lastClickTime = currentTimeMillis
        }
    }
}