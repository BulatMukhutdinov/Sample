package ru.bulat.mukhutdinov.sample.infrastructure.common.di

import android.content.Context
import androidx.room.Room
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.bulat.mukhutdinov.sample.BuildConfig
import ru.bulat.mukhutdinov.sample.infrastructure.common.db.SampleDatabase
import ru.bulat.mukhutdinov.sample.infrastructure.util.DummyDataProvider
import timber.log.Timber

object CommonInjectionModule {
    private const val DATABASE_NAME = "sample_db"

    val module = Kodein.Module(CommonInjectionModule::class.java.name) {

        bind<SampleDatabase>() with singleton {
            return@singleton Room
                .databaseBuilder(
                    instance(),
                    SampleDatabase::class.java,
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

        bind<OkHttpClient>() with singleton {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Timber.d(it)
            })

            logger.level = HttpLoggingInterceptor.Level.BASIC

            return@singleton OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
        }

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(instance())
                .build()
        }
    }
}