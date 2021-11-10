package com.yefimoyevhen.appspot.api

import com.yefimoyevhen.appspot.api.model.Data
import com.yefimoyevhen.appspot.api.model.UserResponse
import com.yefimoyevhen.appspot.api.model.UsersIdResponse

const val SUCCESS = "success"
const val WRONG_ID = "55"

class FakeAppspotApi : AppspotApi {
    private val usersId = arrayOf("1", "2", "3", "4", "5", "6")
    private val usersWithWrongId = arrayOf("1", WRONG_ID, "3", "4", "5", "6")

    var shouldReturnNetworkError = false
    var shouldReturnWrongID = false

    override suspend fun getUsersIdResp(): UsersIdResponse {
        if (shouldReturnNetworkError) {
            throw IllegalStateException()
        }
        return getFakeUsersIdResp()
    }

    override suspend fun getUserResp(id: String): UserResponse {
        getFakeData().forEach {
            if (id == it.id)
                return UserResponse(it, SUCCESS)
        }
        throw java.lang.IllegalStateException()
    }

    private fun getFakeUsersIdResp(): UsersIdResponse = if (shouldReturnWrongID) {
        UsersIdResponse(usersWithWrongId.toList(), SUCCESS)
    } else {
        UsersIdResponse(usersId.toList(), SUCCESS)
    }

    private fun getFakeData(): MutableList<Data> = mutableListOf(
        Data(34, "USA", "Jack", "Mail", "1", "Black"),
        Data(24, "USA", "Kile", "Mail", "2", "Ges"),
        Data(44, "Russia", "Adam", "Mail", "3", "Smith"),
        Data(74, "Ukraine", "Nill", "Mail", "4", "Gamon"),
        Data(39, "Poland", "Jack", "Mail", "5", "Black"),
        Data(81, "China", "Sun", "Mail", "6", "Hun"),
    )
}
