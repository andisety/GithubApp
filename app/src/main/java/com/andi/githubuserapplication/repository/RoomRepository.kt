package com.andi.githubuserapplication.repository

import androidx.lifecycle.LiveData
import com.andi.githubuserapplication.data.local.entity.UserEntity
import com.andi.githubuserapplication.data.local.room.UserDao
import com.andi.githubuserapplication.data.remote.response.UserResponseDetail
import com.andi.githubuserapplication.utils.AppExecutors
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val userDao: UserDao,
    private val appExecutors: AppExecutors
) {

    fun getFavorite(): LiveData<List<UserEntity>> {
        return userDao.getUser()
    }

    fun addFavorite(user: UserResponseDetail, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            val userFavorite = UserEntity(
                user.id,
                user.avatarUrl,
                user.login,
                user.type,
                favoriteState
            )
            userDao.insertUserFavorite(userFavorite)
        }
    }

    fun getuser(login:String): LiveData<Boolean> {
        return userDao.isUserFavorite(login)
    }

    fun deleteUser(login:String){
        appExecutors.diskIO.execute {
            userDao.delete(login)
        }
    }

}