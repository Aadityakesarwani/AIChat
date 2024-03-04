package com.innovativetools.ai.myaiassistant.ui.language

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.innovativetools.ai.myaiassistant.utils.click
import com.innovativetools.ai.myaiassistant.components.MyAppBar
import com.innovativetools.ai.myaiassistant.data.model.LanguageModel
import com.innovativetools.ai.myaiassistant.ui.theme.OpenSans
import java.util.*
import com.innovativetools.ai.myaiassistant.R
import com.innovativetools.ai.myaiassistant.ui.theme.teal_200

@Composable
fun LanguageScreen(
    navigateToBack: () -> Unit,
    languageViewModel: LanguageViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val languageList: List<LanguageModel> = listOf(
        LanguageModel(
            name = "English", code = "en", image = R.drawable.us
        ),
        LanguageModel(
            name = "Arabic (العربية)", code = "ar", image = R.drawable.ar
        ),
        LanguageModel(
            name = "Germany (Deutsch)", code = "de", image = R.drawable.de
        ),
        LanguageModel(
            name = "Hindi (हिन्दी)", code = "hi", image = R.drawable.ind
        ),

        LanguageModel(
            name = "Italian (Italiano)", code = "it", image = R.drawable.it
        ),
        LanguageModel(
            name = "Japanese (日本語) ", code = "ja", image = R.drawable.jp
        ),
        LanguageModel(
            name = "Korean (한국어)", code = "ko", image = R.drawable.kr
        ),
        LanguageModel(
            name = "Marathi (मराठी)", code = "mr", image = R.drawable.ind
        ),
        LanguageModel(
            name = "Norwegian (Norsk)", code = "no", image = R.drawable.no
        ),
        LanguageModel(
            name = "Romanian (Română)", code = "ro", image = R.drawable.md
        ),
        LanguageModel(
            name = "Swedish (Svenska)", code = "sv", image = R.drawable.se
        )

    )

    LaunchedEffect(true) {
        languageViewModel.getCurrentLanguage()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        MyAppBar(
            onClickAction = navigateToBack,
            image = R.drawable.arrow_left,
            text = stringResource(R.string.language),
            MaterialTheme.colors.surface
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            items(languageList) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colors.onSecondary,
                            RoundedCornerShape(14.dp)
                        )
                        .padding(10.dp)
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                        .click {
                            languageViewModel.selectedValue.value = it.code
                            languageViewModel.setCurrentLanguage(it.code, it.name)
                            changeLanguage(context, it.code)
                            navigateToBack()
                        },
                ) {
                    if (languageViewModel.selectedValue.value == it.code) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_circle),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.CenterVertically)
                                .background(
                                    color = teal_200,
                                    shape = RoundedCornerShape(90.dp)
                                )
                                .padding(5.dp)
                        )
                    }else {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_circle),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .size(25.dp)
                        )
                    }

                    Text(
                        text = it.name,
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .align(Alignment.CenterVertically)
                            .weight(1f),
                    )

                    Icon(
                        painter = painterResource(id = it.image),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }

                Divider(
                    color = MaterialTheme.colors.secondary,
                    thickness = 1.dp,
                )
            }
        }
    }
}
fun changeLanguage(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}