package com.mun.smartcalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mun.smartcalendar.ui.theme.SelectedIcon
import com.mun.smartcalendar.ui.theme.SplashBackGround

@Composable
fun CommonBaseScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar(Modifier.background(SplashBackGround)) {
                IconButton(
                    onClick = { navController.navigate(Destination.CALENDAR.name) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    } },
                    Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null,
                        tint = if (currentRoute == Destination.CALENDAR.name) SelectedIcon else Color.White
                    )
                }
                IconButton(
                    onClick = { navController.navigate(Destination.TEST.name) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true } //이전 스택에 쌓인 내용을 지우고 시작화면만 남겨놓음
                        launchSingleTop = true //스택에 동일한 화면이 쌓이는걸 방지
                        restoreState = true //화면 이동시 상태유지
                    } },
                    Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = if (currentRoute == Destination.TEST.name) SelectedIcon else Color.White
                    )
                }
                IconButton(
                    onClick = { navController.navigate(Destination.TEST2.name) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    } },
                    Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = if (currentRoute == Destination.TEST2.name) SelectedIcon else Color.White
                    )
                }
            }
        }) { innerPadding ->
        NavHost(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White),
            navController = navController
        )
    }
}
