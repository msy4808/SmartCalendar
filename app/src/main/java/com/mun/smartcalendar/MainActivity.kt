package com.mun.smartcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mun.smartcalendar.ui.theme.SelectedIcon
import com.mun.smartcalendar.ui.theme.SmartCalendarTheme
import com.mun.smartcalendar.ui.theme.SplashBackGround

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SmartCalendarTheme {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomAppBar(Modifier.background(SplashBackGround)) {
                            IconButton(
                                onClick = { navController.navigate("calendar") },
                                Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = null,
                                    tint = if (currentRoute == "calendar") SelectedIcon else Color.White
                                )
                            }
                            IconButton(
                                onClick = { navController.navigate("test") },
                                Modifier.weight(1f)
                            ) {
                                Icon(imageVector = Icons.Filled.Search,
                                    contentDescription = null,
                                    tint = if (currentRoute == "test") SelectedIcon else Color.White
                                )
                            }
                            IconButton(
                                onClick = { navController.navigate("test2") },
                                Modifier.weight(1f)
                            ) {
                                Icon(imageVector = Icons.Filled.Info,
                                    contentDescription = null,
                                    tint = if (currentRoute == "test2") SelectedIcon else Color.White
                                )
                            }
                        }
                    }) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.White),
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "CalendarScreen")
    }
}
@Composable
fun TestScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "TestScreen")
    }
}
@Composable
fun TestScreen2(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "TestScreen2")
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier, navController: NavHostController) {

    //NavHost Setting
    NavHost(navController = navController, startDestination = "calendar") {
        //Define each screen as composable
        composable("calendar") { CalendarScreen(modifier) }
        composable("test") { TestScreen(modifier) }
        composable("test2") { TestScreen2(modifier) }
    }
}