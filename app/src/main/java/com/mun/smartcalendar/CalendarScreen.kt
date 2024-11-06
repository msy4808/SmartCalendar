package com.mun.smartcalendar

import android.content.Context
import android.os.Build
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mun.smartcalendar.ui.theme.White

@Composable
fun CalendarScreen(
    context: Context,
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
    val selectedWeeks =
        getWeeksOfMonth(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth)

    val pagerState = rememberPagerState(pageCount = { totalWeeks }, initialPage = selectedWeeks - 1)

    var onClickedTodayButton by remember { mutableStateOf(false) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var showCalendarBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(), // Column 너비를 부모 너비로 설정
        verticalArrangement = Arrangement.Top, // 컴포저블들이 위쪽에 딱 붙게 정렬
        horizontalAlignment = Alignment.CenterHorizontally // 중앙 정렬
    ) {
        MainTopBar(
            title = selectedDate.monthValue.toString().padStart(2, '0') + "월",
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
        ElevatedCard(
            modifier = modifier
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ),
            colors = CardDefaults.elevatedCardColors(containerColor = White),
            shape = RectangleShape
        ) {
            Column(
                modifier = modifier
                    .padding(20.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {
                HorizontalCalendar(
                    pagerState = pagerState,
                    viewModel = calendarViewModel,
                    isExpanded = isExpanded,
                    onClickDate = { clickedDate ->
                        calendarViewModel.updateSelectedDate(clickedDate)
                    },
                    onClickedTodayButton = {
                        calendarViewModel.initDateToToday()
                        onClickedTodayButton = true
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(24.dp))

                if (isExpanded) {
                    DateList(
                        navigateToCalendarDetailScreen = {
                            navController.navigate(Destination.CALENDAR_DETAIL.name)
                        }
                    )
                }
            }
        }
    }
    if (showCalendarBottomSheet) {
        CalendarBottomSheet(
            onDismissRequest = {showCalendarBottomSheet = false},
            onClickedTodayButton = {
                calendarViewModel.initDateToToday()
                onClickedTodayButton = true
            },
            viewModel = calendarViewModel
        )
    }

}

@Composable
fun DateList(
    navigateToCalendarDetailScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .height(480.dp),
        state = scrollState
    ) {
        items((0..23).toList()) { time ->
            DateListItem(
                time = time,
                onClickDate = navigateToCalendarDetailScreen
            )
        }
    }

    LaunchedEffect(key1 = true) {
        scrollState.scrollToItem(index = 23)
    }
}