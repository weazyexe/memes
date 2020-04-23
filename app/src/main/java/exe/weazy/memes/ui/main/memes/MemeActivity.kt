package exe.weazy.memes.ui.main.memes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import exe.weazy.memes.R
import exe.weazy.memes.entity.Meme
import exe.weazy.memes.ui.main.MainViewModel
import exe.weazy.memes.util.extensions.useViewModel
import exe.weazy.memes.util.handleToolbarInsets
import kotlinx.android.synthetic.main.activity_meme.*
import org.ocpsoft.prettytime.PrettyTime

class MemeActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var meme: Meme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme)

        rootViewMeme.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        handleToolbarInsets(memeToolbarLayout)

        viewModel = useViewModel(this, MainViewModel::class.java)

        initListeners()

        val meme = intent.getParcelableExtra<Meme>("meme")
        if (meme != null) {
            this.meme = meme
            showMemeInfo(meme)
        }
    }

    private fun initListeners() {
        closeButton.setOnClickListener {
            onBackPressed()
        }

        shareButton.setOnClickListener {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
        }

        favoriteButton.setOnClickListener {
            viewModel.likeMeme(meme)
            showMemeInfo(Meme(meme.id, meme.title, meme.description,
                !meme.isFavorite, meme.createDate, meme.photoUrl)
            )
        }
    }

    private fun showMemeInfo(meme: Meme) {
        titleTextView.text = meme.title
        descriptionTextView.text = meme.description?.replace("<br/>", "\n")
        dateTextView.text = PrettyTime().format(meme.createDate)

        val favoriteDrawable = if (meme.isFavorite) {
            R.drawable.ic_favorite_filled_blue_24dp
        } else {
            R.drawable.ic_favorite_border_white_24dp
        }

        favoriteButton.setImageDrawable(
            ContextCompat.getDrawable(this, favoriteDrawable)
        )

        Glide
            .with(this)
            .load(meme.photoUrl)
            .into(memeImageView)
    }
}
