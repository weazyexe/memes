package exe.weazy.memes.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import exe.weazy.memes.entity.Meme
import exe.weazy.memes.network.NetworkRepository
import exe.weazy.memes.storage.UserStorage
import exe.weazy.memes.util.extensions.subscribe

class MainViewModel: ViewModel() {

    private val repository = NetworkRepository()
    val memes = MutableLiveData<List<Meme>>(listOf())

    fun getUserToken(context: Context): String {
        val userStorage = UserStorage(context)
        return userStorage.getAccessToken()
    }

    fun fetchMemes() {
        subscribe(repository.fetchMemes(), {

        }, {

        })
    }
}