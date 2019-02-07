package ru.bulat.mukhutdinov.sample.userslist.interactor

import io.reactivex.Single
import ru.bulat.mukhutdinov.sample.userslist.usecase.GetAllUsersUseCase
import ru.bulat.mukhutdinov.sample.user.gateway.UserGateway
import ru.bulat.mukhutdinov.sample.user.model.User

class GetAllUsersInteractor(private val userGateway: UserGateway) : GetAllUsersUseCase {

    override fun execute(): Single<List<User>> =
        userGateway.getAll()
}