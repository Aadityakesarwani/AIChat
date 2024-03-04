package com.innovativetools.ai.myaiassistant.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.messaging.FirebaseMessaging
import com.innovativetools.ai.myaiassistant.utils.loadRewarded
import com.innovativetools.ai.myaiassistant.navigation.BottomNavigationBar
import com.innovativetools.ai.myaiassistant.navigation.NavGraph
import com.innovativetools.ai.myaiassistant.navigation.Screen
import com.innovativetools.ai.myaiassistant.ui.theme.MyAiAssistantTheme
import com.innovativetools.ai.myaiassistant.ui.upgrade.PurchaseHelper
import com.innovativetools.ai.myaiassistant.utils.Constants
import com.innovativetools.ai.myaiassistant.utils.Constants.MY_REQUEST_CODE
import com.innovativetools.ai.myaiassistant.utils.loadInterstitial
import com.innovativetools.ai.myaiassistant.utils.loadTwoRewardedAds
import com.innovativetools.ai.myaiassistant.utils.removeInterstitial
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.Nullable
import com.suddenh4x.ratingdialog.AppRating
import com.suddenh4x.ratingdialog.preferences.RatingThreshold
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    val CHANNEL_ID = "MyAiAssistant"
    val CHANNEL_NAME = "MyAiAssistant"
    val NOTIF_ID = 0

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {

            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalMaterialNavigationApi::class
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        checkForAppUpdate()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

//        viewModel.getCurrentLanguageCode()
//        val currentLanguageCode = viewModel._currentLanguageCode.value
//        changeLanguage(this,currentLanguageCode)


        try {
            val db = FirebaseFirestore.getInstance()
            db.collection("config").document("QfDamQodHRQMW4g2L4E4")
                .addSnapshotListener(object : EventListener<DocumentSnapshot?> {
                    @SuppressLint("SuspiciousIndentation")
                    override fun onEvent(
                        @Nullable value: DocumentSnapshot?,
                        @Nullable error: FirebaseFirestoreException?
                    ) {
                        if (error != null) {
                            Log.w("FIREBASEERROR", "Listen failed.", error)
                            return
                        }
                        if (value != null) {
                            val apiKey = value.getString("apiKey")
                            val rewardedAdUnitId = value.getString("rewardedAdUnitId")
                            val secondRewardAdUnitId = value.getString("secondReward")
                            val intertitialAdUnitId = value.getString("intertitialAdUnitId")
                            val isIntEnabled = value.getBoolean("isIntEnabled")

                            runOnUiThread {
                                if (apiKey != null) {
                                    Constants.API_KEY = apiKey
                                }
                                if (rewardedAdUnitId != null) {
                                  Constants.REWARDED_AD_UNIT_ID = rewardedAdUnitId
                                  Constants.SECOND_REWARDED_AD_UNIT_ID = secondRewardAdUnitId!!
//                                Constants.REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
                                    loadRewarded(this@MainActivity)
                                    loadTwoRewardedAds(this@MainActivity)
                                }
                                if(isIntEnabled == true){
                                    Constants.isInterEnabled = true
                                    if (intertitialAdUnitId != null) {
                                        Constants.INTERTITIAL_ADUNIT = intertitialAdUnitId //"ca-app-pub-3940256099942544/1033173712"
                                        loadInterstitial(this@MainActivity)
                                    }
                                }
                            }
                        } else {
                            Log.d("nullvalue", "value is null")
                        }
                    }
                })

        }catch (e : Exception ){
            Log.e("FirestoreException", e.toString())
        }

        val purchaseHelper = PurchaseHelper(this)
        purchaseHelper.billingSetup()

        FirebaseMessaging.getInstance().subscribeToTopic("all").addOnSuccessListener {}
        askNotificationPermission()
        createNotificationChannel()


        AppRating.Builder(this)
            .useGoogleInAppReview()
            .setMinimumLaunchTimes(5)
            .setMinimumDays(2)
            .setMinimumLaunchTimesToShowAgain(5)
            .setMinimumDaysToShowAgain(7)
            .setRatingThreshold(RatingThreshold.FOUR)
            .showIfMeetsConditions()


        setContent {

            val darkThemeCurrent by viewModel.darkMode.collectAsState()
            val darkTheme = remember { mutableStateOf(darkThemeCurrent) }
//          MyAiAssistantTheme(darkTheme = darkTheme.value) {
            MyAiAssistantTheme(darkTheme = true) {
                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val navController = rememberAnimatedNavController(bottomSheetNavigator)
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                when (navBackStackEntry?.destination?.route) {
                    Screen.Upgrade.route -> bottomBarState.value = false
                    Screen.Splash.route -> bottomBarState.value = false
                    "${Screen.AssistantChat.route}?name={name}&role={role}&examples={examples}&id={id}" -> bottomBarState.value = false
                    null -> bottomBarState.value = false
                    else -> bottomBarState.value = true
                }

                ModalBottomSheetLayout(
                    bottomSheetNavigator = bottomSheetNavigator,
                    sheetShape = RoundedCornerShape(
                        topStart = 35.dp,
                        topEnd = 35.dp
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .fillMaxSize()
                            .navigationBarsPadding()
                            .captionBarPadding()
                            .imePadding()
                            .statusBarsPadding(),
                    )

                    {
                        NavGraph(
                            navController = navController,
                            bottomBarState,
                            darkMode = darkTheme,
                            purchaseHelper
                        )

                        Column(
                            Modifier
                                .fillMaxHeight(),
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            BottomNavigationBar(navController, bottomBarState)
                        }


                    }

                }
            }

        }
    }

    override fun onDestroy() {
        removeInterstitial()
        super.onDestroy()
    }

    private fun checkForAppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    this,
                    MY_REQUEST_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                checkForAppUpdate()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getCurrentLanguageCode()
        val currentLanguageCode = viewModel._currentLanguageCode.value
        changeLanguage(this,currentLanguageCode)

        val appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.FLEXIBLE,
                        this,
                        MY_REQUEST_CODE
                    )
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


//    fun changeLanguage(context: Context, language: String) {
//        val locale = Locale(language)
//        val config = Configuration(context.resources.configuration)
//        config.setLocale(locale)
//
//        val localizedContext = context.createConfigurationContext(config)
//        context.resources = localizedContext.resources
//    }

}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }

    return false
}
