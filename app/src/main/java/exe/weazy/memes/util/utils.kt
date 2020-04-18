package exe.weazy.memes.util

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import com.google.android.material.bottomnavigation.BottomNavigationView

fun handleBottomNavigationBarInsets(bottomNav: BottomNavigationView) {
    val bottomNavBottomPadding = bottomNav.paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(bottomNav) { view, insets ->
        view.updatePadding(bottom = bottomNavBottomPadding + insets.systemWindowInsetBottom)
        insets
    }
}

fun handleToolbarInsets(toolbar: View) {
    val toolbarPaddingTop = toolbar.paddingTop

    ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
        view.updatePadding(
            top = toolbarPaddingTop + insets.systemWindowInsetTop
        )
        insets
    }
}