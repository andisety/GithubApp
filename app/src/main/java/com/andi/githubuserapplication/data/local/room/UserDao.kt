package com.andi.githubuserapplication.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.andi.githubuserapplication.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM githubApp")
    fun getUser(): LiveData<List<UserEntity>>

    @Query("DELETE FROM githubApp WHERE githubApp.login = :loginn")
    fun delete(loginn:String)

    @Query("SELECT EXISTS(SELECT * FROM githubApp WHERE login =:login)")
    fun isUserFavorite(login: String): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserFavorite(user: UserEntity)


}