package ru.bulat.mukhutdinov.sample.userslist.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import ru.bulat.mukhutdinov.sample.R
import ru.bulat.mukhutdinov.sample.user.model.User
import ru.bulat.mukhutdinov.sample.userslist.usecase.GetAllUsersUseCase

@RunWith(AndroidJUnit4::class)
class UsersListFragmentTest {

    private val module = module(override = true) {
        viewModel { (fragment: Fragment) ->
            val getAllUsersUseCase = mockk<GetAllUsersUseCase>(relaxed = true)
            every {
                getAllUsersUseCase.execute()
            } returns Single.just(listOf(USER))

            val viewModel = UsersListAndroidViewModel(getAllUsersUseCase)
            viewModel.lifecycleOwner = fragment.viewLifecycleOwner

            return@viewModel viewModel
        }
    }

    @Before
    fun setup() {
        startKoin { modules(module) }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun users_checkInfoIsDisplayed() {
        launchFragmentInContainer<UsersListFragment>(themeResId = R.style.AppTheme)

        onView(withText(USER.name))
                .check(matches(isDisplayed()))

        onView(withText(USER.description))
                .check(matches(isDisplayed()))
    }

    companion object {
        private val USER = User(id = 1, name = "Bulat", icon = "", description = "The author", iconThumbnail = "")
    }
}