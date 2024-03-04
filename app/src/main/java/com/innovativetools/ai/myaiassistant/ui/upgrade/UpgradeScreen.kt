package com.innovativetools.ai.myaiassistant.ui.upgrade

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.innovativetools.ai.myaiassistant.R
import com.innovativetools.ai.myaiassistant.utils.ProTypeEnum
import com.innovativetools.ai.myaiassistant.utils.click
import com.innovativetools.ai.myaiassistant.ui.theme.GrayShadow
import com.innovativetools.ai.myaiassistant.ui.theme.teal_200
import com.innovativetools.ai.myaiassistant.ui.theme.OpenSans
import com.innovativetools.ai.myaiassistant.utils.Constants
import com.valentinilk.shimmer.defaultShimmerTheme
import es.dmoral.toasty.Toasty

@Composable
fun UpgradeScreen(
    purchaseHelper: PurchaseHelper,
    navigateToBack: () -> Unit = {},
    viewModel: UpgradeViewModel = hiltViewModel()
) {

    val proWeeklyPrice by purchaseHelper.proWeeklyPrice.collectAsState("")
//  val proMonthlyPrice by purchaseHelper.proMonthlyPrice.collectAsState("")
    val proYearlyPrice by purchaseHelper.proYearlyPrice.collectAsState("")
    val purchased by purchaseHelper.purchased.collectAsState()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

//    Toasty.success(
//        context,
//        "Current model: ${viewModel.getGptModelUseCase.invoke().model}",
//        Toast.LENGTH_LONG, true).show();

    LaunchedEffect(purchased)
    {
        if (purchased) {
            viewModel.setProVersion(true)

//            Toasty.success(
//                context,
//                "Current model: ${viewModel.getGptModelUseCase.invoke().model}",
//                Toast.LENGTH_LONG, true).show();

            Toasty.success(
                context,
                context.resources.getString(R.string.welcome_to_pro_version),
                Toast.LENGTH_LONG, true).show();
            navigateToBack()
        }
    }


    var showSuccessToast by remember {
        mutableStateOf(false)
    }


    var showErrorToast by remember {
        mutableStateOf(false)
    }


    if (showSuccessToast) {
        Toasty.success(
            context,
            context.resources.getString(R.string.welcome_to_pro_version),
            Toast.LENGTH_LONG, true).show();
        showSuccessToast = false
    }


    if (showErrorToast) {
        Toasty.error(
            context,
            context.resources.getString(R.string.pro_not_purchased),
            Toast.LENGTH_SHORT, true).show();
        showErrorToast = false
    }

    val creditCardTheme = defaultShimmerTheme.copy(
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 1_000,
                easing = LinearEasing,
            ),
        ),
        blendMode = BlendMode.Hardlight,
        rotation = 25f,
        shaderColors = listOf(
            Color.White.copy(alpha = 0.0f),
            Color.White.copy(alpha = 0.4f),
            Color.White.copy(alpha = 0.0f),
        ),
        shaderColorStops = null,
        shimmerWidth = 150.dp,
    )

    BackHandler(true) {}


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())  // Add this line
            .padding(10.dp)
            .padding(end = 10.dp)
            .background(color = MaterialTheme.colors.background),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = null,
                tint = MaterialTheme.colors.surface,
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = GrayShadow,
                        shape = RoundedCornerShape(90.dp)
                    )
                    .padding(5.dp)
                    .click { navigateToBack() }
            )
