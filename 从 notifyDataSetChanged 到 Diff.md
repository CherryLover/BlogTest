# 从 notifyDataSetChanged 到 DiffUtil

DiffUtil 是 RecyclerView 中自带的一个计算数据集差异性的组件，其内部还提供了对 RecyclerView 进行局部刷新的 API。以往我们对 RecyclerView 的刷新，大多会采用 notifyDataSetChanged，而这种刷新方式，会造成 RecyclerView#Adapter 调用 List#size 次的 onCreateViewHolder 和 onBindViewHolder，很容易就造成资源浪费，在使用了 DiffUtil 之后，无论是添加、删除、还是更新元素，也只会调用对应数量个 onCreateViewHolder 和 onBindViewHolder，从而对 RecyclerView 的使用进行优化。

下面我们就通过一个简单的例子来认识一下这个 DiffUtil。

## 创建 RecyclerView

首先，我们创建出一个简单的列表。然后为其设置 Adapter、LayoutManager 等 RecyclerView 基础配置。

代码如下：

```kotlin
private val numberList = mutableListOf<SimpleText>()
private val simpleAdapter = SimpleAdapter(numberList)

rv_main_diff.adapter = simpleAdapter
rv_main_diff.layoutManager = LinearLayoutManager(this)
rv_main_diff.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

for (i in 1..10) {
  numberList.add(SimpleText("Tittle $i"))
}

prepareList()
simpleAdapter.notifyDataSetChanged()

data class SimpleText(var title: String)
```

SimpleAdapter 和 布局文件内容比较简单，就不展示了，仅仅是把 SimpleText 的中的 title 显示到 RecyclerView 的 Item 上。

![](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/BlogTest-DiffUtil-1.png)

## 更新列表

### 实现 DiffUtil.Callback

在没有使用 DiffUtil 时，更新页面上的内容我们需要更新数据集合「List」，然后再次调用 Adapter#notifyDataSetChanged 或 Adapter 的其它局部更新的方法进行刷新即可。现在我们既然要使用 DiffUtill，那么具体该怎么做呢？

首先，我们需要创建一个 DiffUtil.Callback 的实现类，DiffUtil.Callback 是一个抽象类，其中包含了 4 个抽象方法，都是用于比较两个数据集时所必须用到的方法。

```kotlin
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
```

DiffUtil.Callback 需要两个列表，就把新旧两个数据集合在构造函数中传入即可。

- getOldListSize：顾名思义就是获取上一个版本的列表个数；
- getNewListSize：和 getOldListSize 一样，只不过是获取最新的列表个数；
- areItemsTheSame：这个方法是用于比较指定新旧两个元素位置所对应的元素是否为同一个元素；
- areContentsTheSame：这个方法有些类似上一个，区别在于确定两个元素是同一个元素后，判断新旧列表中的这两个元素内容是否相同。

到这里，我们猜一下，这个 DiffUtil 的工作原理：先进行判断新旧两个数据集的尺寸是否相同，然后再比较新旧两个数据集同一个下标的所对应的元素是否相同，最后再对元素内容是否一致进行比较。

不管猜的对不对，我们先继续。

在创建完 DiffUtil.Callback 的实现类后，需要对它进行实例化，注意看，DiffCallback 的主构造器声明了两个字段，所以我们在对它进行实例化时也需要传入两个元素。

### 编辑列表元素

现在我们有个需求：对数据集合中倒数第二个元素进行编辑。我们该怎么做？按照之前不使用 Diff 的时候，我们应该这么做：

```kotlin
numberList[numberList.size - 2].title = "New Edit To"
simpleAdapter.notifyItemChanged(numberList.size - 2)
```

现在呢？自然是用 DiffUtil.Callback 来进行，不过在使用 Diff 之前，我们还需要准备一个 List。因为我们刚才给 DiffCallback 的主构造器声明了两个字段，一个是 oldList，一个是 newList。oldList 就是我们的 numberList，我们还需要准备一个新的 List。

```kotlin
val tmpList = MutableList<SimpleText>(10) {
  return@MutableList numberList[it]
}
tmpList[tmpList.size - 2] = SimpleText("New Edit To")
```

数据准备完成后，接下来就是应用在 Diff 上了。

```kotlin
val diffResult = DiffUtil.calculateDiff(DiffCallback(numberList, tmpList))
numberList.clear()
numberList.addAll(tmpList)
diffResult.dispatchUpdatesTo(simpleAdapter)
```

可以看到，就这样很简单的把两个 List 放在 DiffUtil#calculateDiff 中进行计算，然后得出 diffResult，最后将 diffResult 关联到 Adapter 即可。

怎么样？是不是挺简单的？去试一下，向列表添加元素和删除元素吧。

