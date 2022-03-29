package com.nidhin.customerapp.persistance

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class Converters {


    companion object {
        var strSeparator = "__,__"

        @TypeConverter
        @JvmStatic
        fun convertIntList(items: List<Int>): String {
            return Gson().toJson(items)
        }

        @TypeConverter
        @JvmStatic
        fun convertIntStringToList(string: String) =
            Gson().fromJson(string, Array<Int>::class.java).toList()

        @TypeConverter
        @JvmStatic
        fun convertToDate(time: Long): Date {
            return Date(time)
        }

        @TypeConverter
        @JvmStatic
        fun convertFromDate(time: Date): Long {
            return time.time
        }
    }
}