package exe.weazy.memes.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import exe.weazy.memes.repository.db.entities.MemeEntity

@Database(entities = [MemeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memesDao() : MemesDao
}