package exe.weazy.memes.di

import dagger.Component
import exe.weazy.memes.network.NetworkRepository

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun injectRepository(repository: NetworkRepository)
}