package com.innovativetools.ai.myaiassistant.navigation
import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.innovativetools.ai.myaiassistant.utils.Constants
import com.innovativetools.ai.myaiassistant.utils.Constants.TRANSITION_ANIMATION_DURATION
import com.innovativetools.ai.myaiassistant.ui.myassistants.MyAssistantsScreen
import com.innovativetools.ai.myaiassistant.ui.assistantchat.AssistantChatScreen
import com.innovativetools.ai.myaiassistant.ui.history.HistoryScreen
import com.innovativetools.ai.myaiassistant.ui.language.LanguageScreen
import com.innovativetools.ai.myaiassistant.ui.settings.SettingsScreen
import com.innovativetools.ai.myaiassistant.ui.splash.SplashScreen
import com.innovativetools.ai.myaiassistant.ui.chat.ChatScreen
import com.innovativetools.ai.myaiassistant.ui.history.ClearHistoryDialog
import com.innovativetools.ai.myaiassistant.ui.upgrade.PurchaseHelper
import com.innovativetools.ai.myaiassistant.ui.upgrade.UpgradeScreen

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialNavigationApi::class)
@ExperimentalAnimationApi
@Composable
fun NavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    darkMode: MutableState<Boolean>,
    purchaseHelper: PurchaseHelper
) {
    val paddingBottom =
        animateDpAsState(
            if (bottomBarState.value) 55.dp else 0.dp,
            animationSpec = tween(TRANSITION_ANIMATION_DURATION)
        )
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(bottom = paddingBottom.value)
    ) {

        bottomSheet(route = Screen.DeleteHistory.route) {
            ClearHistoryDialog(onConfirmClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(Constants.IS_DELETE, true)
                navController.popBackStack()
            }, onCancelClick = {
                navController.popBackStack()
            })
        }

//        bottomSheet(route = Screen.AiAssistants.route) {
//            RatingBottomSheet(
//                onCancelClick = { /* handle cancel click */ },
//                onRatingClick = { /* handle rate click with the rating value */ }
//            )
//        }

//        bottomSheet(route = Screen.Logout.route) {
//            LogoutBottomSheet(onConfirmClick = {
//                navController.navigate(Screen.Splash.route) {
//                    popUpTo(Screen.Splash.route) {
//                        inclusive = true
//                    }
//                }
//            }, onCancelClick = {
//                navController.popBackStack()
//            })
//        }


        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen(
                navigateToAssistants = {
                    navController.navigate(Screen.AiAssistants.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        composable(
            route = Screen.Chat.route,
        ) {
            var exampleList: List<String> = emptyList()
            if (it.arguments?.getString("examples") != "null") {
                exampleList =
                    it.arguments?.getString("examples")?.split("|")?.toTypedArray()?.toList()
                        ?: emptyList()
            }
            ChatScreen(
                navigateToBack = {
                    navController.popBackStack()
                     bottomBarState.value = true
                },
                navigateToUpgrade = {
                    navController.navigate(Screen.Upgrade.route)
                },
                examples = exampleList
            )
        }


        composable(
            route = "${Screen.AssistantChat.route}?name={name}&role={role}&instruction={instruction}&examples={examples}&id={id}",
        )
        {
            var exampleList1: List<String> = emptyList()
            var instruction: String = ""
            if(it.arguments?.getString("instruction") != "null"){
                instruction = it.arguments?.getString("instruction").toString()
            }
            if (it.arguments?.getString("examples") != "null") {
                exampleList1 =
                    it.arguments?.getString("examples")?.split("|")?.toTypedArray()?.toList()
                        ?: emptyList()
            }
            AssistantChatScreen(
                navigateToBack = {
                    if(!navController.popBackStack()){
                        navController.navigate(route = Screen.AiAssistants.route)
                    }

                },
                navigateToUpgrade = {
                    navController.navigate(Screen.Upgrade.route)
                },
                it.arguments?.getString("name"),
                instruction,
                exampleList1,
//              exampleList2
            )
        }


        composable(
            route = Screen.AiAssistants.route,
        ) {
           MyAssistantsScreen(
                navigateToChat = { name, role,instruction,exampleList1 ->
                     val exampleLst1 = exampleList1.joinToString(separator = "|")
                    navController.navigate("${Screen.AssistantChat.route}?name=$name&role=$role&instruction=$instruction&examples=$exampleLst1")
                 },
                navigateToUpgrade = {
                    navController.navigate(Screen.Upgrade.route)
                },
            )
        }

        composable(route = Screen.History.route){
            val screenResult = navController.currentBackStackEntry
                ?.savedStateHandle
            HistoryScreen(
                navigateToBack = {
                    if(!navController.popBackStack()){
                        navController.navigate(route = Screen.Settings.route)
                    }                },
                navigateToChat = { name, role, examples, id ->
                    val examplesString = examples?.joinToString(separator = "|")
                    navController.navigate("${Screen.AssistantChat.route}?name=$name&role=$role&examples=$examplesString&id=$id")
                },
                navigateToDeleteConversations = {
                    navController.navigate(Screen.DeleteHistory.route)
                },
                savedStateHandle = screenResult
            )
        }

        composable(
            route = Screen.Settings.route,)
        {
            SettingsScreen(
                darkMode = darkMode,
                navigateToLanguages = {
                    navController.navigate(Screen.Languages.route)
                },
                navigateToHistory = {
                    navController.navigate(Screen.History.route)
                },
                navigateToUpgrade = {
                    navController.navigate(Screen.Upgrade.route)
                },
                purchaseHelper = purchaseHelper
            )
        }

        composable(
            route = Screen.Languages.route,
        ) {
            LanguageScreen(
                navigateToBack = {
                    if(!navController.popBackStack()){
                        navController.navigate(route = Screen.Settings.route)
                    }
                }
            )
        }

        composable(
            route = Screen.Upgrade.route,) {
            UpgradeScreen(
                purchaseHelper,
                navigateToBack = {
                    if(!navController.popBackStack()){
                        navController.navigate(route = Screen.Settings.route)
                    }
                }
            )
        }
    }
}
