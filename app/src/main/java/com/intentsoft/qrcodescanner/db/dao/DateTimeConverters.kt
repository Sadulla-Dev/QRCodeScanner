package com.intentsoft.qrcodescanner.db.dao

import androidx.room.TypeConverter
import java.util.*

class DateTimeConverters {
    @TypeConverter
    fun toCalendar(l: Long): Calendar? {
        val c = Calendar.getInstance()
        c!!.timeInMillis = l
        return c
    }

    fun fromCalendar(c: Calendar?): Long? {
        return c?.time?.time
    }
}