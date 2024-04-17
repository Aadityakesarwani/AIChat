package com.innovativetools.ai.myaiassistant.ui.chat

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.innovativetools.ai.myaiassistant.R
import com.innovativetools.ai.myaiassistant.utils.Constants
import com.innovativetools.ai.myaiassistant.components.ChatInputText
import com.innovativetools.ai.myaiassistant.components.ChatMessageCard
import com.innovativetools.ai.myaiassistant.components.MainAppBar
import com.innovativetools.ai.myaiassistant.utils.showRewarded
import com.innovativetools.ai.myaiassistant.data.model.MessageModel
import com.innovativetools.ai.myaiassistant.ui.theme.OpenSans
import com.innovativetools.ai.myaiassistant.ui.theme.PastelOrange
import com.innovativetools.ai.myaiassistant.ui.theme.PastelRed
import com.innovativetools.ai.myaiassistant.ui.theme.White
import com.innovativetools.ai.myaiassistant.ui.theme.teal_200
import com.innovativetools.ai.myaiassistant.utils.click
import com.innovativetools.ai.myaiassistant.utils.showTwoRewardedAds


@Composable
fun ChatScreen(
    navigateToBack: () -> Unit,
    navigateToUpgrade: () -> Unit,
    examples: List<String>?,
    viewModel: ChatViewModel = hiltViewModel(),
) {

    val freeMessageCount by viewModel.freeMessageCount.collectAsState()
    val isProVersion by viewModel.isProVersion.collectAsState()
    val conversationId by viewModel.currentConversationState.collectAsState()
    val messagesMap by viewModel.messagesState.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val context = LocalContext.current
    var keyboardFocusState by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        viewModel.getProVersion()
        viewModel.getFreeMessageCount()
    }

    val messages: List<MessageModel> =
        if (messagesMap[conversationId] == null) listOf() else messagesMap[conversationId]!!

    val paddingBottom =
        animateDpAsState(
            if (isGenerating) {
                90.dp
            } else if (viewModel.showAdsAndProVersion.value) {
                190.dp
            } else {
                0.dp
            },
            animationSpec = tween(Constants.TRANSITION_ANIMATION_DURATION)
        )

    val inputText = remember {
        mutableStateOf("")
    }


    val rotationState = rememberInfiniteTransition()
    val rotationAngle by rotationState.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing,
            )
        )
    )

    Column(
        Modifier
    ) {

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            MainAppBar(
                onClickAction = {},
                image = R.drawable.app_icon,
                text = stringResource(R.string.chat),
                tint = Color.Unspecified
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                if (isProVersion) {
                    Image(
                        painter = painterResource(R.drawable.pro_icon), // Replace with the actual resource ID
                        contentDescription = "Pro Icon",
                        colorFilter = ColorFilter.tint(teal_200),
                        modifier = Modifier
                            .size(40.dp, 40.dp)
                            .padding(end = 7.dp)
                            .graphicsLayer(
                                rotationY = rotationAngle,
                            )
                    )
                }
                else {
                    Row(
                        modifier = Modifier
                            .padding(end = 15.dp)
                    ) {

                        Text(
                            text = freeMessageCount.toString(),
                            color = MaterialTheme.colors.surface,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = OpenSans,
                                lineHeight = 25.sp
                            ),
                            textAlign = TextAlign.Center
                        )

                        Icon(
                            painter = painterResource(R.drawable.shines),
                            contentDescription = "image",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp)
                                .padding(end = 5.dp, start = 5.dp)
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
        )
        {
            if (messages.isEmpty() and viewModel.showAdsAndProVersion.value.not()) {
                if (examples.isNullOrEmpty()) {
                    Capabilities(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 14.dp)
                    )
                } else
                {
                    Examples(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 14.dp),
                        examples = examples,
                        inputText = inputText
                    )
                }
            } else {
                MessageList(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = paddingBottom.value), messages
                )
            }

            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AnimatedVisibility(
                    visible = isGenerating,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(Constants.TRANSITION_ANIMATION_DURATION)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(Constants.TRANSITION_ANIMATION_DURATION)
                    ),
                    content = {
                        StopButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            viewModel.stopGenerate()
                        }
                    })

                AnimatedVisibility(
                    visible = viewModel.showAdsAndProVersion.value,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(Constants.TRANSITION_ANIMATION_DURATION)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(Constants.TRANSITION_ANIMATION_DURATION)
                    ),
                    content = {
                        ShowDialog(context = context, viewModel = viewModel, navigateToUpgrade)
                    }
                )

            }

        }

        ChatInputText(
            inputText = inputText
        )
    }

}



