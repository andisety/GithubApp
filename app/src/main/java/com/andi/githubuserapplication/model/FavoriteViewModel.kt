package com.andi.githubuserapplication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andi.githubuserapplication.data.GithubRepository
import com.andi.githubuserapplication.model.response.UserResponseDetail

class FavoriteViewModel  (private val githubRepository: GithubRepository) : ViewModel() {

    fun getFavorite() = githubRepository.getFavorite()

    fun addFavorite(user: UserResponseDetail){
        githubRepository.addFavorite(user,true)
    }

    fun delete(login: String){
        githubRepository.deleteUser(login)
    }

    fun getUserFav(login:String):LiveData<Boolean>{
        return githubRepository.getuser(login)
    }


}