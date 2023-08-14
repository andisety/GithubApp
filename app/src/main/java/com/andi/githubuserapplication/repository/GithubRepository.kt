package com.andi.githubuserapplication.repository

import com.andi.githubuserapplication.network.ApiService
import javax.inject.Inject

class GithubRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()
    suspend fun getSearch(username:String) =apiService.getUsersSearch("",username)
    suspend fun getUser(username:String) =apiService.getUserDetail(username)
    suspend fun getFollower(username:String) =apiService.getFollowers(username)
    suspend fun getFollowing(username:String) =apiService.getFollowing(username)
}