//            Text(
//                text = stringResource(R.string.restore),
//                color = MaterialTheme.colors.surface,
//                style = TextStyle(
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.W600,
//                    fontFamily = OpenSans,
//                    lineHeight = 25.sp
//                ),
//                modifier = Modifier
//                    .background(
//                        color = GrayShadow,
//                        shape = RoundedCornerShape(90.dp)
//                    )
//                    .padding(vertical = 5.dp, horizontal = 10.dp)
//                    .click {
//                        purchaseHelper.restorePurchase {
//                            if (it.not()) {
//                                showErrorToast = true
//                            }
//                        }
//                    }
//            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp)
                .background(color = MaterialTheme.colors.background),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.surface,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans
                        )
                    ) {
                        append(stringResource(R.string.unlock))
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.primary,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans
                        )
                    ) {
                        append(stringResource(R.string.limitless))
                    }
                },
                modifier = Modifier
                    .padding(vertical = 3.dp)
            )

            Text(
                text = stringResource(R.string.convo_with),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp
                ),
                modifier = Modifier
            )

            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.primary,
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans
                ),
                modifier = Modifier
                    .padding(vertical = 3.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.powered_by),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OpenSans,
                    lineHeight = 25.sp,
                ),
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.primaryVariant,
                        shape = RoundedCornerShape(20),
                    )
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            )

            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {

                //feature 1
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.feature1),
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Justify,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.feature2),
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Justify
                    )
                }


                //feature 3

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.feature3),
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Justify,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.feature4),
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Justify,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.feature5),
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Justify,
                    )
                }
            }

            var selectedPlan by remember { mutableStateOf(ProTypeEnum.YEARLY) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .clickable {
                            selectedPlan = ProTypeEnum.WEEKLY
                        }
                        .weight(1f)
                        .background(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colors.onBackground
                        )
                        .border(
                            1.dp,
                            if (selectedPlan == ProTypeEnum.WEEKLY) {
                                MaterialTheme.colors.primary
                            } else {
                                MaterialTheme.colors.onBackground
                            },
                            shape = RoundedCornerShape(16.dp)
                        ),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.weekly),
                        color = MaterialTheme.colors.primary,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(15.dp)
                    )

                    Divider(
                        color = MaterialTheme.colors.primary, thickness = 1.dp
                    )
                    Text(
                        text = proWeeklyPrice,
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(15.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.per_week),
                        color = MaterialTheme.colors.onSurface,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 15.dp)

                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier
                        .clickable {
                            selectedPlan = ProTypeEnum.YEARLY
                        }
                        .weight(1f)
                        .background(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colors.onBackground
                        )

                        .border(
                            1.dp,
                            if (selectedPlan == ProTypeEnum.YEARLY) {
                                MaterialTheme.colors.primary
                            } else {
                                MaterialTheme.colors.onBackground
                            },
                            shape = RoundedCornerShape(16.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Added the "Save 85%" feature
                    Text(
                        text = stringResource(R.string.save85),
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(
                                color = teal_200,
                                shape = RoundedCornerShape(
                                    topEnd = 16.dp,
                                    topStart = 0.dp,
                                    bottomEnd = 0.dp,
                                    bottomStart = 10.dp
                                )
                            )
                            .padding(vertical = 3.dp, horizontal = 10.dp)
                            .align(Alignment.End)
                    )

                    Text(
                        text = stringResource(R.string.yearly),
                        color = MaterialTheme.colors.primary,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier =  Modifier
                            .padding(15.dp)
                    )
                    Divider(
                        color = MaterialTheme.colors.primary,
                        thickness = 1.dp,
//                      modifier = Modifier.padding(vertical = 5.dp)
                    )

                    Text(
                        text = proYearlyPrice,
                        color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(15.dp)
                            .padding(bottom = 5.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.per_year),
                        color = MaterialTheme.colors.onSurface,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = OpenSans,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )

                }
            }


// Continue button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (selectedPlan == ProTypeEnum.WEEKLY) {
                            purchaseHelper.makePurchase(ProTypeEnum.WEEKLY)
                        } else if (selectedPlan == ProTypeEnum.YEARLY) {
                            purchaseHelper.makePurchase(ProTypeEnum.YEARLY)
                        }
                    }
                    .padding(horizontal = 10.dp, vertical = 20.dp)
                    .height(50.dp)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(50.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Continue",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = OpenSans
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp) // Adjust vertical padding as needed
                )
            }

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append(stringResource(id = R.string.by_subscribing))
                    }
                    withStyle(style = SpanStyle(color = MaterialTheme.colors.primary, textDecoration = TextDecoration.Underline)) {
                        append(stringResource(id = R.string.privacy_policy))
                    }
                },
                color = MaterialTheme.colors.onSurface,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        uriHandler.openUri(Constants.PRIVACY_POLICY)
                    }
                    .padding(top = 5.dp)
            )

            // Additional message about subscriptions
            Text(
          stringResource(id = R.string.billing_message).trimIndent(),
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
            )

        }
    }
}







