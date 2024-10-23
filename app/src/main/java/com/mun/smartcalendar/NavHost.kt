package com.mun.smartcalendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavHost(modifier: Modifier = Modifier, navController: NavHostController) {

    //NavHost Setting
    NavHost(navController = navController, startDestination = Destination.CALENDAR.name) {
        //Define each screen as composable
        composable(route = Destination.CALENDAR.name) { CalendarScreen(navController) }

        composable(route = Destination.TEST.name) { TestScreen(navController) }

        composable(route = Destination.TEST2.name) { TestScreen2(navController) }
    }
}

enum class Destination(name: String) {
    CALENDAR("calendar"),
    TEST("test"),
    TEST2("test2")

}