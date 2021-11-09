package com.yefimoyevhen.appspot.ui.userdatails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yefimoyevhen.appspot.MainCoroutineRule
import com.yefimoyevhen.appspot.repository.FakeRepository
import com.yefimoyevhen.appspot.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: UserDetailsViewModel
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setup() {
        fakeRepository = FakeRepository()
        viewModel = UserDetailsViewModel(fakeRepository)
    }


    @Test
    fun `find user with wrong id, returns DataState Error`() {
        viewModel.findUserById("78954581njkg")
        val dataState = viewModel.dataState.value
        assert(dataState is DataState.Error)
    }

    @Test
    fun `find user, returns DataState Success`() {
        viewModel.findUserById("1")
        val dataState = viewModel.dataState.value
        assert(dataState is DataState.Success)
    }
}