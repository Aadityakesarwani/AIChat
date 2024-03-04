package com.innovativetools.ai.myaiassistant.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innovativetools.ai.myaiassistant.ui.theme.OpenSans

@Composable
fun MainAppBar(
    onClickAction: () -> Unit,
    image: Int,
    text: String,
    tint: Color,
    menuItems: (@Composable () -> Unit)? = null
) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
    {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 16.dp),
            color = MaterialTheme.colors.surface,
            style = TextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 20.sp,
                fontFamily = OpenSans,
                textAlign = TextAlign.Center
            )
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
                Icon(
                    painter = painterResource(image),
                    contentDescription = "image",
                    tint = tint,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                )

            Spacer(modifier = Modifier.weight(1f))

            if (menuItems != null) {
                menuItems()
            }

        }
    }


}