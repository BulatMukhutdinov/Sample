package ru.bulat.mukhutdinov.sample

import android.annotation.SuppressLint
import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.bulat.mukhutdinov.sample.infrastructure.common.di.CommonInjectionModule
import ru.bulat.mukhutdinov.sample.infrastructure.common.network.di.NetworkInjectionModule
import ru.bulat.mukhutdinov.sample.infrastructure.util.DummyDataProvider
import ru.bulat.mukhutdinov.sample.main.di.MainInjectionModule
import ru.bulat.mukhutdinov.sample.post.di.PostInjectionModule
import ru.bulat.mukhutdinov.sample.postslist.di.PostsListInjectionModule
import ru.bulat.mukhutdinov.sample.user.di.UserInjectionModule
import ru.bulat.mukhutdinov.sample.userslist.di.UsersListInjectionModule
import timber.log.Timber

class App : Application() {

    private val dummyDataProvider: DummyDataProvider by inject()

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

//        setupStrictMode()

        setupKoin()

        setupStetho()

//        dummyDataProvider.generateUsersDummyData()
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                { Timber.d("Dummy users data is generated") },
//                { Timber.e(it) })
    }

    @SuppressLint("CheckResult")
    private fun setupStetho() {
        Completable.fromCallable { Stetho.initializeWithDefaults(this) }
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Timber.d("Stetho is initialized") },
                { Timber.e(it) }
            )
    }

    private fun setupStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )

            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build()
            )
        }
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)

            modules(
                UserInjectionModule.module,
                CommonInjectionModule.module,
                UsersListInjectionModule.module,
                MainInjectionModule.module,
                PostInjectionModule.module,
                PostsListInjectionModule.module,
                NetworkInjectionModule.module
            )
        }
    }
}