package me.monster.blogtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_diff_list.*

class DiffListActivity : AppCompatActivity() {

    private val numberList = mutableListOf<SimpleText>()
    private val simpleAdapter = SimpleAdapter(numberList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_list)

        btnAdd.setOnClickListener {
            add()
        }

        btnMinus.setOnClickListener {
            minus()
        }

        btnEdit.setOnClickListener {
            edit()
        }

        rv_main_diff.also {
            it.adapter = simpleAdapter
            it.layoutManager = LinearLayoutManager(this)
            it.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }
        prepareList()
    }

    /**
     * 编辑倒数第二个元素
     */
    private fun edit() {
        val tmpList = MutableList(10) {
            return@MutableList numberList[it]
        }
        tmpList[tmpList.size -2] = SimpleText("New Edit To")
        updateDiff(tmpList)
    }

    /**
     * 在列表前加上 20 个元素
     */
    private fun add() {
        val tmpList = MutableList(10) {
            return@MutableList numberList[it]
        }
        for (i in 0 until 20) {
            tmpList.add(0, SimpleText(("Add Item $i")))
        }
        updateDiff(tmpList)
    }

    /**
     * 减去列表中最前面的 10 个元素
     */
    private fun minus() {
        if (numberList.size >= 10) {
            val tmpList = MutableList(10) {
                return@MutableList numberList[it]
            }
            updateDiff(tmpList.subList(0, 10))
        }
    }

    private fun prepareList() {
        for (i in 1..10) {
            numberList.add(SimpleText("Tittle $i"))
        }
    }

    private fun updateDiff(tmpList: List<SimpleText>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(numberList, tmpList))
        numberList.clear()
        numberList.addAll(tmpList)
        diffResult.dispatchUpdatesTo(simpleAdapter)
    }

    data class SimpleText(var title: String)

    class DiffCallback(private val oldList: List<SimpleText>, private val newList: List<SimpleText>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title.hashCode() == newList[newItemPosition].title.hashCode()
        }
    }
}
