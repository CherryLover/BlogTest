package me.monster.blogtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_diff_list.*
import java.util.*

class DiffListActivity : AppCompatActivity() {

    private val numberList = mutableListOf<SimpleText>()
    private val simpleListAdapter = SimpleListAdapter()

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
            it.adapter = simpleListAdapter
            it.layoutManager = LinearLayoutManager(this)
            it.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }
        prepareList()
        simpleListAdapter.submitList(numberList)
    }

    /**
     * 编辑倒数第二个元素
     */
    private fun edit() {
        val tmpList = MutableList(10) {
            return@MutableList numberList[it]
        }
        tmpList[tmpList.size -2] = SimpleText("New Edit To")
        simpleListAdapter.submitList(tmpList)
    }

    /**
     * 在列表前加上 20 个元素
     */
    private fun add() {
        val tmpList = MutableList(10) {
            return@MutableList numberList[it]
        }
        for (i in 0 until 20) {
            val element = SimpleText(("Add Item $i"))
            tmpList.add(0, element)
            numberList.add(0, element)
        }
        simpleListAdapter.submitList(tmpList)
    }

    /**
     * 减去列表中最前面的 10 个元素
     */
    private fun minus() {
        if (numberList.size >= 10) {
            val tmpList = MutableList(numberList.size) {
                return@MutableList numberList[it]
            }
            simpleListAdapter.submitList(tmpList.subList(0, 10))
        }
    }

    private fun prepareList() {
        for (i in 1..10) {
            numberList.add(SimpleText("Tittle $i"))
        }
    }

    data class SimpleText(var title: String)

}
