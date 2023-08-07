package com.andi.githubuserapplication.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andi.githubuserapplication.databinding.ItemProjectBinding
import com.andi.githubuserapplication.ui.profile.Project

class AdapterProject(private var projects:List<Project>): RecyclerView.Adapter<AdapterProject.ViewHolder>(){

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        holder.bind(project)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    class ViewHolder(private val binding:ItemProjectBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(project: Project){
            binding.imageView4.setImageResource(project.img)
            binding.imageView4.setOnClickListener {
                val context = binding.imageView4.context
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(project.url)
                context.startActivity(intent)
            }
        }
    }


}
