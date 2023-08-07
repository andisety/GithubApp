package com.andi.githubuserapplication.network

import com.andi.githubuserapplication.model.response.SearchResponse
import com.andi.githubuserapplication.model.response.UserResponseDetail
import com.andi.githubuserapplication.model.response.UsersResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

companion object;
    @GET("users")
    fun getUsers():Call<UsersResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username:String
    ):Call<UserResponseDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ):Call<UsersResponse>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ):Call<UsersResponse>

    @GET("search/users{username}")
    fun getUsersSearch(
        @Path("username") username : String,
        @Query("q")q:String

    ):Call<SearchResponse>
}