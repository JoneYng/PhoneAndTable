package com.zx.fitter

import android.content.res.Resources
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.window.layout.WindowMetricsCalculator

class GeneralActivity : ComponentActivity() {
    lateinit var  tvMessage: TextView

//    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
//        super.onMultiWindowModeChanged(isInMultiWindowMode)
//        if (isInMultiWindowMode) {
//            // 进入分屏模式，切换到简化布局
//            setContentView(R.layout.activity_compact)
//        } else {
//            // 退出分屏，恢复完整布局
//            setContentView(R.layout.activity_normal)
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_calendar)
        val root: ConstraintLayout = findViewById(R.id.main)
//        tvMessage = findViewById(R.id.tv_general)
//        //监听窗口大小变化
//        root.viewTreeObserver.addOnGlobalLayoutListener{
//            computeWindowSizeClasses()
//        }
//        computeWindowSizeClasses()
    }

    // 非 Compose 应用中的使用方法
    fun computeWindowSizeClasses() {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val widthDp = metrics.bounds.width() / Resources.getSystem().displayMetrics.density
        val widthWindowSizeClass = when {
            widthDp < 600f -> WindowSize.COMPACT
            widthDp < 840f -> WindowSize.MEDIUM
            else -> WindowSize.EXPANDED
        }
        val heightDp = metrics.bounds.height() / Resources.getSystem().displayMetrics.density
        val heightWindowSizeClass = when {
            heightDp < 480f -> WindowSize.COMPACT
            heightDp < 900f -> WindowSize.MEDIUM
            else -> WindowSize.EXPANDED
        }
        onWidthWindowSize(widthWindowSizeClass, heightWindowSizeClass)
    }

    fun onWidthWindowSize(
        widthWindowSizeClass: WindowSize, heightWindowSizeClass: WindowSize
    ) {
        when (widthWindowSizeClass) {
            WindowSize.COMPACT -> {
                tvMessage.text="小屏"
            }
            WindowSize.MEDIUM -> {
                tvMessage.text="中屏"
            }
            WindowSize.EXPANDED -> {
                tvMessage.text="大屏"
            }
        }
    }
}

enum class WindowSize { COMPACT, MEDIUM, EXPANDED }
