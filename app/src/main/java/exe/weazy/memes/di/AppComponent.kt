package exe.weazy.memes.di

import dagger.Component
import exe.weazy.memes.network.LocalRepository
import exe.weazy.memes.network.NetworkRepository

@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {
    fun injectNetworkRepository(repository: NetworkRepository)
    fun injectLocalRepository(repository: LocalRepository)
}