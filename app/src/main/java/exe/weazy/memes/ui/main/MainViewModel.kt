package exe.weazy.memes.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import exe.weazy.memes.storage.UserStorage

class MainViewModel: ViewModel() {

    private lateinit var userStorage: UserStorage

    fun getUserToken(context: Context): String {
        val userStorage = UserStorage(context)
        return userStorage.getAccessToken()
    }
}