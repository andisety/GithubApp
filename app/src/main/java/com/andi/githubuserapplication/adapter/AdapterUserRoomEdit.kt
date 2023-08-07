package com.andi.githubuserapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andi.githubuserapplication.R
import com.andi.githubuserapplication.data.local.entity.UserEntity
import com.bumptech.glide.Glide

class AdapterUserRoomEdit(private var users:List<UserEntity>,
                          private val context:Context, private val listener:OnAdapterListener
                  ): RecyclerView.Adapter<AdapterUserRoomEdit.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.name.text = user.login
        holder.type.text = user.type
        Glide.with(context)
            .load(user.avatar_url)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            listener.itemClick(user.login)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val name:TextView = itemView.findViewById(R.id.tvName)
        val img:ImageView = itemView.findViewById(R.id.imageView)
        val type:TextView = itemView.findViewById(R.id.tvUser)
    }

    interface OnAdapterListener{
        fun itemClick(username:String)
    }

}
