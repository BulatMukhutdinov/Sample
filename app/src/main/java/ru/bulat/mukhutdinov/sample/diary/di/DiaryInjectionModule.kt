package ru.bulat.mukhutdinov.sample.diary.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.diary.gateway.NoteBoundGateway
import ru.bulat.mukhutdinov.sample.diary.gateway.NoteGateway
import ru.bulat.mukhutdinov.sample.diary.ui.DiaryAndroidViewModel
import ru.bulat.mukhutdinov.sample.diary.usecase.AddNoteInteractor
import ru.bulat.mukhutdinov.sample.diary.usecase.AddNoteUseCase
import ru.bulat.mukhutdinov.sample.diary.usecase.ObserveNotesInteractor
import ru.bulat.mukhutdinov.sample.diary.usecase.ObserveNotesUseCase
import ru.bulat.mukhutdinov.sample.infrastructure.common.db.SampleDatabase

object DiaryInjectionModule {

    val module = module {
        viewModel { DiaryAndroidViewModel(get(), get()) }

        single<NoteGateway> { NoteBoundGateway(get()) }

        factory<AddNoteUseCase> { AddNoteInteractor(get()) }

        factory<ObserveNotesUseCase> { ObserveNotesInteractor(get()) }

        single { get<SampleDatabase>().noteDao() }
    }
}