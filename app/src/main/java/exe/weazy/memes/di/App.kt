package exe.weazy.memes.di

import android.app.Application

class App : Application() {

    companion object {
        private lateinit var component : AppComponent

        fun getComponent() = component
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .networkModule(NetworkModule(applicationContext))
            .databaseModule(DatabaseModule(applicationContext))
            .build()
    }
}