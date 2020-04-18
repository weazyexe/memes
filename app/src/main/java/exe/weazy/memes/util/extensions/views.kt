package exe.weazy.memes.util.extensions

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import exe.weazy.memes.R


fun Activity.showErrorSnackbar(stringRes: Int, view: View) {
    val snackbar = Snackbar.make(view, stringRes, Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(getColor(R.color.colorRed))
    snackbar.setTextColor(getColor(R.color.colorWhite))
    snackbar.show()
}