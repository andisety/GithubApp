package com.andi.githubuserapplication.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andi.githubuserapplication.MainActivity
import com.andi.githubuserapplication.R
import com.andi.githubuserapplication.adapter.AdapterUserRoomEdit
import com.andi.githubuserapplication.data.local.entity.UserEntity
import com.andi.githubuserapplication.model.RoomViewModel
import com.andi.githubuserapplication.ui.detail.DetaiActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private lateinit var rcFav:RecyclerView
    private val roomViewModel: RoomViewModel by viewModels()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title="Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_favorite)
         rcFav= findViewById(R.id.rcFavorite)


        roomViewModel.getFavorite().observe(this){result->
            setDataUsers(result)

        }

    }
    private fun setDataUsers(users: List<UserEntity>){
        val userAdapter = AdapterUserRoomEdit(users,this,object:AdapterUserRoomEdit.OnAdapterListener{
            override fun itemClick(username: String) {
                val intent = Intent(this@FavoriteActivity, DetaiActivity::class.java)
                intent.putExtra(MainActivity.DATA,username)
                startActivity(intent)
            }
        })
        rcFav.adapter = userAdapter
        rcFav.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

