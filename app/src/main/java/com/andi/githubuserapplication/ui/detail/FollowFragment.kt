package com.andi.githubuserapplication.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andi.githubuserapplication.MainActivity
import com.andi.githubuserapplication.R
import com.andi.githubuserapplication.adapter.AdapterHome
import com.andi.githubuserapplication.data.remote.ApiResult
import com.andi.githubuserapplication.data.remote.response.UserResponse
import com.andi.githubuserapplication.databinding.FragmentFollowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var rcList:RecyclerView
    private lateinit var progressBar:ProgressBar
    private val detailViewModel:DetailViewModel by viewModels()
    private lateinit var adapterHome: AdapterHome


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        progressBar.visibility = View.VISIBLE
        val username = arguments?.getString(MainActivity.DATA)

        detailViewModel.getFollower(username!!)
        detailViewModel.followers.observe(viewLifecycleOwner){follower->
            when(follower){
                is ApiResult.Loading->{
                    showLoading(true)
                }
                is ApiResult.Error->{
                    showLoading(false)
                }
                is ApiResult.Success->{
                    showLoading(false)
                    setupRv(follower.data)
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun initView(view: View){
        rcList = view.findViewById(R.id.rcList)
        progressBar = view.findViewById(R.id.progressBar)
    }
    private fun setupRv(users:List<UserResponse>) {
        adapterHome = AdapterHome()
        adapterHome.users = users
        binding.rcList.apply {
            adapter = adapterHome
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        }

    }

}