package com.yefimoyevhen.appspot.api

import com.yefimoyevhen.appspot.api.model.UserResponse
import com.yefimoyevhen.appspot.api.model.UsersIdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AppspotApi {
    @GET("/list/")
    suspend fun getUsersIdResp(): UsersIdResponse

    @GET("/get/{id}")
    suspend fun getUserResp(
        @Path("id") id: String,
    ):UserResponse
}