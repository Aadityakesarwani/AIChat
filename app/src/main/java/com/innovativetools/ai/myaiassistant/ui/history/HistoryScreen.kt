package com.innovativetools.ai.myaiassistant.ui.history

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.innovativetools.ai.myaiassistant.utils.Constants
import com.innovativetools.ai.myaiassistant.components.MyAppBar
import com.innovativetools.ai.myaiassistant.utils.toFormattedDate
import com.innovativetools.ai.myaiassistant.ui.theme.teal_200
import com.innovativetools.ai.myaiassistant.ui.theme.OpenSans
import com.innovativetools.ai.myaiassistant.R


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    navigateToBack : () -> Unit,
    navigateToChat: (String, String, List<String>?, String) -> Unit,
    navigateToDeleteConversations: () -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel(),
    savedStateHandle: SavedStateHandle? = null
) {

    if (savedStateHandle?.get<Boolean>(Constants.IS_DELETE) == true) {
        historyViewModel.deleteAllConversation()
        savedStateHandle.remove<Boolean>(Constants.IS_DELETE)
    }

    val conversations by historyViewModel.conversationsState.collectAsState()
    val isFetching by historyViewModel.isFetching.collectAsState()
    val darkMode by historyViewModel.darkMode.collectAsState()


    LaunchedEffect(true) {
        historyViewModel.getConversations()
        historyViewModel.getDarkMode()
    }

    var searchState by remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }


    var rotationState by remember { mutableStateOf(0f) }
    val rotation by animateFloatAsState(
        targetValue = rotationState - 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )

    LaunchedEffect(Unit) {
        rotationState -= 360f
    }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            if (searchState) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .padding(end = 15.dp)
                        .padding(top = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            searchState = !searchState
                        },
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = "image",
                            tint = MaterialTheme.colors.surface,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    OutlinedTextField(
                        value = searchText.value,
                        onValueChange = {
                            searchText.value = it
                        },
                        label = null,
                        placeholder = {
                            Text(
                                stringResource(R.string.search_conversation),
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onSurface,
                                fontFamily = OpenSans,
                                fontWeight = FontWeight.W600
                            )
                        },
                        textStyle = TextStyle(
                            color = MaterialTheme.colors.surface,
                            fontSize = 16.sp,
                            fontFamily = OpenSans,
                            fontWeight = FontWeight.W600
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 50.dp)
                            .heightIn(max = 50.dp)
                            .padding(end = 18.dp)
                            .weight(1f)
                            .border(
                                1.dp,
                                Color.Transparent,
                                RoundedCornerShape(16.dp)
                            )
                            .onFocusChanged { focusState -> hasFocus = focusState.hasFocus },
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = MaterialTheme.colors.surface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            backgroundColor = MaterialTheme.colors.secondary
                        )
                    )
                }
            } else {
                MyAppBar(
                    onClickAction = navigateToBack,
                    image = R.drawable.arrow_left,
                    text = stringResource(R.string.history),
                    MaterialTheme.colors.surface,
                    menuItems = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    searchState = !searchState
                                },
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_search),
                                    contentDescription = "image",
                                    tint = MaterialTheme.colors.surface,
                                    modifier = Modifier
                                        .width(30.dp)
                                        .height(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(15.dp))
                            IconButton(
                                onClick = {
                                  navigateToDeleteConversations()
                                },
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.delete_outline),
                                    contentDescription = "image",
                                    tint = MaterialTheme.colors.surface,
                                    modifier = Modifier
                                        .width(30.dp)
                                        .height(30.dp)
                                )
                            }
                        }

                    }
                )
            }
            if (isFetching) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.app_icon),
                        contentDescription = null,
                        tint = teal_200,
                        modifier = Modifier
                            .size(150.dp)
                            .rotate(rotation)
                    )

                }
            } else if (conversations.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    //show image if list is empty
//                    Image(
//                        painter = painterResource(id = if (darkMode) R.drawable.empty_list_dark else R.drawable.empty_list),
//                        contentDescription = null,
//                        modifier = Modifier.size(200.dp)
//                    )
                    Text(
                        text = stringResource(R.string.no_history),
                        color = teal_200,
                        style = TextStyle(
                            fontSize = 23.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        modifier = Modifier.padding(top = 50.dp)
                    )
//                    Text(
//                        text = stringResource(R.string.no_history),
//                        color = MaterialTheme.colors.surface,
//                        style = TextStyle(
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.W500,
//                            fontFamily = OpenSans,
//                            lineHeight = 25.sp
//                        ),
//                        modifier = Modifier.padding(top = 15.dp)
//                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .padding(top = 10.dp)
                ) {

                    items(items = conversations.filter {
                        it.title.contains(searchText.value, ignoreCase = true)
                    }, key = { it.id }) { conversation ->

                        val currentItem by rememberUpdatedState(conversation)

                        val dismissState = rememberDismissState()

                        Row(
                            modifier = Modifier
                                .clickable {
                                    navigateToChat("", "", null, currentItem.id)
                                }
                                .padding(vertical = 5.dp, horizontal = 5.dp)
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colors.onSecondary,
                                    RoundedCornerShape(14.dp)
                                )
                                .border(
                                    0.5.dp,
                                    MaterialTheme.colors.onPrimary,
                                    RoundedCornerShape(10.dp)
                                )
                                .padding(vertical = 10.dp, horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = currentItem.title,
                                    color = MaterialTheme.colors.surface,
                                    maxLines = 3,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.W700,
                                        fontFamily = OpenSans,
                                        lineHeight = 25.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(8.dp))
//                                Divider(
//                                    color = MaterialTheme.colors.secondary, thickness = 1.5.dp,
//                                )
                                Text(
                                    text = currentItem.createdAt.toFormattedDate(),
                                    color = MaterialTheme.colors.onSurface,
                                    style = TextStyle(
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.W600,
                                        fontFamily = OpenSans,
                                        lineHeight = 25.sp,
                                    ),
                                )
                            }

                            // Clickable composable for delete icon
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        // Handle delete functionality here
                                        historyViewModel.deleteConversation(conversation.id)
                                    }
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.delete_filled),
                                    tint = Color.White,
                                    contentDescription = "Delete Icon",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}
