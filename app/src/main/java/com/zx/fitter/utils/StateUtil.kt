package com.zx.fitter.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * @description:状态工具类
 * @author: zhouxiang
 * @created: 2024/08/13 09:48
 * @version: V1.0
 */

/**
 * 快捷使用remember { mutableStateOf(T) }
 * Quick use remember { mutableStateOf(T) }
 */
@Composable
inline fun <T> rememberMutableStateOf(
    crossinline initValue: @DisallowComposableCalls () -> T
) = remember { mutableStateOf(initValue()) }

@Composable
inline fun <T> rememberMutableStateOf(
    key1: Any?,
    crossinline initValue: @DisallowComposableCalls () -> T
): MutableState<T> = remember(key1 = key1) { mutableStateOf(initValue()) }

@Composable
inline fun <T> rememberMutableStateOf(
    key1: Any?,
    key2: Any?,
    crossinline initValue: @DisallowComposableCalls () -> T
): MutableState<T> = remember(key1 = key1, key2 = key2) { mutableStateOf(initValue()) }

@Composable
inline fun <T> rememberMutableStateOf(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    crossinline initValue: @DisallowComposableCalls () -> T
): MutableState<T> = remember(key1 = key1, key2 = key2, key3 = key3) { mutableStateOf(initValue()) }

@Composable
fun <T> rememberMutableStateListOf(): SnapshotStateList<T> = remember { SnapshotStateList() }

@Composable
inline fun <T> rememberMutableStateListOf(
    crossinline initValue: @DisallowComposableCalls () -> List<T>
): SnapshotStateList<T> = remember {
    SnapshotStateList<T>().apply {
        addAll(initValue())
    }
}

@Deprecated(
    "Need to use a function with the same name as lambda for higher performance", ReplaceWith(
        "rememberMutableStateOf { value }",
        "androidx.compose.runtime.remember",
        "androidx.compose.runtime.mutableStateOf"
    )
)
@Composable
fun <T> rememberMutableStateOf(value: T): MutableState<T> = remember { mutableStateOf(value) }

@Deprecated(
    "Need to use a function with the same name as lambda for higher performance", ReplaceWith(
        "rememberMutableStateOf(key1) { value }",
        "androidx.compose.runtime.remember",
        "androidx.compose.runtime.mutableStateOf"
    )
)
@Composable
fun <T> rememberMutableStateOf(key1: Any?, value: T): MutableState<T> =
    remember(key1 = key1) { mutableStateOf(value) }

@Deprecated(
    "Need to use a function with the same name as lambda for higher performance", ReplaceWith(
        "rememberMutableStateOf(key1, key2) { value }",
        "androidx.compose.runtime.remember",
        "androidx.compose.runtime.mutableStateOf"
    )
)
@Composable
fun <T> rememberMutableStateOf(key1: Any?, key2: Any?, value: T): MutableState<T> =
    remember(key1 = key1, key2 = key2) { mutableStateOf(value) }

@Deprecated(
    "Need to use a function with the same name as lambda for higher performance", ReplaceWith(
        "rememberMutableStateOf(key1, key2, key3) { value }",
        "androidx.compose.runtime.remember",
        "androidx.compose.runtime.mutableStateOf"
    )
)
@Composable
fun <T> rememberMutableStateOf(key1: Any?, key2: Any?, key3: Any?, value: T): MutableState<T> =
    remember(key1 = key1, key2 = key2, key3 = key3) { mutableStateOf(value) }