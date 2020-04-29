package exe.weazy.memes.ui.main.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import exe.weazy.memes.data.MemesRepository
import exe.weazy.memes.di.App
import exe.weazy.memes.model.Meme
import exe.weazy.memes.state.ScreenState
import java.util.*
import javax.inject.Inject

class CreateMemeViewModel: ViewModel() {

    @Inject
    lateinit var memesRepository: MemesRepository

    var state = MutableLiveData<ScreenState>(ScreenState.DEFAULT)

    init {
        App.getComponent().injectCreateMemeViewModel(this)
    }

    fun save(title: String, description: String, image: String) {
        state.postValue(ScreenState.LOADING)

        try {
            val meme = Meme(
                id = Date().time,
                title = title,
                description = description,
                isFavorite = false,
                createDate = Date(),
                photoUrl = image
            )

            memesRepository.save(meme)
            state.postValue(ScreenState.SUCCESS)
        } catch (e: Throwable) {
            state.postValue(ScreenState.ERROR)
        }
    }
}