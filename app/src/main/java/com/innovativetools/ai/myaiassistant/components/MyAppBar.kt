package com.innovativetools.ai.myaiassistant.components

import android.service.autofill.OnClickAction
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
fun MyAppBar(
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
                .padding(start = 16.dp, end = 16.dp),
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
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            IconButton(
                onClick = onClickAction,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            ) {

                Icon(
                    painter = painterResource(image),
                    contentDescription = "image",
                    tint = tint,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (menuItems != null) {
                menuItems()
            }

        }
    }


}




