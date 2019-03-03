package ru.bulat.mukhutdinov.sample

import android.annotation.SuppressLint
import android.app.Application
import android.os.StrictMode
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import io.fabric.sdk.android.Fabric
import io.reactivex.Completable
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.bulat.mukhutdinov.sample.auth.di.AuthInjectionModule
import ru.bulat.mukhutdinov.sample.diary.di.DiaryInjectionModule
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

        setupStrictMode()

        setupTimber()

        setupKoin()

        setupStetho()

        setupFabric()

        setRxErrorHandler()
    }

    @SuppressLint("CheckResult")
    private fun setupFabric() {
        Completable
            .fromCallable { Fabric.with(this, Crashlytics()) }
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Timber.d("Crashlytics is initialized") },
                { Timber.e(it) }
            )
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
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

    private fun setRxErrorHandler() {
        val oldHandler = RxJavaPlugins.getErrorHandler()
        RxJavaPlugins.setErrorHandler { throwable ->
            when (throwable) {
                is UndeliverableException -> Timber.d("subscription was cancelled")
                is InterruptedException -> Timber.d("some blocking code was interrupted by a dispose call")
                else -> acceptRxThrowable(oldHandler, throwable)
            }
        }
    }

    private fun acceptRxThrowable(handler: Consumer<in Throwable>?, throwable: Throwable) {
        if (handler != null) {
            handler.accept(throwable)
        } else {
            Thread.currentThread()
                .uncaughtExceptionHandler
                .uncaughtException(Thread.currentThread(), throwable)
        }
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
            // todo use detectAll() after https://github.com/square/okhttp/issues/3537 will be fixed
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .detectCleartextNetwork()
                .detectFileUriExposure()
                .detectLeakedClosableObjects()
                .detectLeakedRegistrationObjects()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build()
            )
        }
    }

    @SuppressLint("CheckResult")
    private fun setupKoin() {
        Completable
            .fromCallable {
                startKoin {
                    androidContext(this@App)

                    modules(
                        UserInjectionModule.module,
                        CommonInjectionModule.module,
                        UsersListInjectionModule.module,
                        MainInjectionModule.module,
                        PostInjectionModule.module,
                        PostsListInjectionModule.module,
                        NetworkInjectionModule.module,
                        AuthInjectionModule.module,
                        DiaryInjectionModule.module
                    )

                }
            }
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    initDummyData()
                    Timber.d("Koin is initialized")
                },
                { Timber.e(it) }
            )
    }

    @SuppressLint("CheckResult")
    private fun initDummyData() {
        dummyDataProvider.generateUsersDummyData()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Timber.d("Dummy users data is generated") },
                { Timber.e(it) })
    }
}