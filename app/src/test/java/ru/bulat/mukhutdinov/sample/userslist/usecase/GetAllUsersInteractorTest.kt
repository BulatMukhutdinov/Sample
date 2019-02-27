package ru.bulat.mukhutdinov.sample.userslist.usecase

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ru.bulat.mukhutdinov.sample.infrastructure.exception.SampleException
import ru.bulat.mukhutdinov.sample.user.gateway.UserGateway
import ru.bulat.mukhutdinov.sample.user.model.User

@ExtendWith(MockKExtension::class)
class GetAllUsersInteractorTest {

    @MockK
    lateinit var userGateway: UserGateway

    lateinit var getAllUsersInteractor: GetAllUsersInteractor

    @BeforeEach
    fun setup() {
        getAllUsersInteractor = GetAllUsersInteractor(userGateway)
    }


    @Test
    fun getAll_success() {
        every {
            userGateway.getAll()
        } returns Single.just(users)

        getAllUsersInteractor.execute()
                .test()
                .assertResult(users)
    }

    @Test
    fun getAll_exception() {
        every {
            userGateway.getAll()
        } returns Single.error(exception)

        getAllUsersInteractor.execute()
                .test()
                .assertError(exception)
    }

    companion object {
        private val users = listOf(
                User(id = 1, name = "Bulat", icon = "", description = "The author", iconThumbnail = "")
        )

        private val exception = SampleException.DatabaseException(RuntimeException())
    }
}