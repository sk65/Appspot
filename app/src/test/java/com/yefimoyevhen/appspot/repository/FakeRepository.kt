package com.yefimoyevhen.appspot.repository

import com.yefimoyevhen.appspot.database.model.User
import com.yefimoyevhen.appspot.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : AppspotRepository {

    var shouldReturnNetworkError = false

    private val users = getFakeUsers()

    private fun getFakeUsers(): List<User> = listOf(
        User("1", 34, "USA", "Jack", "Mail", "Black"),
        User("2", 24, "USA", "Kile", "Mail", "Ges"),
        User("3", 44, "Russia", "Adam", "Mail", "Smith"),
        User("4", 74, "Ukraine", "Nill", "Mail", "Gamon"),
        User("5", 39, "Poland", "Jack", "Mail", "Black"),
        User("6", 81, "China", "Sun", "Mail", "Hun"),
    )

    override suspend fun fetchData(): Flow<DataState<List<User>>> = flow {
        if (shouldReturnNetworkError) {
            emit(DataState.Error("Error"))
        } else {
            emit(DataState.Success(users))
        }
    }

    override suspend fun findAllUsers(): Flow<DataState<List<User>>> = flow {
        emit(DataState.Success(users))
    }

    override suspend fun findUserById(userId: String): Flow<DataState<User>> = flow {
        var user: User? = null
        users.forEach {
            if (it.id == userId) {
                user = it
                return@forEach
            }
        }
        if (user == null) {
            emit(DataState.Error(null))
        } else {
            emit(DataState.Success(user!!))
        }
    }
}


