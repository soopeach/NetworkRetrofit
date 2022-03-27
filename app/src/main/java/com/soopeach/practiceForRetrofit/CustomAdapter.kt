package com.soopeach.practiceForRetrofit

import Repository
import RepositoryItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soopeach.practiceForRetrofit.databinding.ItemRecyclerBinding

class CustomAdapter: RecyclerView.Adapter<Holder>() {
    var userList : Repository? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = userList?.get(position)
        holder.setUser(user)
    }

    override fun getItemCount(): Int {
        return userList?.size?: 0
    }
}
class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root){

    fun setUser(user: RepositoryItem?){
        user?.let {
            binding.textName.text = user.name
            binding.textId.text = user.node_id
            binding.texturl.text = user.url
            Glide.with(binding.imageAvater).load(user.owner.avatar_url).into(binding.imageAvater)
        }
    }
}