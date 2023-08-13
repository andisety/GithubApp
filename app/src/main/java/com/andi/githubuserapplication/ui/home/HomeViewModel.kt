package com.andi.githubuserapplication.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andi.githubuserapplication.model.response.UsersResponse
import com.andi.githubuserapplication.repository.GithubRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: GithubRepository):ViewModel() {
    private val _response = MutableLiveData<UsersResponse>()
    val response: LiveData<UsersResponse> = _response

    init {
        getUsers()
    }

    private fun getUsers() = viewModelScope.launch {
        repository.getUsers().let { response->
            if (response.isSuccessful){
                _response.postValue(response.body())
            }else{
                Log.e("Error","Error: ${response.code()}")
            }
    }

    }

}