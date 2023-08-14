package com.andi.githubuserapplication.ui.detail



import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.andi.githubuserapplication.MainActivity
import com.andi.githubuserapplication.R
import com.andi.githubuserapplication.databinding.ActivityDetaiBinding
import com.andi.githubuserapplication.model.FavoriteViewModel
import com.andi.githubuserapplication.model.response.UserResponseDetail
import com.andi.githubuserapplication.network.ApiResult
import com.andi.githubuserapplication.ui.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetaiActivity : AppCompatActivity() {

    private var isFavorite:Boolean = false
    lateinit var favorite:UserResponseDetail
    private val detailViewModel:DetailViewModel by viewModels()
    private lateinit var binding:ActivityDetaiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(MainActivity.DATA)
        supportActionBar?.title=username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel.getUser(username!!)
        detailViewModel.userDetail.observe(this){detail->
            when(detail){
                is ApiResult.Loading->{
                    showLoading(true)
                }
                is ApiResult.Error->{
                    showLoading(false)
                }
                is ApiResult.Success->{
                    showLoading(false)
                    setDetailUser(detail.data)
                    favorite = detail.data
                }
            }
        }

        val factory : ViewModelFactory = ViewModelFactory.getInstance(this)
        val favViewModel:FavoriteViewModel by  viewModels{
            factory
        }



       favViewModel.getUserFav(username).observe(this) { userFav ->
           if (userFav) {
               binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
               isFavorite = true
               binding.ivFavorite.setOnClickListener {
                   favViewModel.delete(username)
                   isFavorite = false
                   Toast.makeText(applicationContext,"Removed",Toast.LENGTH_SHORT).show()
               }
           } else {
               isFavorite = false
               binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
               binding.ivFavorite.setOnClickListener {
                   favViewModel.addFavorite(favorite)
                   isFavorite = true
                   Toast.makeText(applicationContext,"Added",Toast.LENGTH_SHORT).show()
               }
           }
       }

        val adapter = SectionPageAdapter(this)
        val title = arrayOf("Followers","Following")
        adapter.setUpData(username)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tbLayout,binding.viewPager){tab,position ->
            tab.text = title[position]
        }.attach()



    }


    private fun setDetailUser(user:UserResponseDetail?) {

        binding.apply {
            tvName.text = user?.name
            tvUsername.text = user?.login
            tvRepo.text = user?.publicRepos.toString()
            tvFollowing.text = user?.following.toString()
            tvFollowers.text = user?.followers.toString()
        }


        if (user?.location == null){
            binding.tvLocation.visibility = View.GONE
            binding.tvLocation.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        }else{
            binding.tvLocation.text = user.location
            binding.tvLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location,0,0,0)
        }

        if (user?.company == null){
            binding.tvCompany.visibility = View.GONE
            binding.tvCompany.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        }else{
            binding.tvCompany.text = user.company
            binding.tvCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_work,0,0,0)
        }
        Glide.with(this@DetaiActivity)
            .load(user?.avatarUrl)
            .into(binding.ivProfile)
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.ivFavorite.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.ivFavorite.visibility = View.VISIBLE
        }
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