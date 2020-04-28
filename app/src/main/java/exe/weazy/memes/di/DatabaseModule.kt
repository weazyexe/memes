package exe.weazy.memes.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import exe.weazy.memes.data.db.AppDatabase
import exe.weazy.memes.data.db.MemesDao

@Module
class DatabaseModule(private val context: Context) {

    private val database = Room.databaseBuilder(context, AppDatabase::class.java, "database")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    fun provideMemesDao(): MemesDao {
        return database.memesDao()
    }
}