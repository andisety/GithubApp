package com.andi.githubuserapplication.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andi.githubuserapplication.model.response.UsersResponse
import com.andi.githubuserapplication.repository.GithubRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.andi.githubuserapplication.model.response.SearchResponse
import com.andi.githubuserapplication.model.response.UserResponseDetail
import com.andi.githubuserapplication.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: GithubRepository,
    ):ViewModel() {
    private val _userDetail = MutableLiveData<ApiResult<UserResponseDetail>>()
    val userDetail:LiveData<ApiResult<UserResponseDetail>> = _userDetail
    private val _followers = MutableLiveData<ApiResult<UsersResponse>>()
    val followers:LiveData<ApiResult<UsersResponse>> = _followers

    private val _following = MutableLiveData<ApiResult<UsersResponse>>()
    val following:LiveData<ApiResult<UsersResponse>> = _following



     fun getUser(username: String) = viewModelScope.launch {
         _userDetail.postValue(ApiResult.Loading)
         try {
             repository.getUser(username).let { response ->
                 if (response.isSuccessful) {
                     _userDetail.postValue(ApiResult.Success(response.body()!!))
                 } else {
                     _userDetail.postValue(ApiResult.Error(response.code().toString()))
                 }
             }
         }catch (e:Exception){
             _userDetail.postValue(ApiResult.Error(e.message.toString()))
         }


    }
     fun getFollower(username: String) = viewModelScope.launch {
         _followers.postValue(ApiResult.Loading)
         try {
             repository.getFollower(username).let { response ->
                 if (response.isSuccessful) {
                     _followers.postValue(ApiResult.Success(response.body()!!))
                 } else {
                     _followers.postValue(ApiResult.Error(response.code().toString()))
                 }
             }
         }catch (e:Exception){
             _followers.postValue(ApiResult.Error(e.message.toString()))
         }


    }
     fun getFollowing(username: String) = viewModelScope.launch {
         _following.postValue(ApiResult.Loading)
         try {
             repository.getFollowing(username).let { response ->
                 if (response.isSuccessful) {
                     _following.postValue(ApiResult.Success(response.body()!!))
                 } else {
                     _following.postValue(ApiResult.Error(response.code().toString()))
                 }
             }
         }catch (e:Exception){
             _following.postValue(ApiResult.Error(e.message.toString()))
         }


    }



}