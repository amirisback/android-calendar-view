package com.qomunal.opensource.androidresearch.model

import java.util.*

data class CalendarModel(
    var date: Int,
    var month: Int,
    var year : Int,
    var calendarCompare : Calendar,
    var isToday: Boolean = false
)