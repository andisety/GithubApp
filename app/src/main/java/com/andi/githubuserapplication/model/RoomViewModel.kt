package com.andi.githubuserapplication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andi.githubuserapplication.data.remote.response.UserResponseDetail
import com.andi.githubuserapplication.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor (private val roomRepository: RoomRepository) : ViewModel() {

    fun getFavorite() = roomRepository.getFavorite()

    fun addFavorite(user: UserResponseDetail){
        roomRepository.addFavorite(user,true)
    }

    fun delete(login: String){
        roomRepository.deleteUser(login)
    }

    fun getUserFav(login:String):LiveData<Boolean>{
        return roomRepository.getuser(login)
    }


}