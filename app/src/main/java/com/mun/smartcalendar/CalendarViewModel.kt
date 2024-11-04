package com.mun.smartcalendar

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltViewModel
class CalendarViewModel: ViewModel() {

    private val _selectedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        MutableStateFlow<LocalDate>(LocalDate.now())
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val selectedDate get() = _selectedDate.asStateFlow()

    fun updateSelectedDate(date: LocalDate) {
        viewModelScope.launch {
            _selectedDate.value = date
        }
    }

    fun initDateToToday() {
        viewModelScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                _selectedDate.value = LocalDate.now()
            }
        }
    }
}