package exe.weazy.memes.util

import android.app.Activity
import android.content.SharedPreferences
import android.view.View
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.snackbar.Snackbar
import exe.weazy.memes.R
import exe.weazy.memes.entity.UserInfo

fun <T> Activity.useViewModel(owner: ViewModelStoreOwner, vmClass: Class<T>): T where T : ViewModel {
    return ViewModelProvider(owner, ViewModelProvider.NewInstanceFactory()).get(vmClass)
}

fun Activity.showErrorSnackbar(stringRes: Int, view: View) {
    val snackbar = Snackbar.make(view, stringRes, Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(getColor(R.color.colorRed))
    snackbar.setTextColor(getColor(R.color.colorWhite))
    snackbar.show()
}

fun Activity.saveUserData(prefs: SharedPreferences, token: String, userInfo: UserInfo?) {
    val user = userInfo ?: UserInfo(DEFAULT_ID, DEFAULT_USERNAME,
        DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_DESCRIPTION)

    prefs.edit {
        putString("token", token)
        putInt("id", user.id)
        putString("username", user.username)
        putString("firstName", user.firstName)
        putString("lastName", user.lastName)
        putString("userDescription", user.description)
    }
}

fun Activity.getAccessToken(prefs: SharedPreferences): String {
    return prefs.getString("token", "") ?: ""
}

fun Activity.getUserInfo(prefs: SharedPreferences): UserInfo {
    return UserInfo(
        prefs.getInt("id", DEFAULT_ID),
        prefs.getString("username", DEFAULT_USERNAME)!!,
        prefs.getString("firstName", DEFAULT_FIRST_NAME)!!,
        prefs.getString("lastName", DEFAULT_LAST_NAME)!!,
        prefs.getString("description", DEFAULT_DESCRIPTION)!!
    )
}