> 添加和删除元素本质上和编辑元素没太多区别，就不进行阐述了，在提供的代码中都有。

![](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/BlogTest-DiffUtil-Action.gif)

## ListAdapter

Diff 用起来好像挺简单的，不过 Google 还向我们提供了一个工具，能让我们用起来更方便，这个就是 ListAdapter。

ListAdapter 继承了 RecyclerView#Adapter，并且开发者不需要再实现 DiffCallback 的四个方法，只需要实现其中的两个「areItemsTheSame，areContentsTheSame」即可。也不需要调用拿到 DiffUtil#calculateDiff 的结果了。只需要在需要更新列表的时候，调用 `simpleListAdapter.submitList(List)` 即可，不过 RecyclerView 的 Adapter 就不能继承 RecyclerView#Adaper 了，而是需要继承自 ListAdapter。

```kotlin
class SimpleDiffItemCallback : DiffUtil.ItemCallback<MainActivity.SimpleText>() {
    override fun areItemsTheSame(oldItem: MainActivity.SimpleText, newItem: MainActivity.SimpleText): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MainActivity.SimpleText, newItem: MainActivity.SimpleText): Boolean {
        return oldItem.title.hashCode() == newItem.title.hashCode()
    }
}

class SimpleListAdapter : ListAdapter<MainActivity.SimpleText, SimpleListViewHolder>(SimpleDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleListViewHolder {
        return SimpleListViewHolder(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent,false))
    }

    override fun onBindViewHolder(holder: SimpleListViewHolder, position: Int) {
        holder.tvText.text = getItem(position).title
    }
}

class SimpleListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var tvText: TextView = itemView.findViewById(android.R.id.text1)
}
```

仔细看的朋友可能会发现，我在使用 ListAdapter 时传入一个 SimpleDiffItemCallback 这个对象，这个对象实现的抽象类是 DiffUtil.ItemCallback。这个 ItemCallback 和之前使用 Callback 有 3 处不同。

1. Callback 是 DiffUtil 用于比较两个列表，而 ItemCallback 是 DiffUtil 用于比较两个元素不为空的列表；
2. Callback 有 4 个抽象方法，ItemCallback 只有 2 个；
3. Callback 适用于自己创建并应用在 RecyclerView#Adapter 上，ItemCallback 是 ListAdapter 内置在其中的。

怎么样？ListAdapter 是不是挺简单的？不过我现在有点好奇，ListAdpater 内部具体做了哪些事情？来让我们看看 ListAdapter 的源码。

## ListAdapter 到底干了啥

当我点开 ListAdapter 的源码时，我发现，它内部好像啥也没干，又好像啥都干了。具体来说就是把全部的脏活累活都丢给了 AsyncListDiffer 这个类，ListAdapter 自己就像是一个代理，需要做什么，就丢给 AsyncListDiffer 这个类，需要获取什么就从 AsyncListDiffer 这个类里取出来。

![](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/20200327163615.png)

看到这，我继续进入 AsyncListDiffer，发现这个类其实也没特别多的东西，最重要的就是其中两个方法。一个是 submitList，另一个是 latchList。

submitList 其实就是我们在 Activity/Fragment 调用 adapter#submit(List) 后通过各种调用最终的目的地。不过在 submitList 的内部实际上是通过在子线程计算两个列表的差异，并在主线程上通过调用 latchList 将数据关联到 RecyclerView#Adapter 的效果。

latchList 做的内容就很简单了，只是将数据进行绑定。

![](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/carbon-asynclistdiffer.png)

## 多类型怎么做？

在这个例子中，我使用的是单一类型的数据集，但是在实际项目中存在多类型数据集该怎么做呢？我这里提供一下思路和简单的示例代码。

思路：

1. 多类型数据集合，我们可以创建一个 Base 类，在 Base 类中定义类型，不同的类型在继承 Base 类时，在其构造方法内调用 Base 的构造方法，并传入类型。
2. 如果不使用 ListAdapter 的话，RecyclerView#Adapter 就可以按照以往的多类型编写方式进行，只需要更改 DiffUtil.Callback 的实现类中的 areItemsTheSame 和 areContentsTheSame 的具体实现。具体实现可以先判断元素类型是否相同，然后再对内容进行判断；
3. 如果使用 ListAdapter 的话，需要在继承的 ListAdapter 传入泛型进行更改，而且还需要对实现 DiffUtil.ItemCallback 的实现类的传入泛型的更改；

怎么样？Diff 用起来很简单吧？要不要去试一下？而且，偷偷告诉你，Google 发布的另一个库 Paging 内部也有 Diff 的身影哦。

本文示例代码已经上传到了 GitHub，分支为 diff，多类型示例。


