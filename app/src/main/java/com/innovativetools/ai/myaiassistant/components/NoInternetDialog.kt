package com.innovativetools.ai.myaiassistant.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innovativetools.ai.myaiassistant.ui.theme.OpenSans
import com.innovativetools.ai.myaiassistant.R


@Composable
fun NoInternetDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.connection_issue),
                fontSize = 22.sp,
                fontWeight = FontWeight.W700,
                fontFamily = OpenSans,
                color = MaterialTheme.colors.surface
            )
        },

        text = {
            Text(
                text = stringResource(R.string.connect_try_again),
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                fontFamily = OpenSans,
                color = MaterialTheme.colors.surface
            )
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .height(60.dp)
                        .weight(0.5f),
                    shape = RoundedCornerShape(90.dp),
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = OpenSans
                        ),
                        color = MaterialTheme.colors.surface
                    )
                }
            }
        },
        modifier = Modifier
            .background(MaterialTheme.colors.background, RoundedCornerShape(35.dp))
            .border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(35.dp))
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onSurface
    )
}
