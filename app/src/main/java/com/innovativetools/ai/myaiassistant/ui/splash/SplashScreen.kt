package com.innovativetools.ai.myaiassistant.ui.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.innovativetools.ai.myaiassistant.R
import kotlinx.coroutines.delay
import kotlin.Float
import kotlin.Unit


@Composable
fun SplashScreen(
    navigateToAssistants: () -> Unit,
) {
    var startAnimation by remember { mutableStateOf(false) }
    val scaleAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 1.2f,
        animationSpec = tween(
            durationMillis = 750
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(800)
        navigateToAssistants()
    }
    SplashDesign(scale = scaleAnimation.value)
}


@Composable
fun SplashDesign(scale: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(300.dp)
                .graphicsLayer(scaleX = scale, scaleY = scale),
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = stringResource(R.string.app_icon),
        )
    }
}





//    val db = FirebaseFirestore.getInstance()
//    db.collection("config").document("QfDamQodHRQMW4g2L4E4")
//        .addSnapshotListener(object : EventListener<DocumentSnapshot?> {
//            override fun onEvent(
//                @Nullable value: DocumentSnapshot?,
//                @Nullable error: FirebaseFirestoreException?
//            ) {
//                if (error != null) {
//                    Log.w("FIREBASEERROR", "Listen failed.", error)
//                    return
//                }
//                if (value != null) {
//                    val apiKey = value.getString("apiKey")
//                    val rewardedAdUnitId = value.getString("rewardedAdUnitId")
//                    if (apiKey != null) {
//                        Constants.API_KEY = apiKey
//                    }
//                    if (rewardedAdUnitId != null) {
//                        Constants.REWARDED_AD_UNIT_ID = rewardedAdUnitId
//                    }
//
//                }else{
//                    Log.d("nullvalue","value is  null")
//                }
//            }
//        })


