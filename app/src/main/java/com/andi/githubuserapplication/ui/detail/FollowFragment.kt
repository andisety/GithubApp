package com.andi.githubuserapplication.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andi.githubuserapplication.MainActivity
import com.andi.githubuserapplication.R
import com.andi.githubuserapplication.adapter.AdapterUser
import com.andi.githubuserapplication.model.MainViewModel
import com.andi.githubuserapplication.model.response.UsersResponse

class FollowFragment : Fragment() {
    private lateinit var rcList:RecyclerView
    private lateinit var progressBar:ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        progressBar.visibility = View.VISIBLE
        val username = arguments?.getString(MainActivity.DATA)

        val mainModel=ViewModelProvider(this)[MainViewModel::class.java]
        mainModel.getFollowers(username!!)
        mainModel.followers.observe(viewLifecycleOwner){followers ->
            setDataUsers(followers)
        }
    }

    private fun initView(view: View){
        rcList = view.findViewById(R.id.rcList)
        progressBar = view.findViewById(R.id.progressBar)
    }
    private fun setDataUsers(users:UsersResponse){
        val userAdapter = AdapterUser(users,requireContext(),object: AdapterUser.OnAdapterListener{
            override fun itemClick(username: String) {
                val intent = Intent(requireContext(), DetaiActivity::class.java)
                intent.putExtra(MainActivity.DATA,username)
                startActivity(intent)
            }
        })
        rcList.adapter = userAdapter
        rcList.layoutManager = LinearLayoutManager(requireContext())
        progressBar.visibility = View.GONE
    }

}