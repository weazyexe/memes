package exe.weazy.memes.ui.main.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import exe.weazy.memes.R
import exe.weazy.memes.state.ScreenState
import exe.weazy.memes.util.extensions.useViewModel
import kotlinx.android.synthetic.main.activity_create_meme.*

class CreateMemeActivity : AppCompatActivity() {

    private lateinit var viewModel: CreateMemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meme)

        viewModel = useViewModel(this, CreateMemeViewModel::class.java)

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        closeButton.setOnClickListener {
            onBackPressed()
        }

        removeImageButton.setOnClickListener {
            Toast.makeText(this, "No image bruh", Toast.LENGTH_SHORT).show()
        }

        chooseImageButton.setOnClickListener {
            val dialog = ChooseMemeDialogFragment()
            dialog.show(supportFragmentManager, "dialog_tag")
        }

        createMemeButton.setOnClickListener {
            viewModel.save(
                title = memeTitleEditText.text.toString(),
                description = memeDescriptionEditText.text.toString(),
                image = "https://i.imgur.com/AY8nvLr.jpg"
            )
        }
    }

    private fun initObservers() {
        viewModel.state.observe(this, Observer {
            setState(it)
        })
    }

    private fun setState(state: ScreenState) {
        when (state) {
            ScreenState.DEFAULT, ScreenState.EMPTY -> {
                progressBar.isVisible = false
                errorTextView.isVisible = false
                formLayout.isVisible = true
            }
            ScreenState.ERROR -> {
                progressBar.isVisible = false
                errorTextView.isVisible = true
                formLayout.isVisible = false
            }
            ScreenState.SUCCESS -> {
                progressBar.isVisible = false
                errorTextView.isVisible = false
                formLayout.isVisible = true

                Toast.makeText(this, getString(R.string.meme_created), Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            ScreenState.LOADING -> {
                progressBar.isVisible = true
                errorTextView.isVisible = false
                formLayout.isVisible = true
            }
        }
    }
}
