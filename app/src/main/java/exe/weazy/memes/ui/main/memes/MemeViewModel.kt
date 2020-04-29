package exe.weazy.memes.ui.main.memes

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

class MemeViewModel: ViewModel() {

    @Inject
    lateinit var memesRepository: MemesRepository

    @Inject
    lateinit var userStorage: UserStorage

    var meme = MutableLiveData<Meme>()

    var userInfo = MutableLiveData<UserInfo>()

    var state = MutableLiveData<ScreenState>(ScreenState.DEFAULT)

    init {
        App.getComponent().injectMemeViewModel(this)
    }

    fun getMeme(id: Long) {
        state.postValue(ScreenState.LOADING)
        subscribe(memesRepository.getById(id), {
            meme.postValue(it)
            state.postValue(ScreenState.SUCCESS)
        }, {
            state.postValue(ScreenState.ERROR)
        })
    }

    fun getUserInfo() {
        userInfo.postValue(userStorage.getUserInfo())
    }

    fun likeMeme() {
        val meme = meme.value
        if (meme != null) {
            memesRepository.like(meme)
        }
    }
}