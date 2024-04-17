package com.innovativetools.ai.myaiassistant.components

import android.content.Intent
import android.health.connect.datatypes.units.Length
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.innovativetools.ai.myaiassistant.R
import com.innovativetools.ai.myaiassistant.data.model.MessageModel
import com.innovativetools.ai.myaiassistant.ui.theme.Typography
import com.innovativetools.ai.myaiassistant.ui.theme.semiTransparentTeal
import es.dmoral.toasty.Toasty
import es.dmoral.toasty.Toasty.LENGTH_SHORT

@Composable
fun ChatMessageCard(message: MessageModel, isHuman: Boolean = false, isLast: Boolean = false) {
    Column(
        horizontalAlignment = if (isHuman) Alignment.End else Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = if (isHuman) 0.dp else 10.dp)
            .padding(start = if (isHuman) 10.dp else 0.dp)
            .padding(vertical = 4.dp)
            .padding(top = if (isLast) 50.dp else 0.dp)
    ) {
        if (isHuman) {
            UserMessageCard(message = message)
        } else {
            BotMessageCard(message = message)
        }
    }
}


@Composable
fun UserMessageCard(message: MessageModel) {
    Box(
        modifier = Modifier
            .widthIn(0.dp)
            .background(
                MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        Text(
            text = message.question,
            color = MaterialTheme.colors.surface,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            textAlign = TextAlign.Start,
            style = Typography.body1
        )
    }
}


@Composable
fun BotMessageCard(
    message: MessageModel,
) {
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message.answer.trimIndent())
        type = "text/plain"
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.padding(end = 23.dp)) {
            Box(
                modifier = Modifier
                .widthIn(0.dp)
//                    .fillMaxWidth()
                    .background(
                        semiTransparentTeal, // Choose your theme's color for the bot messages
                        shape = RoundedCornerShape(10.dp)
                    )
//                    .padding(start = 12.dp, end = 20.dp, top = 10.dp, bottom = 10.dp) //
                .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    text = message.answer,
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
            }
        }


        // Row for Copy and Share buttons
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            // Share Button
            IconButton(
                onClick = {
                    // Share message logic
                    context.startActivity(shareIntent)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share message",
                    tint = MaterialTheme.colors.onBackground
                )
            }
            // Copy Button
            IconButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(message.answer.trimIndent()))
                    Toasty.success(context,R.string.text_copied, LENGTH_SHORT).show()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = "Copy message",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}




//@Composable
//fun BotMessageCard(message: MessageModel) {
//    val sendIntent: Intent = Intent().apply {
//        action = Intent.ACTION_SEND
//        putExtra(Intent.EXTRA_TEXT, message.answer.trimIndent())
//        type = "text/plain"
//    }
//    val shareIntent = Intent.createChooser(sendIntent, null)
//    val context = LocalContext.current
//    val clipboardManager: ClipboardManager = LocalClipboardManager.current
//    var expanded by remember { mutableStateOf(false) }
//    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
//        Box(
//            modifier = Modifier
//                .widthIn(0.dp)
//                .background(
//                    MaterialTheme.colors.onSecondary,
//                    shape = RoundedCornerShape(
//                        topEnd = 14.dp,
//                        topStart = 5.dp,
//                        bottomEnd = 14.dp,
//                        bottomStart = 14.dp
//                    )
//                )
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onLongPress = {
//                            expanded = true
//                        }
//                    )
//                },
//        ) {
//            MaterialRichText(
//                modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
//                style = RichTextStyle(
//                    codeBlockStyle = CodeBlockStyle(
//                        textStyle = TextStyle(
//                            fontFamily = OpenSans,
//                            fontSize = 14.sp,
//                            color = MaterialTheme.colors.surface
//                        ),
//                        wordWrap = true,
//                        modifier = Modifier.background(
//                            shape = RoundedCornerShape(6.dp),
//                            color = DarkBackground,
//                        )
//                    ),
//                    tableStyle = TableStyle(borderColor = MaterialTheme.colors.surface),
//                )
//            ) {
//                Markdown(
//                    content = message.answer.trimIndent()
//                )
//            }
//        }
//        MaterialTheme(
//            colors = MaterialTheme.colors.copy(surface = MaterialTheme.colors.surface),
//            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16))
//        ) {
//            DropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false },
//                modifier = Modifier
//                    .alignByBaseline()
//                    .background(MaterialTheme.colors.onSecondary, RoundedCornerShape(10.dp)),
//                properties = PopupProperties(focusable = false)
//            ) {
//                DropdownMenuItem(
//                    onClick = {
//                        clipboardManager.setText(AnnotatedString((message.answer.trimIndent())))
//                        expanded = false
//                    }
//                ) {
//                    Icon(
//                        painterResource(R.drawable.ic_copy),
//                        "copyMessage",
//                        modifier = Modifier.size(25.dp),
//                        tint = teal_200,
//                    )
//                    Text(
//                        text = stringResource(R.string.copy),
//                        color = teal_200,
//                        modifier = Modifier.padding(horizontal = 10.dp),
//                        style = Typography.body1
//                    )
//                }
//                Divider(
//                    color = MaterialTheme.colors.secondary, thickness = 1.dp,
//                )
//                   DropdownMenuItem(
//                       onClick = {
//                           context.startActivity(shareIntent)
//                           expanded = false
//                       }
//                   )
//                   {
//                       Icon(
//                           painterResource(R.drawable.share),
//                           "shareMessage",
//                           modifier = Modifier.size(25.dp),
//                           tint = teal_200,
//                       )
//                       Text(
//                           text = stringResource(R.string.share),
//                           color = teal_200,
//                           modifier = Modifier.padding(horizontal = 10.dp),
//                           style = Typography.body1
//                       )
//
//                   }
//               }
//        }
//
//    }
//}









