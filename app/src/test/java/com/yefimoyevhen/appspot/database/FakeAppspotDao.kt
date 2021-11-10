package com.yefimoyevhen.appspot.database

import com.yefimoyevhen.appspot.database.model.User

const val VALID_USER_ID = "1"
const val INVALID_USER_ID = "1875"

class FakeAppspotDao : AppspotDao {

    private val users: MutableList<User> = getFakeUsers()
    var shouldReturnError = false
    override suspend fun insertUser(user: User) {
        users.add(user)
    }

    override suspend fun deleteAllUsers() =
        users.clear()


    override suspend fun findAllUsers(): List<User> {
        if (shouldReturnError) {
            throw Exception()
        }
        return users
    }

    override suspend fun findUserById(id: String): User {
        if (shouldReturnError) {
            throw Exception()
        }
        users.forEach {
            if (it.id == id) {
                return it
            }
        }
        throw IllegalStateException()
    }

    private fun getFakeUsers(): MutableList<User> = mutableListOf(
        User("1", 34, "USA", "Jack", "Mail", "Black"),
        User("2", 24, "USA", "Kile", "Mail", "Ges"),
        User("3", 44, "Russia", "Adam", "Mail", "Smith"),
        User("4", 74, "Ukraine", "Nill", "Mail", "Gamon"),
        User("5", 39, "Poland", "Jack", "Mail", "Black"),
        User("6", 81, "China", "Sun", "Mail", "Hun"),
    )
}