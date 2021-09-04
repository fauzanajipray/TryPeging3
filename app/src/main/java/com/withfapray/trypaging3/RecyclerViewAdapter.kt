package com.withfapray.trypaging3

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.withfapray.trypaging3.databinding.RvRowBinding
import com.withfapray.trypaging3.network.CharacterData

class RecyclerViewAdapter : PagingDataAdapter<CharacterData, RecyclerViewAdapter.MyViewHolder>(
    DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = getItem(position) as CharacterData
        character.apply {
            holder.bind(this)

        }
    }

    inner class MyViewHolder(val binding : RvRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: CharacterData){
            Log.d("CEK_DATA",data.name as String)

            with(binding){
                tvName.text = data.name
                tvDesc.text = data.species
                Glide.with(itemView.context)
                    .load(data.image)
                    .circleCrop()
                    .into(imageView)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<CharacterData>(){
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.species == newItem.species &&
                    oldItem.image == newItem.image
        }

    }

}