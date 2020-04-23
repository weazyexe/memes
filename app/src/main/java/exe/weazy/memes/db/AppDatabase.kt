package exe.weazy.memes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import exe.weazy.memes.entity.Meme

@Database(entities = [Meme::class], version = 5)
@TypeConverters(value = [DateConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun memesDao() : MemesDao
}