package ru.bulat.mukhutdinov.mvvm

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.content.Context
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.bulat.mukhutdinov.mvvm.infrastructure.common.di.CommonInjectionModule
import ru.bulat.mukhutdinov.mvvm.infrastructure.util.DummyDataProvider
import ru.bulat.mukhutdinov.mvvm.user.di.UserInjectionModule
import ru.bulat.mukhutdinov.mvvm.usersList.di.UsersListInjectionModule

class App : Application(), KodeinAware {

    override val kodein = ConfigurableKodein()

    private val dummyDataProvider: DummyDataProvider by instance()

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        setupKodein()
//        dummyDataProvider.generateUsersDummyData()
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                { Timber.d("Dummy users data is generated") },
//                { Timber.e(it) })
    }

    private fun setupKodein() {
        kodein.apply {
            addImport(UserInjectionModule.module)
            addImport(CommonInjectionModule.module)
            addImport(UsersListInjectionModule.module)

            addImport(Kodein.Module(App::class.java.name) {
                bind<ContentResolver>() with singleton { this@App.contentResolver }
                bind<Context>() with singleton { this@App }
                bind<Application>() with singleton { this@App }
            })
        }
    }
}