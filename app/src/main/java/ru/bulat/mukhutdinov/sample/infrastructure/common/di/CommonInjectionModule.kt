package ru.bulat.mukhutdinov.sample.infrastructure.common.di

import androidx.room.Room
import com.squareup.picasso.BuildConfig
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.infrastructure.common.db.SampleDatabase
import ru.bulat.mukhutdinov.sample.infrastructure.util.DummyDataProvider

object CommonInjectionModule {
    private const val DATABASE_NAME = "sample_db"

    val module = module {

        single {
            Room.databaseBuilder(get(), SampleDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        single(createdAtStart = true) {
            Picasso.Builder(get())
                .memoryCache(LruCache(androidContext()))
                .loggingEnabled(BuildConfig.DEBUG)
                .build()
        }

        single { DummyDataProvider(get()) }
    }
}