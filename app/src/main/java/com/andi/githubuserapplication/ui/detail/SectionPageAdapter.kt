package com.andi.githubuserapplication.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andi.githubuserapplication.MainActivity

class SectionPageAdapter(activity:AppCompatActivity):FragmentStateAdapter(activity) {

    lateinit var username:String
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment :Fragment? = null
        val bundle = Bundle()

        when(position){
            0 -> {
                fragment = FollowFragment()
                bundle.putString(MainActivity.DATA,username)
                fragment.arguments = bundle

            }
            1 -> {
                fragment = FollowingFragment()
                bundle.putString(MainActivity.DATA,username)
                fragment.arguments = bundle
            }
        }
        return fragment as Fragment
    }

    fun setUpData(data:String){
        username = data
    }

}