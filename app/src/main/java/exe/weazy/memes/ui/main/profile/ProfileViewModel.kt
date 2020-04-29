package exe.weazy.memes.ui.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import exe.weazy.memes.data.MemesRepository
import exe.weazy.memes.data.storage.UserStorage
import exe.weazy.memes.di.App
import exe.weazy.memes.model.Meme
import exe.weazy.memes.model.UserInfo
import exe.weazy.memes.state.ScreenState
import exe.weazy.memes.util.extensions.subscribe
import javax.inject.Inject

class ProfileViewModel: ViewModel() {

    @Inject
    lateinit var memesRepository: MemesRepository

    @Inject
    lateinit var userStorage: UserStorage

    val userInfo = MutableLiveData<UserInfo>()
    val memes = MutableLiveData<List<Meme>>()
    val state = MutableLiveData<ScreenState>(ScreenState.DEFAULT)

    init {
        App.getComponent().injectProfileViewModel(this)
    }

    fun getLocalMemes() {
        state.postValue(ScreenState.LOADING)

        subscribe(memesRepository.fetchLocal(), {
            memes.postValue(it)
            memesRepository.localMemes = it as MutableList<Meme>

            if (it.isEmpty()) {
                state.postValue(ScreenState.EMPTY)
            } else {
                state.postValue(ScreenState.SUCCESS)
            }

        }, {
            state.postValue(ScreenState.ERROR)
        })
    }

    fun refreshMemes() {
        if (state.value == ScreenState.SUCCESS || state.value == ScreenState.EMPTY) {
            memes.postValue(memesRepository.localMemes)
        }
    }

    fun getUserInfo() {
        userInfo.postValue(userStorage.getUserInfo())
    }

    fun likeMeme(meme: Meme) {
        memesRepository.like(meme)
        this.memes.postValue(memesRepository.localMemes)
    }

    fun logout() {
        userStorage.erase()
    }
}