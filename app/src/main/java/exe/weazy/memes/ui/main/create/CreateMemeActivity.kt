package exe.weazy.memes.ui.main.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import exe.weazy.memes.R
import kotlinx.android.synthetic.main.activity_create_meme.*

class CreateMemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meme)

        initListeners()
    }

    private fun initListeners() {
        closeButton.setOnClickListener {
            onBackPressed()
        }
    }
}
