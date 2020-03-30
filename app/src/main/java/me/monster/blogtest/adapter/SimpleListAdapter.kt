package me.monster.blogtest.adapter

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.monster.blogtest.DiffListActivity

/**
 * @description
 * @author: Created jiangjiwei in 2020/3/23 23:04
 */

class SimpleDiffItemCallback : DiffUtil.ItemCallback<DiffListActivity.SimpleText>() {
    override fun areItemsTheSame(oldItem: DiffListActivity.SimpleText, newItem: DiffListActivity.SimpleText): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DiffListActivity.SimpleText, newItem: DiffListActivity.SimpleText): Boolean {
        return oldItem.title.hashCode() == newItem.title.hashCode()
    }
}

class SimpleListAdapter : ListAdapter<DiffListActivity.SimpleText, SimpleListViewHolder>(
    SimpleDiffItemCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleListViewHolder {
        return SimpleListViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.simple_list_item_1, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SimpleListViewHolder, position: Int) {
        holder.tvText.text = getItem(position).title
    }
}

class SimpleListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tvText: TextView = itemView.findViewById(android.R.id.text1)
}