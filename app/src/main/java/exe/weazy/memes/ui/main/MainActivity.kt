package exe.weazy.memes.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import exe.weazy.memes.R
import exe.weazy.memes.util.extensions.useViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var memesFragment: MemesFragment
    private lateinit var createMemeFragment: CreateMemeFragment
    private lateinit var profileFragment: ProfileFragment
    private var active = Fragment()

    private lateinit var viewModel : MainViewModel

    private var newPosition = 0
    private var startingPosition = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.memesButton -> {
                newPosition = 0
                if (startingPosition != newPosition) {
                    changeFragment(memesFragment)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.createMemeButton -> {
                newPosition = 1
                if (startingPosition != newPosition) {
                    changeFragment(createMemeFragment)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.profileButton -> {
                newPosition = 2
                if (startingPosition != newPosition) {
                    changeFragment(profileFragment)
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootViewMain.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        val bottomNavBottomPadding = bottomNav.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(bottomNav) { view, insets ->
            view.updatePadding(bottom = bottomNavBottomPadding + insets.systemWindowInsetBottom)
            insets
        }

        viewModel = useViewModel(this, MainViewModel::class.java)

        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        loadFragments()
    }

    private fun loadFragments() {
        memesFragment = MemesFragment()
        createMemeFragment = CreateMemeFragment()
        profileFragment = ProfileFragment()

        supportFragmentManager.beginTransaction().add(R.id.fragmentLayout, memesFragment).hide(memesFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragmentLayout, createMemeFragment).hide(createMemeFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragmentLayout, profileFragment).commit()

        bottomNav.selectedItemId = R.id.memesButton
        active = memesFragment
    }

    private fun changeFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().show(fragment).hide(active).commit()
        startingPosition = newPosition
        active = fragment
    }
}
