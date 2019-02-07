package ru.bulat.mukhutdinov.sample.userslist.usecase

import io.reactivex.Single
import ru.bulat.mukhutdinov.sample.user.model.User

interface GetAllUsersUseCase {

    fun execute(): Single<List<User>>
}