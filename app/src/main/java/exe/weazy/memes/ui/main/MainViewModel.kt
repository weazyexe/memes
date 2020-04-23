package exe.weazy.memes.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import exe.weazy.memes.entity.Meme
import exe.weazy.memes.network.LocalRepository
import exe.weazy.memes.network.NetworkRepository
import exe.weazy.memes.state.MemesState
import exe.weazy.memes.storage.UserStorage
import exe.weazy.memes.util.extensions.subscribe

class MainViewModel: ViewModel() {

    private val networkRepository = NetworkRepository()
    private val localRepository = LocalRepository()

    val memes = MutableLiveData<List<Meme>>(listOf())
    val memesState = MutableLiveData<MemesState>(MemesState.DEFAULT)

    fun getUserToken(context: Context): String {
        val userStorage = UserStorage(context)
        return userStorage.getAccessToken()
    }

    fun fetchMemes() {
        memesState.postValue(MemesState.LOADING)


        val observable = networkRepository.fetchMemes()
            .map { response -> response.map { it.convert() } }
            .map { memes ->
                memes.map { meme ->
                    val memesFromDb = localRepository.fetchMemes()

                    // Если лайк у мема сохранен локально, отображаем его
                    if (memesFromDb.any { meme.id == it.id && meme.isFavorite != it.isFavorite }) {
                        Meme(meme.id, meme.title, meme.description, !meme.isFavorite, meme.createDate, meme.photoUrl)
                    } else {
                        meme
                    }
                }
            }

        subscribe(observable, { memes ->
            this.memes.postValue(memes)
            localRepository.saveMemes(memes)

            if (memes.isEmpty()) {
                memesState.postValue(MemesState.EMPTY)
            } else {
                memesState.postValue(MemesState.SUCCESS)
            }
        }, {
            memesState.postValue(MemesState.ERROR)
        })
    }

    fun likeMeme(meme: Meme) {
        var memes = this.memes.value?.toList()

        if (!memes.isNullOrEmpty()) {
            memes = memes.map {
                if (it.id == meme.id) {
                    Meme(meme.id, meme.title, meme.description, !meme.isFavorite, meme.createDate, meme.photoUrl)
                } else {
                    it
                }
            }

            localRepository.likeMeme(meme)
            this.memes.postValue(memes)
        }
    }
}