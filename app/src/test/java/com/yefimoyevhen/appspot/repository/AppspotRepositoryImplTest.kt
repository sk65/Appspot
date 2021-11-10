package com.yefimoyevhen.appspot.repository

import com.yefimoyevhen.appspot.api.FakeAppspotApi
import com.yefimoyevhen.appspot.database.FakeAppspotDao
import com.yefimoyevhen.appspot.database.INVALID_USER_ID
import com.yefimoyevhen.appspot.database.VALID_USER_ID
import com.yefimoyevhen.appspot.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AppspotRepositoryImplTest {

    private lateinit var appspotRepository: AppspotRepositoryImpl
    private lateinit var fakeAppspotApi: FakeAppspotApi
    private lateinit var fakeAppsDao: FakeAppspotDao

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        fakeAppsDao = FakeAppspotDao()
        fakeAppspotApi = FakeAppspotApi()
        Dispatchers.setMain(testDispatcher)
        appspotRepository = AppspotRepositoryImpl(fakeAppspotApi, fakeAppsDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch data with network error, returns DataState Error`() {
        fakeAppspotApi.shouldReturnNetworkError = true
        runBlockingTest {
            appspotRepository.fetchData().onEach { dataState ->
                if (dataState !is DataState.Loading) {
                    assert(dataState is DataState.Error)
                }
            }.launchIn(this)
        }
    }

    @Test
    fun `fetch data, returns DataState Success`() {
        runBlockingTest {
            appspotRepository.fetchData().onEach { dataState ->
                if (dataState !is DataState.Loading) {
                    assert(dataState is DataState.Success)
                }
            }.launchIn(this)
        }
    }

    @Test
    fun `fetch data with wrong userId, returns DataState Success`() {
        fakeAppspotApi.shouldReturnWrongID
        runBlockingTest {
            appspotRepository.fetchData().onEach { dataState ->
                if (dataState !is DataState.Loading) {
                    assert(dataState is DataState.Success)
                }
            }.launchIn(this)
        }
    }

    @Test
    fun `findAllUsers, returns DataState Success`() {
        runBlockingTest {
            appspotRepository.findAllUsers().onEach { dataState ->
                if (dataState !is DataState.Loading) {
                    assert(dataState is DataState.Success)
                }
            }.launchIn(this)
        }
    }

    @Test
    fun `findAllUsers with error, returns DataState Error`() {
        fakeAppsDao.shouldReturnError = true
        runBlockingTest {
            appspotRepository.findAllUsers().onEach { dataState ->
                if (dataState !is DataState.Loading) {
                    assert(dataState is DataState.Error)
                }
            }.launchIn(this)
        }
    }

    @Test
    fun `findUserById with wrong user id, returns DataState Error`() {
        runBlockingTest {
            appspotRepository.findUserById(INVALID_USER_ID).onEach { dataState ->
                if (dataState !is DataState.Loading) {
                    assert(dataState is DataState.Error)
                }
            }.launchIn(this)
        }
    }

    @Test
    fun `findUserById with error, returns DataState Error`() {
        fakeAppsDao.shouldReturnError = true
        runBlockingTest {
            appspotRepository.findUserById(VALID_USER_ID).onEach { dataState ->
                if (dataState !is DataState.Loading) {
                    assert(dataState is DataState.Error)
                }
            }.launchIn(this)
        }
    }

    @Test
    fun `findUserById , returns DataState Success`() {
        runBlockingTest {
            appspotRepository.findUserById(VALID_USER_ID).onEach { dataState ->
                if (dataState !is DataState.Loading) {
                    assert(dataState is DataState.Success)
                }
            }.launchIn(this)
        }
    }


}