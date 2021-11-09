package com.yefimoyevhen.appspot.api

import com.yefimoyevhen.appspot.api.model.UserResponse
import com.yefimoyevhen.appspot.api.model.UsersIdResponse
import com.yefimoyevhen.appspot.database.model.User


fun UsersIdResponse.toUsersId() = data

fun UserResponse.toUser() = with(data) {
    User(
        id,
        age,
        country,
        firstName,
        gender,
        lastName
    )
}

