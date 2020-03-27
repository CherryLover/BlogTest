package me.monster.blogtest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @description
 * @author: Created jiangjiwei in 2020/3/23 16:30
 */
class SimpleAdapter(private val list: List<DiffListActivity.SimpleText>) : RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {
    val tag = "SimpleAdapter"

    class SimpleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvText: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        Log.e(tag, "onCreateViewHolder ")
        return SimpleViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        Log.d(tag, "onBindViewHolder $position")
        holder.tvText.text = list[position].title
    }
}