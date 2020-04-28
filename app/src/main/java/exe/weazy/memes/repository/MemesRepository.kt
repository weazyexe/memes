package exe.weazy.memes.repository

import exe.weazy.memes.repository.db.MemesDao
import exe.weazy.memes.repository.db.entities.MemeEntity
import exe.weazy.memes.di.App
import exe.weazy.memes.model.Meme
import exe.weazy.memes.repository.network.NetworkService
import exe.weazy.memes.repository.network.responses.MemesResponse
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemesRepository {

    @Inject
    lateinit var memesDao: MemesDao

    @Inject
    lateinit var service: NetworkService

    lateinit var memes: MutableList<Meme>

    private val mergeNetworkAndDb = BiFunction<List<MemesResponse>, List<MemeEntity>, List<Meme>> { response, entities ->
        val result = response.map { meme ->
            Meme(
                id = meme.id,
                title = meme.title,
                description = meme.description,
                isFavorite = entities.find { meme.id == it.id }?.isFavorite ?: meme.isFavorite,
                createDate = Date(meme.createDate),
                photoUrl = meme.photoUrl
            )
        } as MutableList<Meme>

        result.addAll(entities.map { it.convert() })

        return@BiFunction result.distinctBy { it.id }
    }

    init {
        App.getComponent().injectMemesRepository(this)
    }

    fun fetch(): Observable<List<Meme>> = Observable.zip(
        service.fetchMemes(),
        memesDao.getAll(),
        mergeNetworkAndDb
    )

    fun getById(id: Long): Observable<Meme> = memesDao.getById(id)
        .map { it.convert() }

    fun save(memes: List<Meme>) {
        this.memes = memes as MutableList<Meme>
        memesDao.clearAll()

        memes.forEach {
            memesDao.insert(it.toEntity())
        }
    }

    fun like(meme: Meme) {
        val updatedMeme = Meme(
            meme.id,
            meme.title,
            meme.description,
            !meme.isFavorite,
            meme.createDate,
            meme.photoUrl
        )

        if (::memes.isInitialized) {
            val index = memes.indexOf(meme)
            memes[index] = updatedMeme
        }

        memesDao.update(updatedMeme.toEntity())
    }
}