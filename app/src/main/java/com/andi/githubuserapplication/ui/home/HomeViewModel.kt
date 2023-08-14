package com.andi.githubuserapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andi.githubuserapplication.data.remote.ApiResult
import com.andi.githubuserapplication.data.remote.response.SearchResponse
import com.andi.githubuserapplication.data.remote.response.UsersResponse
import com.andi.githubuserapplication.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: GithubRepository):ViewModel() {
    private val _response = MutableLiveData<ApiResult<UsersResponse>>()
    val response: LiveData<ApiResult<UsersResponse>> = _response
    private val _search = MutableLiveData<ApiResult<SearchResponse>>()
    val search: LiveData<ApiResult<SearchResponse>> = _search

    init {
        getUsers()
    }

     fun getUsers() = viewModelScope.launch {
        _response.postValue(ApiResult.Loading)
         try {
             repository.getUsers().let { response ->
                 if (response.isSuccessful) {
                     _response.postValue(ApiResult.Success(response.body()!!))
                 } else {
                     _response.postValue(ApiResult.Error(response.code().toString()))
                 }
             }
         }catch (e:Exception){
             _response.postValue(ApiResult.Error(e.message.toString()))
         }


    }

     fun searchUser(username: String) = viewModelScope.launch {
         _response.postValue(ApiResult.Loading)
         try {
             repository.getSearch(username).let { response ->
                 if (response.isSuccessful) {
                     _search.postValue(ApiResult.Success(response.body()!!))
                 } else {
                     _search.postValue(ApiResult.Error(response.code().toString()))
                 }
             }
         }catch (e:Exception){
             _search.postValue(ApiResult.Error(e.message.toString()))
         }
    }

}