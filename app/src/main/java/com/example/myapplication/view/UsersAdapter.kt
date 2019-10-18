package com.example.myapplication.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.data.response.User
import kotlinx.android.synthetic.main.item_user.view.*
import kotlin.math.roundToInt

class UsersAdapter() : ListAdapter<User, UserViewHolder>(UserDiffUtil()) {
    var width: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    private val requestOptions = RequestOptions().apply {
        transform(CircleCrop())
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.root.user_name.text = item.name
        Glide.with(holder.root.context).load(item.image)
            .apply(requestOptions)
            .into(holder.root.user_avatar)
        val userItemSize = width / 2
        holder.bindItems(item.items, userItemSize)
    }
}

class UserDiffUtil : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}

fun Int.isEven() = this and 1 == 0

class UserViewHolder(val root: View) : RecyclerView.ViewHolder(root) {

    init {
        root.user_items_list.layoutManager = GridLayoutManager(root.context, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (!itemCount.isEven() && position == 0) 2 else 1
                }

            }
        }
    }

    fun bindItems(items: List<String>, userItemSize: Int) {
        val listHeight = userItemSize * (items.count().toDouble() / 2).roundToInt()
        val adapter = UserItemAdapter(items, userItemSize)
        root.user_items_list.adapter = adapter
        root.user_items_list.layoutParams = root.user_items_list.layoutParams.apply {
            height = listHeight
        }
    }
}

class UserItemAdapter(
    private val items: List<String>,
    private val userItemSize: Int
) :
    RecyclerView.Adapter<UserItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val imageView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_user_image,
            parent,
            false
        ) as ImageView
        imageView.layoutParams = imageView.layoutParams.apply {
            height = userItemSize
//            width = userItemSize
        }
        return UserItemViewHolder(imageView)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        Glide.with(holder.image.context).load(items[position]).into(holder.image)
    }


}

class UserItemViewHolder(val image: ImageView) : RecyclerView.ViewHolder(image) {

}
