package com.mun.smartcalendar

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    val context: Context = LocalContext.current
    //NavHost Setting
    NavHost(navController = navController, startDestination = Destination.CALENDAR.name) {
        //Define each screen as composable
        composable(route = Destination.CALENDAR.name) { CalendarScreen(context, navController) }

        composable(route = Destination.TEST.name) { TestScreen(context, navController) }

        composable(route = Destination.TEST2.name) { TestScreen2(context, navController) }
    }
}

enum class Destination(name: String) {
    CALENDAR("calendar"),
    TEST("test"),
    TEST2("test2")

}