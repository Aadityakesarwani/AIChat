package com.innovativetools.ai.myaiassistant.components

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.innovativetools.ai.myaiassistant.R
import com.innovativetools.ai.myaiassistant.ui.activity.isOnline
import com.innovativetools.ai.myaiassistant.ui.chat.ChatViewModel
import com.innovativetools.ai.myaiassistant.ui.theme.GreenShadow
import com.innovativetools.ai.myaiassistant.ui.theme.teal_200
import com.innovativetools.ai.myaiassistant.ui.theme.OpenSans
import com.innovativetools.ai.myaiassistant.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun ChatInputText(
    viewModel: ChatViewModel = hiltViewModel(),
    inputText: MutableState<String>,
) {

    val focusRequester = remember { FocusRequester() }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()


//    LaunchedEffect(Unit) {
//        delay(200)  // This delay is crucial
//        focusRequester.requestFocus()
//    }

    val context = LocalContext.current
    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        NoInternetDialog {
            showDialog = false
        }
    }


    val isGenerating by viewModel.isGenerating.collectAsState()
    val freeMessageCount by viewModel.freeMessageCount.collectAsState()

    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var hasFocus by remember { mutableStateOf(false) }
    text = text.copy(text = inputText.value)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            inputText.value = "${inputText.value} ${result?.get(0).toString()}"
        }
    }

    Box(
        modifier = Modifier
            .imePadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colors.background),
    ) {

        LaunchedEffect(imeState.value){
              scrollState.scrollTo(scrollState.maxValue)
        }

        Column {
                Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp,)
                Box(
                    Modifier
                        .padding(horizontal = 4.dp)
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Row(Modifier.padding(all = 5.dp), verticalAlignment = Alignment.Bottom) {
                            OutlinedTextField(
                                value = text,
                                onValueChange = {
                                    inputText.value = it.text
                                    text = it
                                },
                                label = null,
                                placeholder = {
                                    Text(
                                        stringResource(R.string.how_can_assist),
                                        fontSize = 16.sp,
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
//                                    .focusRequester(focusRequester)
                                    .fillMaxWidth()
                                    .imePadding()
                                    .defaultMinSize(minHeight = 25.dp)
                                    .heightIn(max = 300.dp)
                                    .padding(end = 10.dp)
                                    .weight(1f)
                                    .border(
                                        1.dp,
                                        Color.Transparent,
                                        RoundedCornerShape(18.dp)
                                    )
                                    .onFocusChanged { focusState ->
                                        hasFocus = focusState.isFocused
                                    },
                                shape = RoundedCornerShape(18.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = MaterialTheme.colors.surface,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    backgroundColor = MaterialTheme.colors.secondary
                                ),
                                trailingIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                                                Toast.makeText(
                                                    context,
                                                    "Speech not Available",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                val intent =
                                                    Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                                                intent.putExtra(
                                                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                                    RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
                                                )
                                                intent.putExtra(
                                                    RecognizerIntent.EXTRA_LANGUAGE,
                                                    Locale.getDefault()
                                                )
                                                intent.putExtra(
                                                    RecognizerIntent.EXTRA_PROMPT,
                                                    "Talk"
                                                )
                                                launcher.launch(intent)
                                            }
                                        }
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_voice),
                                            contentDescription = "Voice input",
                                            modifier = Modifier.size(25.dp),
                                            tint = teal_200
                                        )
                                    }
                                },
                            )

                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (isOnline(context).not()) {
                                            showDialog = true
                                            return@launch
                                        }
                                        if (isGenerating.not()) {
                                            val textClone = text.text
                                            if (textClone.isNotBlank()) {

                                                if (viewModel.isProVersion.value.not()) {

                                                    if (freeMessageCount > 0) {
                                                        viewModel.decreaseFreeMessageCount()
                                                    } else {
                                                        viewModel.showAdsAndProVersion.value = true
                                                        return@launch
                                                    }
                                                }
                                                viewModel.sendMessage(textClone)
                                                text = TextFieldValue("")
                                                inputText.value = ""
                                            }
                                        }
                                    }
                                }, modifier = Modifier
                                    .size(55.dp)
                                    .background(color = White, shape = RoundedCornerShape(90.dp))
//                                .align(CenterVertically)
                            ) {
                                Icon(
                                    painterResource(R.drawable.baseline_send),
                                    "sendMessage",
                                    modifier = Modifier
                                        .size(30.dp),
                                    tint = teal_200,
                                )
                            }

                        }
                    }
                }
            }



        }

