package me.monster.blogtest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_diff_list.*
import me.monster.blogtest.adapter.SimpleListAdapter
import kotlin.random.Random

const val type_text = 1
const val type_image = 2
const val span_count = 2
val allImage = mutableListOf(
    "https://cdn.pixabay.com/photo/2018/01/05/16/24/rose-3063284_150.jpg",
    "https://cdn.pixabay.com/photo/2018/01/28/11/24/sunflower-3113318_150.jpg",
    "https://cdn.pixabay.com/photo/2020/03/10/15/54/dandelion-4919334_150.jpg",
    "https://cdn.pixabay.com/photo/2018/04/05/14/09/sunflower-3292932_150.jpg",
    "https://cdn.pixabay.com/photo/2017/05/08/13/15/spring-bird-2295434_150.jpg",
    "https://cdn.pixabay.com/photo/2016/07/12/18/54/lily-1512813_150.jpg",
    "https://cdn.pixabay.com/photo/2015/04/10/00/41/yellow-715540_150.jpg",
    "https://cdn.pixabay.com/photo/2012/09/08/21/51/anemone-56414_150.jpg",
    "https://cdn.pixabay.com/photo/2017/01/11/17/27/drip-1972411_150.jpg",
    "https://cdn.pixabay.com/photo/2017/03/15/09/00/crocus-2145539_150.jpg",
    "https://cdn.pixabay.com/photo/2018/03/10/20/26/flowers-3215188_150.jpg",
    "https://cdn.pixabay.com/photo/2016/07/23/00/12/sun-flower-1536088_150.jpg",
    "https://cdn.pixabay.com/photo/2018/10/03/03/42/flower-gerbel-3720383_150.jpg",
    "https://cdn.pixabay.com/photo/2017/12/30/13/25/portrait-3050076_150.jpg",
    "https://cdn.pixabay.com/photo/2016/01/08/05/24/sunflower-1127174_150.jpg",
    "https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729515_150.jpg",
    "https://cdn.pixabay.com/photo/2016/08/28/23/24/sunflower-1627193_150.jpg",
    "https://cdn.pixabay.com/photo/2018/09/26/21/47/flowers-3705716_150.jpg",
    "https://cdn.pixabay.com/photo/2012/07/12/14/50/flower-52358_150.jpg",
    "https://cdn.pixabay.com/photo/2013/05/26/12/14/rose-113735_150.jpg"
)

class DiffListActivity : AppCompatActivity() {

    private var numberList = mutableListOf<BaseType>()
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
        btnAddImg.setOnClickListener { addImage(1) }

        rv_main_diff.also {
            it.adapter = simpleListAdapter
            it.layoutManager = GridLayoutManager(this, span_count).apply {
                this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (numberList[position].type == type_image) {
                            span_count / span_count
                        } else {
                            span_count
                        }
                    }
                }
            }
            it.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }
        prepareList()
        simpleListAdapter.submitList(numberList)
    }

    private fun addImage(count: Int) {
        val copyBase = copyBase()
        if (count == 1) {
            copyBase.add(SimpleImage(allImage[Random(System.currentTimeMillis()).nextInt(allImage.size -1)]))
        } else {
            for (i in 0 until count) {
                copyBase.add(SimpleImage(allImage[Random(System.currentTimeMillis() * i).nextInt(allImage.size -1)]))
            }
        }
        numberList = copyBase
        simpleListAdapter.submitList(copyBase)
    }

    /**
     * 编辑倒数第二个元素
     */
    private fun edit() {
        val tmpList = copyBase()
        val baseType = tmpList[tmpList.size - 2]
        if (baseType.type == type_text) {
            tmpList[tmpList.size - 2] = SimpleText("New Edit To")
            numberList = tmpList
            simpleListAdapter.submitList(tmpList)
        } else {
            Toast.makeText(this, "当前倒数第二个不是文本", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 在列表前加上 20 个元素
     */
    private fun add() {
        val tmpList = copyBase()
        for (i in 0 until 20) {
            val element = SimpleText(("Add Item $i"))
            tmpList.add(0, element)
            numberList.add(0, element)
        }
        numberList = tmpList
        simpleListAdapter.submitList(tmpList)
    }

    private fun copyBase(): MutableList<BaseType> {
        val tmpList = MutableList(numberList.size) {
            return@MutableList numberList[it]
        }
        return tmpList
    }

    /**
     * 减去列表中最前面的 10 个元素
     */
    private fun minus() {
        if (numberList.size >= 10) {
            val tmpList = MutableList(numberList.size) {
                return@MutableList numberList[it]
            }
            val subList = tmpList.subList(0, 10)
            numberList = subList
            simpleListAdapter.submitList(subList)
        }
    }

    private fun prepareList() {
        for (i in 1..10) {
            numberList.add(SimpleText("Tittle $i"))
        }
    }

    data class SimpleText(var title: String) : BaseType(type_text)

    data class SimpleImage(var link: String) : BaseType(type_image)

    open class BaseType(val type: Int)

}
