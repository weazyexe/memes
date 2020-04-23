package exe.weazy.memes.db

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun dateToString(date: Date) = date.time.toString()

    @TypeConverter
    fun stringToDate(date: String) = Date(date.toLong())
}