package exe.weazy.memes.util.extensions

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import exe.weazy.memes.R

fun Activity.showErrorSnackbar(stringRes: Int, view: View) {
    val snackbar = Snackbar.make(view, stringRes, Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(getColor(R.color.colorRed))
    snackbar.setTextColor(getColor(R.color.colorWhite))
    snackbar.show()
}

fun Activity.handleBottomNavigationBarInsets(rootView: View, bottomNav: BottomNavigationView) {
    rootView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

    val bottomNavBottomPadding = bottomNav.paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(bottomNav) { view, insets ->
        view.updatePadding(bottom = bottomNavBottomPadding + insets.systemWindowInsetBottom)
        insets
    }
}