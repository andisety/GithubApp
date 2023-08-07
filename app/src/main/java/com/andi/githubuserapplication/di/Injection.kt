package com.andi.githubuserapplication.di

import android.content.Context
import com.andi.githubuserapplication.data.GithubRepository
import com.andi.githubuserapplication.data.room.UserDatabase
import com.andi.githubuserapplication.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): GithubRepository {
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        val appExecutors = AppExecutors()
        return GithubRepository.getInstance( dao, appExecutors)
    }
}