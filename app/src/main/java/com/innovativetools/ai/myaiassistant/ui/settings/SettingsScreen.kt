package com.innovativetools.ai.myaiassistant.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.innovativetools.ai.myaiassistant.R
import com.innovativetools.ai.myaiassistant.utils.Constants
import com.innovativetools.ai.myaiassistant.utils.click
import com.innovativetools.ai.myaiassistant.components.MainAppBar
import com.innovativetools.ai.myaiassistant.components.ProButton
import com.innovativetools.ai.myaiassistant.ui.theme.OpenSans
import com.innovativetools.ai.myaiassistant.ui.theme.teal_200
import com.innovativetools.ai.myaiassistant.ui.upgrade.PurchaseHelper
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SettingsScreen(
    darkMode: MutableState<Boolean>,
    navigateToLanguages: () -> Unit,
    navigateToHistory: () -> Unit,
    navigateToUpgrade: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    purchaseHelper: PurchaseHelper
) {

    val context = LocalContext.current

    val uriHandler = LocalUriHandler.current

    var showSuccessToast by remember {
        mutableStateOf(false)
    }

    var showErrorToast by remember {
        mutableStateOf(false)
    }

    if (showSuccessToast) {
        Toasty.success(context, context.resources.getString(R.string.welcome_to_pro_version), Toast.LENGTH_LONG, true).show();
        showSuccessToast = false
    }

    if (showErrorToast) {
        Toasty.error(context, context.resources.getString(R.string.pro_not_purchased), Toast.LENGTH_SHORT, true).show();
        showErrorToast = false
    }

    val shareAppEvent by settingsViewModel.shareAppEvent.collectAsState()


    if (shareAppEvent) {
        LaunchedEffect(true) {
            GlobalScope.launch(Dispatchers.Main) {
                shareApp(context)
                settingsViewModel.onShareAppHandled()
            }
        }
    }


    LaunchedEffect(Unit) {
        settingsViewModel.getProVersion()
        settingsViewModel.getCurrentLanguage()
    }

    val language by settingsViewModel.currentLanguage.collectAsState()
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
       modifier = Modifier
           .fillMaxSize()
           .verticalScroll(rememberScrollState())

    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

            MainAppBar(
                onClickAction = {},
                image = R.drawable.app_icon,
                text = stringResource(R.string.settings),
                tint = Color.Unspecified
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                if (settingsViewModel.isProVersion.value == true) {
                    Image(
                        painter = painterResource(R.drawable.pro_icon), // Replace with the actual resource ID
                        contentDescription = "Pro Icon",
                        colorFilter = ColorFilter.tint(teal_200),
                        modifier = Modifier
                            .size(40.dp, 40.dp)
                            .padding(end = 12.dp)
                            .graphicsLayer(
                                rotationY = rotationAngle,
                            )
                    )
                }
            }
        }

        if (settingsViewModel.isProVersion.value.not()) {
            ProButton(onClick = { navigateToUpgrade() })
        }

        //App features
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(id = R.string.app_features),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width(10.dp))
            Divider(
                color = MaterialTheme.colors.secondary, thickness = 1.5.dp,
            )
        }


        //language
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 20.dp)
                .click {
                    navigateToLanguages()
                },
        verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_language),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.language),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = language,
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                )
            )
            Spacer(modifier = Modifier.width(15.dp))
        }


        //history
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 20.dp)
                .click {
                    navigateToHistory()
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                painter = painterResource(R.drawable.ic_history),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.history),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
        }

        //Theme mode
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 15.dp)
//                .padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                painter = painterResource(R.drawable.ic_dark_mode),
//                tint = MaterialTheme.colors.surface,
//                contentDescription = stringResource(R.string.app_name),
//                modifier = Modifier
//                    .size(width = 27.dp, height = 27.dp)
//
//            )
//            Spacer(modifier = Modifier.width(20.dp))
//            Text(
//                text = stringResource(id = R.string.dark_theme),
//                color = MaterialTheme.colors.surface,
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.W600,
//                    fontFamily = OpenSans,
//                    lineHeight = 25.sp
//                ),
//                modifier = Modifier.weight(1f)
//            )
//            Spacer(modifier = Modifier.width(20.dp))
//            ThemeSwitch(darkMode)
//        }

        //Manage subsription

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(id = R.string.mng_subscription),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width(10.dp))
            Divider(
                color = MaterialTheme.colors.secondary, thickness = 1.5.dp,
            )
        }

        //Restore Purchase
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 30.dp)
                .click {
                    purchaseHelper.restorePurchase {
                        if (it) {
                            settingsViewModel.setProVersion(true)
                            showSuccessToast = true
                        } else {
                            showErrorToast = true
                        }
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_store),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.restore_purchase),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
        }



