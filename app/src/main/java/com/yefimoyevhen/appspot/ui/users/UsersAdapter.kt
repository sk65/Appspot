package com.yefimoyevhen.appspot.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yefimoyevhen.appspot.databinding.ItemUserBinding
import com.yefimoyevhen.appspot.database.model.User


class UsersAdapter :
    ListAdapter<User, UsersAdapter.UsersViewHolder>(ItemCallback) {

    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ).let(::UsersViewHolder)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) =
        holder.onBind(getItem(position))

    inner class UsersViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: User) = with(binding) {
            firstName.text = item.firstName
            root.setOnClickListener {
                onItemClickListener?.let { it(item.id) }
            }
        }
    }

    companion object ItemCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }

}