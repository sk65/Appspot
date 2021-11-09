package com.yefimoyevhen.appspot.repository

import com.yefimoyevhen.appspot.api.AppspotApi
import com.yefimoyevhen.appspot.api.toUser
import com.yefimoyevhen.appspot.api.toUsersId
import com.yefimoyevhen.appspot.database.AppspotDao
import com.yefimoyevhen.appspot.database.model.User
import com.yefimoyevhen.appspot.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: AppspotApi,
    private val dao: AppspotDao
) {

    suspend fun fetchData(): Flow<DataState<List<User>>> = flow {
        emit(DataState.Loading)
        try {
            val users = ArrayList<User>()
            api.getUsersIdResp().toUsersId().forEach {
                try {
                    users.add(api.getUserResp(it).toUser())
                } catch (e: Exception) {
                    //IGNORE
                    e.printStackTrace()
                }
            }
            emit(DataState.Success(users))
            dao.apply {
                deleteAllUsers()
                users.forEach { dao.insertUser(it) }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.localizedMessage))
        }
    }

    suspend fun findAllUsers(): Flow<DataState<List<User>>> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(dao.findAllUsers()))
        } catch (e: java.lang.Exception) {
            emit(DataState.Error(e.localizedMessage))
        }
    }

    suspend fun findUserById(userId: String): Flow<DataState<User>> = flow {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(dao.findUserById(userId)))
        } catch (e: java.lang.Exception) {
            emit(DataState.Error(e.localizedMessage))
        }
    }
}