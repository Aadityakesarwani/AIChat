package com.innovativetools.ai.myaiassistant.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innovativetools.ai.myaiassistant.ui.theme.OpenSans
import com.innovativetools.ai.myaiassistant.R
import com.innovativetools.ai.myaiassistant.ui.theme.GrayShadow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AssistantCard(
    image: Int,
    name: String,
    description: String,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(5.dp)
            .clickable{ onClick() }
            .size(width = 200.dp, height = 170.dp)
            .height(200.dp)
            .background(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colors.onSecondary)
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp)
                .background(shape = RoundedCornerShape(13.dp), color = GrayShadow)
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = name,
            color = MaterialTheme.colors.surface,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                fontFamily = OpenSans,
                lineHeight = 20.sp
            )
        )

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Text(
                text = description,
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = OpenSans,
                    lineHeight = 15.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}
