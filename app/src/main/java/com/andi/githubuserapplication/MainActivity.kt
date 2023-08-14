package com.andi.githubuserapplication

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andi.githubuserapplication.adapter.AdapterHome
import com.andi.githubuserapplication.data.remote.ApiResult
import com.andi.githubuserapplication.data.remote.response.UserResponse
import com.andi.githubuserapplication.databinding.ActivityMainBinding
import com.andi.githubuserapplication.setting.SettingActivity
import com.andi.githubuserapplication.setting.SettingPreferences
import com.andi.githubuserapplication.setting.ViewModelFactory
import com.andi.githubuserapplication.setting.ViewModelSetting
import com.andi.githubuserapplication.ui.favorite.FavoriteActivity
import com.andi.githubuserapplication.ui.home.HomeViewModel
import com.andi.githubuserapplication.ui.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

private val Context.dataStore: DataStore<Preferences> by  preferencesDataStore(name = "settings")

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel:HomeViewModel by viewModels()
    private lateinit var adapterHome: AdapterHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""

        val pref = SettingPreferences.getInstance(dataStore)
        viewModelSetting = ViewModelProvider(this, ViewModelFactory(pref))[ViewModelSetting::class.java]
        viewModelSetting.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        viewModel.response.observe(this){users->
            when(users){
                is ApiResult.Loading->{
                    showLoading(true)
                }
                is ApiResult.Error->{
                    showLoading(false)
                }
                is ApiResult.Success->{
                    showLoading(false)
                    setupRv(users.data)
                }
            }
        }
        viewModel.search.observe(this@MainActivity){users ->
            binding.apply {
                ivTitle.visibility = View.VISIBLE
                tvTitle.visibility = View.VISIBLE
                tvsubTitle.visibility = View.VISIBLE
            }
            when(users){
                is ApiResult.Loading->{
                    showLoading(true)
                }
                is ApiResult.Error->{
                    showLoading(false)
                }
                is ApiResult.Success->{
                    showLoading(false)
                    setupRv(users.data.items)
                }
            }
        }
    }

    private fun setupRv(users:List<UserResponse>) {
        adapterHome = AdapterHome()
        adapterHome.users = users
        binding.rcList.apply {
            adapter = adapterHome
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu?.findItem(R.id.search)
        val searchView = searchMenuItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query!!)
                binding.apply {
                    ivTitle.visibility = View.GONE
                    tvTitle.visibility = View.GONE
                    tvsubTitle.visibility = View.GONE
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                viewModel.getUsers()
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting->{
                startActivity(Intent(this, SettingActivity::class.java))
            }
            R.id.favorite->{
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
            R.id.profile->{
                startActivity(Intent(this, ProfileActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val DATA="DATA"
        lateinit var viewModelSetting:ViewModelSetting
    }

}