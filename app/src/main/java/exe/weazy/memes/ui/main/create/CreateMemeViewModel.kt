package exe.weazy.memes.ui.main.create

import androidx.lifecycle.ViewModel
import exe.weazy.memes.data.MemesRepository
import exe.weazy.memes.di.App
import javax.inject.Inject

class CreateMemeViewModel: ViewModel() {

    @Inject
    lateinit var memesRepository: MemesRepository

    init {
        App.getComponent().injectCreateMemeViewModel(this)
    }


}