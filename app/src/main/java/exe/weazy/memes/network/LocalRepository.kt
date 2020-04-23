package exe.weazy.memes.network

import exe.weazy.memes.db.MemesDao
import exe.weazy.memes.di.App
import exe.weazy.memes.entity.Meme
import javax.inject.Inject

class LocalRepository {

    @Inject
    lateinit var memesDao: MemesDao

    private val cachedMemes = mutableListOf<Meme>()

    init {
        App.getComponent().injectLocalRepository(this)
    }

    fun fetchMemes(refresh: Boolean = false): List<Meme> {
        return if (refresh || cachedMemes.isEmpty()) {
            memesDao.getAll()
        } else {
            cachedMemes
        }
    }

    fun saveMemes(memes: List<Meme>) {
        memesDao.nukeTable()

        memes.forEach {
            memesDao.insert(it)
        }
    }

    fun likeMeme(meme: Meme) {
        memesDao.update(Meme(
            meme.id,
            meme.title,
            meme.description,
            !meme.isFavorite,
            meme.createDate,
            meme.photoUrl
        ))
    }
}