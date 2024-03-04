package com.innovativetools.ai.myaiassistant.navigation

import com.innovativetools.ai.myaiassistant.R


sealed class BottomNavItem(
    val title: Int,
    val icon: Int,
    val icon_filled: Int,
    val route: String
) {


    object AiAssistants : BottomNavItem(
        title = R.string.assistants,
        icon = R.drawable.ic_assistant,
        icon_filled = R.drawable.ic_assistant_filled,
        route = Screen.AiAssistants.route
    )


    object Chat : BottomNavItem(
        title = R.string.chat,
        icon = R.drawable.ic_chat,
        icon_filled = R.drawable.ic_chat_filled,
        route = Screen.Chat.route
    )


//    object History : BottomNavItem(
//        title = R.string.history,
//        icon = R.drawable.time_circle,
//        icon_filled = R.drawable.time_circle_filled,
//        route = Screen.History.route
//    )

    object Settings : BottomNavItem(
        title = R.string.settings,
        icon = R.drawable.setting,
        icon_filled = R.drawable.setting_filled,
        route = Screen.Settings.route
    )
}
