package com.andi.githubuserapplication.repository

import com.andi.githubuserapplication.network.ApiService
import javax.inject.Inject

class GithubRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()
}