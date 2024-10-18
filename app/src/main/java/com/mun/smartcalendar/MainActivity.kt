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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mun.smartcalendar.ui.theme.SmartCalendarTheme
import com.mun.smartcalendar.ui.theme.SplashBackGround

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartCalendarTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomAppBar(Modifier.background(SplashBackGround)) {
                            IconButton(
                                onClick = {},
                                Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = null
                                )
                            }
                            IconButton(
                                onClick = {},
                                Modifier.weight(1f)
                            ) {
                                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                            }
                            IconButton(
                                onClick = {},
                                Modifier.weight(1f)
                            ) {
                                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                            }
                        }
                    }) { innerPadding ->
                    MainScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.White)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "Test")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartCalendarTheme {
        MainScreen(modifier = Modifier)
    }
}