package exe.weazy.memes.util

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import com.google.android.material.bottomnavigation.BottomNavigationView
import exe.weazy.memes.model.Meme

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

fun share(activity: Activity, meme: Meme) {

    val text = "${meme.title}\n${meme.description}\n${meme.photoUrl}"

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    activity.startActivity(shareIntent)
}