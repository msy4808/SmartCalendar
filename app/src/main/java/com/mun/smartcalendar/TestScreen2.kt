package com.mun.smartcalendar

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun TestScreen2(context: Context,
                navController: NavController,
                modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "TestScreen2")
    }
}