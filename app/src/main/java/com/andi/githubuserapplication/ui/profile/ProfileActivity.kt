package com.andi.githubuserapplication.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andi.githubuserapplication.R
import com.andi.githubuserapplication.adapter.AdapterProject
import com.andi.githubuserapplication.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var binding:ActivityProfileBinding
    private lateinit var rcFav:RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title="Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val projects = listOf(
            Project(R.drawable.apk_github,"https://github.com/andisety/GithubApp"),
            Project(R.drawable.epatrol,"https://github.com/andisety/E-Patrol"),
            Project(R.drawable.protectme,"https://github.com/AndreMaulaRaufiq/ProtecMe"),
            Project(R.drawable.nutripal,"https://github.com/C23PR533/NutriPal-Android"),
            Project(R.drawable.weatherapp,"https://github.com/andisety/Weather"),
            Project(R.drawable.adminperpus,"https://github.com/andisety/admin-perpustakaan"),
        )
        setData(projects)


    }
    private fun setData(projects: List<Project>){
       val adapterProject = AdapterProject(projects)
        val lm = GridLayoutManager(this,2)
        binding.rcList.layoutManager = lm
        binding.rcList.adapter = adapterProject
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}

