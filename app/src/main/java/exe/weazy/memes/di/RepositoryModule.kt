package exe.weazy.memes.di

import dagger.Module
import dagger.Provides
import exe.weazy.memes.repository.MemesRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMemesRepository() = MemesRepository()
}