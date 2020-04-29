package exe.weazy.memes.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import exe.weazy.memes.di.App
import exe.weazy.memes.model.Meme
import exe.weazy.memes.data.MemesRepository
import exe.weazy.memes.state.ScreenState
import exe.weazy.memes.data.storage.UserStorage
import exe.weazy.memes.util.extensions.subscribe
import javax.inject.Inject

class MainViewModel: ViewModel() {

    @Inject
    lateinit var memesRepository: MemesRepository

    @Inject
    lateinit var userStorage: UserStorage

    val memes = MutableLiveData<List<Meme>>(listOf())
    val memesState = MutableLiveData<ScreenState>(ScreenState.DEFAULT)

    init {
        App.getComponent().injectMainViewModel(this)
    }

    fun getUserToken(): String {
        return userStorage.getAccessToken()
    }

    fun fetchMemes() {
        memesState.postValue(ScreenState.LOADING)

        subscribe(memesRepository.fetch(), { memes ->
            this.memes.postValue(memes)
            memesRepository.save(memes)

            if (memes.isEmpty()) {
                memesState.postValue(ScreenState.EMPTY)
            } else {
                memesState.postValue(ScreenState.SUCCESS)
            }
        }, {
            memesState.postValue(ScreenState.ERROR)
        })
    }

    fun refreshMemes() {
        if (memesState.value == ScreenState.SUCCESS || memesState.value == ScreenState.EMPTY) {
            memes.postValue(memesRepository.memes)
        }
    }

    fun likeMeme(meme: Meme) {
        memesRepository.like(meme)
        this.memes.postValue(memesRepository.memes)
    }
}