//help and support
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(id = R.string.help_support),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width(10.dp))
            Divider(
                color = MaterialTheme.colors.secondary, thickness = 1.5.dp,
            )
        }


        //Privacy Policy
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 16.dp)
                .click {
                    uriHandler.openUri(Constants.PRIVACY_POLICY)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_privacy),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.privacy_policy),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))

        }



        //share app
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 16.dp)
                .click { settingsViewModel.requestShareApp() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_share),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.share_app),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
        }


        //Rate app
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 16.dp)
                .click {
                    rateApp(context)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_rateus),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.rate_us),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
        }

        //Feedback
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 16.dp)
                .click {
                    val businessEmail = "innovativetuls@gmail.com"
                    val subject = "App Feedback"
                    sendFeedbackEmail(context, businessEmail, subject)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.feedback),
                tint = MaterialTheme.colors.surface,
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(width = 27.dp, height = 27.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.feedback),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(15.dp))
        }

    }
}


private fun shareApp(context: Context) {
    val appPackageName = context.packageName
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(
        Intent.EXTRA_TEXT,
        "Discover the magic of our AI Assistant app! Check out this amazing app: https://play.google.com/store/apps/details?id=$appPackageName"
    )

    MainScope().launch {
        withContext(Dispatchers.Main) {
            context.startActivity(Intent.createChooser(intent, "Share via"))
        }
    }
}

// Function to Rate App
private  fun rateApp(context: Context) {
    val appPackageName = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackageName")
            )
        )
    } catch (e: android.content.ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            )
        )
    }
}

private fun sendFeedbackEmail(context: Context, businessEmail: String, subject: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$businessEmail")
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    intent.resolveActivityInfo(context.packageManager, 0)?.let {
        context.startActivity(intent)
    }
}


//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp)
//                .padding(horizontal = 16.dp)
//                .clickable(
//                    interactionSource = interactionSource,
//                    indication = null,
//                ) { navigateToLogout() },
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                painter = painterResource(R.drawable.logout),
//                tint = Red,
//                contentDescription = stringResource(R.string.app_name),
//                modifier = Modifier
//                    .size(width = 27.dp, height = 27.dp)
//
//            )
//            Spacer(modifier = Modifier.width(20.dp))
//            Text(
//                text = stringResource(id = R.string.logout),
//                color = Red,
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.W600,
//                    fontFamily = OpenSans,
//                    lineHeight = 25.sp
//                ),
//                modifier = Modifier.weight(1f)
//            )
//            Spacer(modifier = Modifier.width(15.dp))
//            Icon(
//                painter = painterResource(id = R.drawable.right),
//                contentDescription = null,
//                tint = Red,
//                modifier = Modifier
//                    .padding(start = 5.dp)
//                    .size(30.dp)
//            )
//        }


//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp)
//                .padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painter = painterResource(R.drawable.food),
//                contentDescription = stringResource(R.string.app_name),
//                modifier = Modifier
//                    .size(width = 80.dp, height = 80.dp)
//                    .background(
//                        shape = RoundedCornerShape(90.dp),
//                        color = MaterialTheme.colors.secondary
//                    )
//                    .padding(10.dp)
//            )
//            Spacer(modifier = Modifier.width(10.dp))
//            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
//                Text(
//                    text = "Murat ÖZTÜRK",
//                    color = MaterialTheme.colors.surface,
//                    style = TextStyle(
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.W700,
//                        fontFamily = OpenSans,
//                        lineHeight = 25.sp
//                    )
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//                Text(
//                    text = "murat318ozturk@gmail.com",
//                    color = MaterialTheme.colors.onSurface,
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.W600,
//                        fontFamily = OpenSans,
//                        lineHeight = 25.sp
//                    )
//                )
//            }
//            Spacer(modifier = Modifier.width(10.dp))
//            Icon(
//                painter = painterResource(id = R.drawable.right),
//                contentDescription = null,
//                tint = MaterialTheme.colors.surface,
//                modifier = Modifier
//                    .padding(start = 5.dp)
//                    .size(30.dp)
//            )
//        }


//pro version batch
//        if (settingsViewModel.isProVersion.value.not()) {
//            UpgradeButton(onClick = { navigateToUpgrade() })
//        }



