package com.andi.githubuserapplication.network

import com.andi.githubuserapplication.model.response.SearchResponse
import com.andi.githubuserapplication.model.response.UserResponseDetail
import com.andi.githubuserapplication.model.response.UsersResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

companion object{
    const val BASE_URL = "https://api.github.com/"
}
    @GET("users")
    suspend fun getUsers():Response<UsersResponse>

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username:String
    ):Response<UserResponseDetail>

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ):Response<UsersResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ):Response<UsersResponse>

    @GET("search/users{username}")
    suspend fun getUsersSearch(
        @Path("username") username : String,
        @Query("q")q:String

    ):Response<SearchResponse>
}