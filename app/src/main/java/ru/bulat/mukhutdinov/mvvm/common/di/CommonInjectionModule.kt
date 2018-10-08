package ru.bulat.mukhutdinov.mvvm.common.di

import android.content.Context
import androidx.room.Room
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.bulat.mukhutdinov.mvvm.BuildConfig
import ru.bulat.mukhutdinov.mvvm.common.db.MvvmDatabase
import ru.bulat.mukhutdinov.mvvm.util.DummyDataProvider

object CommonInjectionModule {
    private const val DATABASE_NAME = "mvvm_db"

    val module = Kodein.Module("CommonInjectionModule") {

        bind<MvvmDatabase>() with singleton {
            return@singleton Room
                    .databaseBuilder(
                            instance(),
                            MvvmDatabase::class.java,
                            DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
        }

        bind<Picasso>() with singleton {
            return@singleton Picasso.Builder(instance())
                    .memoryCache(LruCache(instance<Context>()))
                    .loggingEnabled(BuildConfig.DEBUG)
                    .build()
        }

        bind<DummyDataProvider>() with singleton {
            return@singleton DummyDataProvider(instance())
        }
    }
}