package com.zx.fitter.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zx.fitter.R
import com.zx.fitter.ui.theme.AppColorA5


/**
 * @description:
 * @author: zhouxiang
 * @created: 2024/09/02 13:27
 * @version: V1.0
 */
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun EmptyView(modifier:Modifier=Modifier, top:Dp=0.dp, emptyRes: Int = R.mipmap.ic_study_no_course, emptyText: String = "文案") {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier=Modifier.padding(top = top),
            painter = painterResource(id = emptyRes),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            fontSize = 12.sp,
            color = AppColorA5,
            text = emptyText
        )

    }
}