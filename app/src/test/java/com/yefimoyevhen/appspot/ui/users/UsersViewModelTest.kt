package com.yefimoyevhen.appspot.ui.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yefimoyevhen.appspot.MainCoroutineRule
import com.yefimoyevhen.appspot.repository.FakeRepository
import com.yefimoyevhen.appspot.util.DataState
import com.yefimoyevhen.appspot.util.FakeInternetChecker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: UsersViewModel
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setup() {
        fakeRepository = FakeRepository()
        viewModel = UsersViewModel(FakeInternetChecker(), fakeRepository)
    }

    @Test
    fun `fetch data with network error, returns DataState Error`() {
        fakeRepository.shouldReturnNetworkError = true
        viewModel.fetchData()
        val dataState = viewModel.dataState.value
        assert(dataState is DataState.Error)
    }

    @Test
    fun `fetch data, returns DataState Success`() {
        fakeRepository.shouldReturnNetworkError = false
        viewModel.fetchData()
        val dataState = viewModel.dataState.value
        assert(dataState is DataState.Success)
    }

}