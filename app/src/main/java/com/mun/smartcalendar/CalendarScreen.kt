package com.mun.smartcalendar

import android.content.Context
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun CalendarScreen(context: Context,
                   navController: NavController,
                   modifier: Modifier = Modifier
) {
    val calendarViewModel = CalendarViewModel()
    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    val totalWeeks = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.month.maxLength())
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val selectedWeeks = getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth)

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var showCalendarBottomSheet by remember { mutableStateOf(false) }

    MainTopBar(
        title = selectedDate.monthValue.toString().padStart(2,'0') + "월",
        titleIcon = true,
        enableExpandButton = true,
        onClickExpandButton = {
            isExpanded = !isExpanded
        },
        onClickTitle = {
            showCalendarBottomSheet = true
        },
        enableBackButton = false
    )
    Box(modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "CalendarScreen")
    }
}