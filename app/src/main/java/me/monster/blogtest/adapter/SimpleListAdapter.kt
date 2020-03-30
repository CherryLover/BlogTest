package me.monster.blogtest.adapter

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.monster.blogtest.DiffListActivity
import me.monster.blogtest.type_image
import me.monster.blogtest.type_text

/**
 * @description
 * @author: Created jiangjiwei in 2020/3/23 23:04
 */

class SimpleDiffItemCallback : DiffUtil.ItemCallback<DiffListActivity.BaseType>() {
    override fun areItemsTheSame(
        oldItem: DiffListActivity.BaseType,
        newItem: DiffListActivity.BaseType
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: DiffListActivity.BaseType,
        newItem: DiffListActivity.BaseType
    ): Boolean {
        return if (oldItem.type != oldItem.type) {
            false
        } else {
            if (oldItem.type == type_text) {
                (oldItem as DiffListActivity.SimpleText).title.hashCode() == (newItem as DiffListActivity.SimpleText).title.hashCode()
            } else {
                (oldItem as DiffListActivity.SimpleImage).link.hashCode() == (newItem as DiffListActivity.SimpleImage).link.hashCode()
            }
        }
    }
}

class SimpleListAdapter : ListAdapter<DiffListActivity.BaseType, RecyclerView.ViewHolder>(
    SimpleDiffItemCallback()
) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == type_text) {
            SimpleListViewHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(R.layout.simple_list_item_1, parent, false)
            )
        } else {
            SimpleImageViewHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(me.monster.blogtest.R.layout.item_img_diff, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item.type == type_text) {
            (holder as SimpleListViewHolder).tvText.text =
                (item as DiffListActivity.SimpleText).title
        } else if (item.type == type_image) {
            val simpleImage = item as DiffListActivity.SimpleImage
            val ivDiff = (holder as SimpleImageViewHolder).ivDiff
            Glide.with(ivDiff)
                .asBitmap()
                .load(simpleImage.link)
                .into(ivDiff)
        }
    }
}

class SimpleListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvText: TextView = itemView.findViewById(android.R.id.text1)
}

class SimpleImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ivDiff = itemView.findViewById<ImageView>(me.monster.blogtest.R.id.ivDiff)
}