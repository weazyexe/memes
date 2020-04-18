package exe.weazy.memes.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import exe.weazy.memes.entity.Meme
import exe.weazy.memes.network.NetworkRepository
import exe.weazy.memes.state.MemesState
import exe.weazy.memes.storage.UserStorage
import exe.weazy.memes.util.extensions.subscribe
import java.util.*

class MainViewModel: ViewModel() {

    private val repository = NetworkRepository()

    val memes = MutableLiveData<List<Meme>>(listOf())
    val memesState = MutableLiveData<MemesState>(MemesState.DEFAULT)

    fun getUserToken(context: Context): String {
        val userStorage = UserStorage(context)
        return userStorage.getAccessToken()
    }

    fun fetchMemes() {
        memesState.postValue(MemesState.LOADING)
        subscribe(repository.fetchMemes(), { response ->
            val memes = response.map { it.convert() }
            this.memes.postValue(memes)
            if (memes.isEmpty()) {
                memesState.postValue(MemesState.EMPTY)
            } else {
                memesState.postValue(MemesState.SUCCESS)
            }
        }, {
            memesState.postValue(MemesState.ERROR)
        })
    }
}