//                Spacer(modifier = Modifier.height(10.dp))
//
//                CompositionLocalProvider(
//                    LocalShimmerTheme provides creditCardTheme
//                ) {
//
//                    Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.click {
//                        purchaseHelper.makePurchase(ProVersionTypeEnum.YEARLY)
//
//                    }) {
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .border(
//                                    2.dp,
//                                    color = MaterialTheme.colors.primary,
//                                    shape = RoundedCornerShape(16.dp)
//                                ),
//                            backgroundColor = MaterialTheme.colors.onSecondary,
//                            shape = RoundedCornerShape(16.dp),
//
//                            ) {
//                            Column(
//                                modifier = Modifier.shimmer(),
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            )
//                            {
//
//
//                                Text(
//                                    text = stringResource(R.string.yearly),
//                                    color = MaterialTheme.colors.primary,
//                                    style = TextStyle(
//                                        fontSize = 16.sp,
//                                        fontWeight = FontWeight.W600,
//                                        fontFamily = OpenSans,
//                                        lineHeight = 25.sp
//                                    ),
//                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier.padding(15.dp)
//                                )
//
//                                Divider(
//                                    color = MaterialTheme.colors.secondary, thickness = 1.dp
//                                )
//                                Text(
//                                    text = proYearlyPrice,
//                                    color = MaterialTheme.colors.surface,
//                                    style = TextStyle(
//                                        fontSize = 16.sp,
//                                        fontWeight = FontWeight.W600,
//                                        fontFamily = OpenSans,
//                                        lineHeight = 25.sp
//                                    ),
//                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier
//                                        .padding(15.dp)
//
//                                )
//
//                                Text(
//                                    text = stringResource(id = R.string.per_year),
//                                    color = MaterialTheme.colors.onSurface,
//                                    style = TextStyle(
//                                        fontSize = 12.sp,
//                                        fontWeight = FontWeight.W500,
//                                        fontFamily = OpenSans,
//                                        lineHeight = 25.sp
//                                    ),
//                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier
//                                        .padding(bottom = 15.dp)
//                                )
//                            }
//                        }

//                        Text(
//                            text = stringResource(R.string.save85),
//                            color = MaterialTheme.colors.surface,
//                            style = TextStyle(
//                                fontSize = 12.sp,
//                                fontWeight = FontWeight.W600,
//                                fontFamily = OpenSans,
//                                lineHeight = 25.sp
//                            ),
//
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .background(
//                                    color = teal_200,
//                                    shape = RoundedCornerShape(
//                                        topEnd = 16.dp,
//                                        topStart = 0.dp,
//                                        bottomEnd = 0.dp,
//                                        bottomStart = 10.dp
//                                    )
//                                )
//                                .padding(vertical = 5.dp, horizontal = 10.dp)
//                        )
//                    }
//                }


//    Column(
//        Modifier.padding(20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//
//        Text(
//            productName,
//            Modifier.padding(20.dp),
//            fontSize = 30.sp
//        )
//
//        Text(statusText)
//
//        Row(Modifier.padding(20.dp)) {
//
//            Button(
//                onClick = { purchaseHelper.makePurchase() },
//                Modifier.padding(20.dp),
//                enabled = buyEnabled
//            ) {
//                Text("Purchase")
//            }
//
//            Button(
//                onClick = { purchaseHelper.consumePurchase() },
//                Modifier.padding(20.dp),
//                enabled = consumeEnabled
//            ) {
//                Text("Consume")
//            }
//        }
//    }