@Composable
fun ShowDialog(
    context: Context,
    viewModel: ChatViewModel,
    navigateToUpgrade: () -> Unit,
) {
    val openDialog = remember { mutableStateOf(true) }

    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        Dialog(onDismissRequest = { }) {
            Box(modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
    if (openDialog.value) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        IconButton(
                            onClick = {
                                openDialog.value = false
                                      },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .background(MaterialTheme.colors.surface, shape = CircleShape)
                        ) {
                            Icon(Icons.Filled.Close, contentDescription = "Close dialog")
                        }
                    }
                    AdsAndProVersion(
                        modifier = Modifier.fillMaxWidth(),
                        onClickWatchAd = {
                            showRewarded(context) {
                                viewModel.showAdsAndProVersion.value = false
                                viewModel.increaseFreeMessageCount()
                                openDialog.value = false
                            }
                        },
                        onClickWatchTwoAd = {
                            showTwoRewardedAds(context, showDialog = showDialog) {
                                viewModel.showAdsAndProVersion.value = false
                                viewModel.increaseThreeFreeMessageCount()
                                openDialog.value = false
                            }
                        },
                        onClickUpgrade = {
                            navigateToUpgrade()
                            openDialog.value = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AdsAndProVersion(modifier: Modifier, onClickWatchAd: () -> Unit, onClickWatchTwoAd: () -> Unit, onClickUpgrade: () -> Unit) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // Replace the content inside this Box with your app icon
                Icon(
                    painter = painterResource(R.drawable.aiappicon),
                    contentDescription = stringResource(R.string.app_name),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(65.dp)
                )
            }

            Text(
                text = stringResource(R.string.you_reach_free_message_limit),
                color = Color.Black,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = OpenSans,
                    lineHeight = 20.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )

            // Replaced Row with a Column
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            ) {
                // Watch Ad button
                Box(
                    modifier = Modifier
                        .click(onClick = onClickWatchAd)
                        .background(
                            shape = RoundedCornerShape(70.dp),
                            color = PastelRed
                        )
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                        .widthIn(min = 100.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_video_ad),
                        contentDescription = stringResource(R.string.app_name),
                        tint = White,
                        modifier = Modifier
                            .size(width = 30.dp, height = 30.dp) // Set icon size to at least 60dp
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = stringResource(id = R.string.watch_an_ad),
                        color = White,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth() // Center the text
                    )
                }


                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .click(onClick = onClickWatchTwoAd)
                        .background(
                            shape = RoundedCornerShape(70.dp),
                            color = PastelRed
                        )
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                        .widthIn(min = 100.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_video_ad),
                        contentDescription = stringResource(R.string.app_name),
                        tint = White,
                        modifier = Modifier
                            .size(width = 30.dp, height = 30.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = stringResource(id = R.string.watch_two_ad),
                        color = White,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth() // Center the text
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Upgrade button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                        .click(onClick = onClickUpgrade)
                        .background(
                            shape = RoundedCornerShape(70.dp),
                            color = PastelOrange
                        )
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                        .widthIn(min = 100.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_lock_open),
                        contentDescription = stringResource(R.string.app_name),
                        tint = White,
                        modifier = Modifier
                            .size(width = 30.dp, height = 30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(id = R.string.upgrade_to_pro),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}



@Composable
fun StopButton(modifier: Modifier, onClick: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 15.dp)
                .click(onClick = onClick)
                .background(
                    shape = RoundedCornerShape(100.dp),
                    color = MaterialTheme.colors.onSecondary
                )
                .padding(vertical = 15.dp, horizontal = 15.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.square),
                contentDescription = stringResource(R.string.app_name),
                tint = teal_200,
                modifier = Modifier
                    .size(width = 20.dp, height = 20.dp)
            )
        }
    }
}


@Composable
fun Capabilities(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.prompt_1),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onSecondary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun Examples(
    modifier: Modifier = Modifier,
    examples: List<String>,
    inputText: MutableState<String>
) {
    Box(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.prompt_examples),
                color = MaterialTheme.colors.onSurface,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center
            )


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(examples) { example ->
                    Text(
                        text = example,
                        color = MaterialTheme.colors.onSurface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .click(
                                onClick = {
                                    inputText.value = example
                                })
                            .background(
                                color = MaterialTheme.colors.onSecondary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(20.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

        }
    }
}

const val ConversationTestTag = "ConversationTestTag"

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    messages: List<MessageModel>,
) {
    val listState = rememberLazyListState()

    Box(modifier = modifier) {
        LazyColumn(
            contentPadding =
            WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
            modifier = Modifier
                .testTag(ConversationTestTag)
                .fillMaxSize(),
            reverseLayout = true,
            state = listState,
        ) {
            items(messages.size) { index ->
                Box(modifier = Modifier.padding(bottom = if (index == 0) 10.dp else 0.dp)) {
                    Column {
                        ChatMessageCard(
                            message = messages[index],
                            isLast = index == messages.size - 1,
                            isHuman = true
                        )
                        ChatMessageCard(message = messages[index])
                    }
                }
            }
        }
    }
}
