package exe.weazy.memes.di

import dagger.Component
import exe.weazy.memes.repository.AuthRepository
import exe.weazy.memes.repository.MemesRepository
import exe.weazy.memes.ui.main.MainViewModel
import exe.weazy.memes.ui.main.memes.MemeViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, RepositoryModule::class])
interface AppComponent {
    fun injectAuthRepository(repository: AuthRepository)
    fun injectMemesRepository(repository: MemesRepository)
    fun injectMainViewModel(mainViewModel: MainViewModel)
    fun injectMemeViewModel(memeViewModel: MemeViewModel)
}