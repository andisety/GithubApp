package com.andi.githubuserapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andi.githubuserapplication.data.remote.response.UserResponse
import com.andi.githubuserapplication.databinding.ItemUserBinding
import com.andi.githubuserapplication.ui.detail.DetaiActivity
import com.bumptech.glide.Glide

class AdapterHome:RecyclerView.Adapter<AdapterHome.ViewHolder>() {
    inner class ViewHolder(val binding:ItemUserBinding) :RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object :DiffUtil.ItemCallback<UserResponse>(){
        override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return newItem.id==oldItem.id
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)
    var users:List<UserResponse>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun getItemCount(): Int {
       return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = users[position]
        Glide.with(holder.itemView.context)
            .load(currentUser.avatarUrl)
            .into(holder.binding.imageView)
        holder.binding.apply {
            tvName.text = currentUser.login
            tvUser.text = currentUser.type

        }
        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetaiActivity::class.java)
            intent.putExtra("DATA",currentUser.login)
            context.startActivity(intent)
        }

    }


}