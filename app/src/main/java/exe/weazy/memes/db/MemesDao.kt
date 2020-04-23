package exe.weazy.memes.db

import androidx.room.*
import exe.weazy.memes.entity.Meme

@Dao
interface MemesDao {

    @Query("SELECT * FROM Meme")
    fun getAll(): List<Meme>

    @Insert
    fun insert(meme: Meme)

    @Update
    fun update(meme: Meme)

    @Query("DELETE FROM Meme")
    fun nukeTable()
}