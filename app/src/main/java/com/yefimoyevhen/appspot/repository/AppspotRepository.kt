package com.yefimoyevhen.appspot.repository

import com.yefimoyevhen.appspot.database.model.User
import com.yefimoyevhen.appspot.util.DataState
import kotlinx.coroutines.flow.Flow

interface AppspotRepository {

    suspend fun fetchData(): Flow<DataState<List<User>>>
    suspend fun findAllUsers(): Flow<DataState<List<User>>>
    suspend fun findUserById(userId: String): Flow<DataState<User>>
}