package com.andi.githubuserapplication

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andi.githubuserapplication.adapter.AdapterUser
import com.andi.githubuserapplication.databinding.ActivityMainBinding
import com.andi.githubuserapplication.model.MainViewModel
import com.andi.githubuserapplication.model.response.UsersResponse
import com.andi.githubuserapplication.setting.SettingActivity
import com.andi.githubuserapplication.setting.SettingPreferences
import com.andi.githubuserapplication.setting.ViewModelFactory
import com.andi.githubuserapplication.setting.ViewModelSetting
import com.andi.githubuserapplication.ui.detail.DetaiActivity
import com.andi.githubuserapplication.ui.favorite.FavoriteActivity
import com.andi.githubuserapplication.ui.profile.ProfileActivity

private val Context.dataStore: DataStore<Preferences> by  preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""

        val pref = SettingPreferences.getInstance(dataStore)

        mainModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        viewModelSetting = ViewModelProvider(this, ViewModelFactory(pref))[ViewModelSetting::class.java]

        viewModelSetting.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainModel.users.observe(this){users ->
            setDataUsers(users)
        }
        mainModel.search.observe(this@MainActivity){search->
            setDataUsers(search.items)
        }
        mainModel.isLoading.observe(this){isloading->
            showLoading(isloading)
        }
    }

    private fun setDataUsers(users:UsersResponse){
         val userAdapter = AdapterUser(users,this,object:AdapterUser.OnAdapterListener{
            override fun itemClick(username: String) {
                val intent = Intent(this@MainActivity, DetaiActivity::class.java)
                intent.putExtra(DATA,username)
                startActivity(intent)
            }
        })
        binding.rcList.adapter = userAdapter
        binding.rcList.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainModel.getUserSearch(query!!)
                binding.apply {
                    ivTitle.visibility = View.GONE
                    tvTitle.visibility = View.GONE
                    tvsubTitle.visibility = View.GONE
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                mainModel.users.observe(this@MainActivity){users ->
                    binding.apply {
                        ivTitle.visibility = View.VISIBLE
                        tvTitle.visibility = View.VISIBLE
                        tvsubTitle.visibility = View.VISIBLE
                    }
                    setDataUsers(users)
                }
                return false
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
        lateinit var mainModel:MainViewModel
        lateinit var viewModelSetting:ViewModelSetting
    }

}