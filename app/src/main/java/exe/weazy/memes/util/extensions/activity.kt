package exe.weazy.memes.util.extensions

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.snackbar.Snackbar
import exe.weazy.memes.R

fun <T : ViewModel> Activity.useViewModel(owner: ViewModelStoreOwner, vmClass: Class<T>): T {
    return ViewModelProvider(owner, ViewModelProvider.NewInstanceFactory()).get(vmClass)
}

fun Activity.showErrorSnackbar(stringRes: Int, view: View) {
    val snackbar = Snackbar.make(view, stringRes, Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(getColor(R.color.colorRed))
    snackbar.setTextColor(getColor(R.color.colorWhite))
    snackbar.show()
}