package com.innovativetools.ai.myaiassistant.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innovativetools.ai.myaiassistant.ui.theme.teal_200
import com.innovativetools.ai.myaiassistant.ui.theme.OpenSans
import com.innovativetools.ai.myaiassistant.ui.theme.White
import com.innovativetools.ai.myaiassistant.utils.click

@Composable
fun TabItem(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (selected) White else teal_200,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
            fontFamily = OpenSans,
            lineHeight = 25.sp
        ), modifier = Modifier
            .padding(5.dp)
            .click(onClick = {
                onClick()
            })
            .background(
                shape = RoundedCornerShape(10.dp),
                color = if (selected) teal_200 else Color.Transparent
            )
            .border(2.dp, color = teal_200, shape = RoundedCornerShape(10.dp))
            .padding(vertical = 10.dp, horizontal = 20.dp)
    )


}
