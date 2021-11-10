package com.yefimoyevhen.appspot.database

import com.yefimoyevhen.appspot.database.model.User
import com.yefimoyevhen.appspot.model.UserDTO

fun List<User>.toUserDTOList(): List<UserDTO> {
    val users = ArrayList<UserDTO>()
    forEach { user ->
        users.add(
            UserDTO(
                user.id,
                user.age,
                user.country,
                user.firstName,
                user.gender,
                user.lastName
            )
        )
    }
    return users
}

fun User.toUserDTO() = UserDTO(
    id,
    age,
    country,
    firstName,
    gender,
    lastName
)