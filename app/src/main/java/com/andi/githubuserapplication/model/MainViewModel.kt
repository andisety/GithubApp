package com.andi.githubuserapplication.model


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andi.githubuserapplication.model.response.SearchResponse
import com.andi.githubuserapplication.model.response.UserResponseDetail
import com.andi.githubuserapplication.model.response.UsersResponse
import com.andi.githubuserapplication.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<UsersResponse>()
     val users:LiveData<UsersResponse> = _users

    private val _userDetail = MutableLiveData<UserResponseDetail>()

     val userDetail:LiveData<UserResponseDetail> = _userDetail

    private val _followers = MutableLiveData<UsersResponse>()
     val followers:LiveData<UsersResponse> = _followers

    private val _following = MutableLiveData<UsersResponse>()
     val following:LiveData<UsersResponse> = _following

    private val _search = MutableLiveData<SearchResponse>()
     val search:LiveData<SearchResponse> = _search

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading




    init {
//        getUsers()
    }

//    private fun getUsers()   {
//        _isLoading.value = true
//
//        val client = ApiConfig.getApiService().getUsers()
//        client.enqueue(object: Callback<UsersResponse>{
//            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
//                if (response.isSuccessful){
//
//                    _isLoading.value = false
//                    _users.value = response.body()
//                }
//            }
//
//            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
//                Log.e(GET_USER,t.toString())
//                _isLoading.value = false
//            }
//
//        })
//
//
//    }

     fun getUserDetail(username:String){
         _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object: Callback<UserResponseDetail>{
            override fun onResponse(
                call: Call<UserResponseDetail>,
                response: Response<UserResponseDetail>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _userDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserResponseDetail>, t: Throwable) {
                Log.e(GET_USER_DETAIL,t.toString())
                _isLoading.value = false
            }
        })
    }

    fun getFollowers(username:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object :Callback<UsersResponse>{
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _followers.value = response.body()
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e(GET_FOLLOWERS,t.toString())
                _isLoading.value = false
            }

        })
    }

    fun getFollowing(username:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object :Callback<UsersResponse>{
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _following.value = response.body()
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e(GET_FOLLOWING,t.toString())
                _isLoading.value = false
            }
        })
    }

    fun getUserSearch(username:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersSearch("",username)
        client.enqueue(object :Callback<SearchResponse>{
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _search.value = response.body()
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(GET_FOLLOWING,t.toString())
                _isLoading.value = false
            }

        })
    }

    companion object{
        const val GET_USER="GET USER"
        const val GET_USER_DETAIL="GET USER DETAIL"
        const val GET_FOLLOWERS="GET FOLLOWERS"
        const val GET_FOLLOWING="GET FOLLOWING"
    }



}