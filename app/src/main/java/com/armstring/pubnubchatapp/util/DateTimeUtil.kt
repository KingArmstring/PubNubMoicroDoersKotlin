package com.armstring.pubnubchatapp.util

import android.annotation.SuppressLint
import org.joda.time.format.ISODateTimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar


open class DateTimeUtil {

    companion object {

        @SuppressLint("SimpleDateFormat")
        fun getTimeStampUtc(): String {
            val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("HH:mm")
            val nowCalendar: Calendar = Calendar.getInstance()
            return simpleDateFormat.format(nowCalendar.time)
        }

        fun getTimeStampUtc(instance: Long): String {
            return ISODateTimeFormat.dateTime().withZoneUTC().print(instance)
        }
    }
}