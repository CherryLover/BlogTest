![题图](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/blog_navigation.jpg)

# 来学一波 Navigation

Navigation 是一个谷歌官方推出的一个用于 APP 内部便捷切换内容（Fragment 或 Activity）的库。从而使得 APP 内的页面跳转更简单。

我知道它的时候它的版本已经是 2.0.0 了，也是时候来学习一波了。

无论什么时候，学习的第一手资料不能缺了官方出品的 [CodeLab](https://codelabs.developers.google.com/codelabs/android-navigation/#0)。相信你，看了[CodeLab](https://codelabs.developers.google.com/codelabs/android-navigation/#0) 之后就能对 Navigation 有一个简单的了解。本人也是对 CodeLab 学习之后才写下了这篇博客，主要内容都能在 [CodeLab](https://codelabs.developers.google.com/codelabs/android-navigation/#0) 上找到。不过 [CodeLab](https://codelabs.developers.google.com/codelabs/android-navigation/#0) 里面是英文的讲解，而且其中的代码是使用 Kotlin 编写的，这篇博客是以 Java 代码的方式进行的。

还一件事情，Navigation 的原生支持是从 Android Studio 3.3 开始的，3.2 版本的需要在设置面板的 Experimental 模块中启用 Navigation 编辑器。

![image-20190628144412075](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190628144412075.png)

> 图片来自 CodeLab。

下面开始正题

## Navigation Graph 和 NavHostFragment

首先，添加依赖。

```groovy
implementation 'androidx.navigation:navigation-fragment:2.0.0'
implementation 'androidx.navigation:navigation-ui:2.0.0'
```

之后，在 res 文件夹下创建类型为 navigation 的资源文件夹，Android Studio 会自动在这个文件夹下生产一个名为 navigation.xml 的文件，这个文件的作用就是用于描述 Fragment 及相应的跳转逻辑、动画、参数等信息。这个文件也叫做 **Navigation Graph**。

![create_navigation_folder](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190628144907092.png)

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/navigation">

</navigation>
```

默认的 Navigation Graph 文件就只有一个根节点，如果我们有更多的 Fragment，添加进来，会有不同的子节点，子节点代表的就是 Fragment，fragment 节点中描述关于 Fragment 的相关信息，并且在 fragment 节点中还可以其他子节点，比如，action、argument、deepLink。他们分别用于表示 Fragment 的相关信息。往后会讲到的。现在我们现在创建一个 Fragment ，就叫 RootFragment 好了。

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
             xmlns:tools="http://schemas.android.com/tools"
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             tools:context=".fragment.RootFragment">
    <TextView
            android:layout_gravity="center"
            android:gravity="center"
            android:layout_width="match_parent"
            android:textSize="24sp"
            android:layout_height="match_parent"
            android:text="Root Fragment"/>

</FrameLayout>
```

只是在页面上显示出这个 Fragment 的名字，Java 代码中没有做任何事情。现在让我们回到 Navigation Graph 中，我们是初学者，不知道或者说不了解 Fragment 节点有哪些属性可以去使用，可以使用 Navigation Graph 的图形化界面，刚才我们看了 Navigation Graph 的代码，现在来看一下，图形化编辑页面。

![graph_design](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190628150519705.png)

> 左边区域：是已经添加进来的 Fragment 以及承载这些 Fragment 的页面；
>
> 中间区域：Fragment 的跳转示意图；
>
> 右边区域：是当前选中的 Fragment 的属性展示区；

页面中间已经提示我们了，点击那个图标，添加一个目标。试试看吧，从 Android Studio 展示出的列表中，找到我们刚才创建的 RootFragment。这时，页面已经发生了变化。我们刚才创建的 RootFragment 的样子已经出现了，而且名称前还有一个小图标，这表示 RootFragment 是 Navigation 管理页面的第一个页面也是开始页面。

![image-20190628150937246](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190628150937246.png)

页面右侧出现了一些属性，我们暂时可以不用管，现在我只想先运行起来，看看效果。不过在这之前，我们还需要改造一下，之前新建项目自动生成的 MainActivity。先打开 activity_main.xml 的图形化编辑页面，然后在 Palette 类型列表中找到 **NavHostFragment** 并拖拽到页面上，此时会弹出一个框，让你选择 Navigation Graph，我们选择刚才自动创建的文件即可。

> Android Studio 的布局文件的拖拽，不是太好用，需要手动切换到源代码形式，简单改动一下页面代码，我们让这个 NavHostFrgament 组件填充满整个容器即可。

![image-20190628151745388](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190628151745388.png)

最终的 activity_main.xml 的源文件如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <fragment
        android:id="@+id/fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:defaultNavHost="true"
        app:navGraph="@navigation/navigation" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

然后运行项目即可。这就是一个最简单的使用 Navigation 的例子，而且其中根本就没什么难度。

![image-20190628161622969](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190628161622969.png)

好，现在我们来回过头来看看，刚刚我们都做什么。我们真正有效的内容是从把 RootFragment 添加到 Navigation Graph 中，我们去看一下，Navigation Graph 的源代码。说不定能从那里发现点什么东西。

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/navigation"
    app:startDestination="@id/rootFragment">

    <fragment
        android:id="@+id/rootFragment"
        android:name="me.monster.blogtest.fragment.RootFragment"
        android:label="fragment_root"
        tools:layout="@layout/fragment_root" />
</navigation>
```

这个文件跟之前自动生成的没什么区别，无非就是多了一个 fragment 节点，以及根节点上多了一个 startDestination 属性。难道就是因为这个属性？是的，没错，在 Navigation 中我们使用 Destination(目标)来描述 Fragment 之间的跳转关系。这里的 startDestination 代表的就是这个是 Navigation 整个页面跳转管理栈的最根级页面。

再来看看那个添加到 MainActivity 页面的 NavHostFragment 组件。它其实就是一个布局文件中的 fragmen 组件，跟我们正常使用的没什么不同，非要说不同，那就是其中的 name、defaultNavHost 以及 navGraph 这三个属性了。

name 属性我们都知道，navGraph 属性里面的值是刚才创建 Navigation Graph，猜一下，就是把 Navigation Graph 引用到了这个 NavHostFragment 中。那最后一个 defaultNavHost 属性呢？那就是拦截系统返回按钮的点击事件的。

```xml
<fragment
    android:id="@+id/fragment"
    android:name="androidx.navigation.fragment.NavHostFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:defaultNavHost="true"
    app:navGraph="@navigation/navigation" />
```

## NavController

单单一个 Fragment 没啥意思，不好玩，这回我们再加一个页面(SettingsFragment)。尝试着从 RootFragment 页面点击按钮切换到 SettingsFragment 页面。然后在 SettingsFragment 页面点击按钮返回到 RootFragment 页面。

> 说是 SettingsFragment，里面就一个 Button 一个 TextView，布局代码就不贴了。

Fragment 准备好了，该往 Navigation Graph 里添加了，按照刚才添加 RootFragment 的方式再来一次，不过，这次比上次多一步。选中 RootFragment，点击 RootFragment 右边的小圆点然后牵引到右侧的 SettingsFragment。这样他们两个就建立一种关系。

![image-20190628160038102](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190628160038102.png)

来看一下源代码吧。我们发现，除了增加了一个 fragment 节点之外，原来的 RootFragment 的节点上还增加了一个子节点 action 。事实上，action 节点就是用来描述 Fragmen 之间的页面跳转的关系的，其中 destination 属性的值就是目标 fragment 的 id。

```xml
<fragment
    android:id="@+id/rootFragment"
    android:name="me.monster.blogtest.fragment.RootFragment"
    android:label="fragment_root"
    tools:layout="@layout/fragment_root" />

<!--上面是原来的代码，下面是新代码-->

<fragment
    android:id="@+id/rootFragment"
    android:name="me.monster.blogtest.fragment.RootFragment"
    android:label="fragment_root"
    tools:layout="@layout/fragment_root" >
    <action
        android:id="@+id/action_rootFragment_to_settingsFragment2"
        app:destination="@id/settingsFragment" />
</fragment>
<fragment
    android:id="@+id/settingsFragment"
    android:name="me.monster.blogtest.fragment.SettingsFragment"
    android:label="SettingsFragment" />
```

继续往下，我们为 RootFragment 页面绑定点击事件。

```java
private void toSettings() {
    btnToSettings.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(btnToSettings)
                    .navigate(R.id.action_rootFragment_to_settingsFragment);
        }
    });
}
```

这一看就知道了，通过 Navigation 找到一个叫 **NavController** 的东西，然后执行 navigate 方法，这个方法里面传的值就是刚才 RootFragment 子节点 action 的 id 的值。先运行一下看看效果。

> 1. 亲测点击按钮能跳转到 SettingsFragment 页面。下面的 Gif 动图只是表示能从 RootFragment 到 SettingsFragment，闪回到 RootFragment 页面只是 Gif 的重新播放。
> 2. 如果你在 SettingsFragment 点击系统的返回键，是能返回到 RootFragment。这就是 MainActivity 中 NavHostFragment 组件的属性 `app:defaultNavHost="true"` 起到的作用，有兴趣的话，可以改成 false 然后再试一下效果。

![](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/Blognav_toSettings.gif)

现在，让我们再次为 SettingsFragment 添加按钮的点击事件吧。

```java
private void goBack() {
    btnToRoot.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(btnToRoot)
                    .popBackStack();
        }
    });
}
```

和之前跳转到这个页面的方式差不多，只是最后执行的方法变成了 `popBackStack` 。

嗯，挺好的，不过，我们有些时候需要在两个 Fragment 之间做切换动画，这个怎么办？这个也不难，在Navigation Graph 中跳转的 action 内增加属性即可。呐，这样就行了，而且还可以用过 Java 代码来实现。

```xml
<fragment
    android:id="@+id/rootFragment"
    android:name="me.monster.blogtest.fragment.RootFragment"
    android:label="fragment_root"
    tools:layout="@layout/fragment_root">
    <action
        android:id="@+id/action_rootFragment_to_settingsFragment"
        app:destination="@id/settingsFragment"
        app:enterAnim="@anim/slide_in_right"
        app:exitAnim="@anim/slide_out_left"
        app:popEnterAnim="@anim/slide_in_left"
        app:popExitAnim="@anim/slide_out_right" />
</fragment>
```

Java 代码

```java
Navigation.findNavController(btnToSettings)
    .navigate(R.id.action_rootFragment_to_settingsFragment);
//                上面是原来的代码，下面是新代码

NavOptions options = new NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build();

Navigation.findNavController(btnToSettings)
    .navigate(R.id.action_rootFragment_to_settingsFragment, null, options);
```

这里，我们调用了 navigate 这个方法，其中第二个参数是 Bundle 类型，我们填入了 null

，那如果正常填了值，Bundle 是不是就是传递到 SettingsFragment 了呢？答案是肯定的。不过 Navigation 还有另一种方式来传值—— **Safe Args**。

### Safe Args

> 为啥要用 Safe Args 呢？
>
> 我也不知道为啥学，感觉如果单纯为了保证 key 安全的话，把 Bundle 里面的 key 抽取成常量值不也行吗？不太懂为啥要通过这种形式来做，不过呢，老话说得好，技多不压身。

Safe Args 是配合 Navigation 使用的一个 Gradle 插件。首先你得先去配置：

首先在你项目的根目录的 build.gradle 文件中加上这些东西：

```groovy
repositories {
    google()
}
dependencies {
    classpath "androidx.navigation:navigation-safe-args-gradle-plugin:2.0.0"
}
```

然后还得在你的 app 或是 module 的目录下的 build.gradle 文件夹加入：

```groovy
apply plugin: "androidx.navigation.safeargs"
```

如果你想用 safe Args 生成的代码时 Kotlin 的话，还需要加入：

```groovy
aapply plugin: "androidx.navigation.safeargs.kotlin"
```

最最最重要的一点是，你要确认你的 build.properties 文件中有这么一行：

```java
android.useAndroidX=true
```

当然了，如果你的项目本身就是用是 AndroidX 的依赖，就不用去确认了，肯定能通过的嘛。

现在我们就来从 RootFragmet 传递一个类型为 String 的备注名到 SettingsFragmen 吧。还是先通过图形化界面进行设置吧，选中 SettingsFragment，然后再右侧属性面板上找到 Argments 点击旁边的➕。

弹出一个框，我们填入一下信息，然后点击 add。

![image-20190628191327486](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190628191327486.png)

完成之后的 Navigation Graph 中 SettingsFragment 节点的内容变了。

```xml
<fragment
    android:id="@+id/settingsFragment"
    android:name="me.monster.blogtest.fragment.SettingsFragment"
    android:label="SettingsFragment"/>

<!--上面是原来的代码，下面是新代码-->

<fragment
    android:id="@+id/settingsFragment"
    android:name="me.monster.blogtest.fragment.SettingsFragment"
    android:label="SettingsFragment">
    <argument
        android:name="nickName"
        android:defaultValue="未设置"
        app:argType="string"
        app:nullable="true" />
</fragment>
```

> 这个时候，Gradle 会自动生成 SettingFragmentArgs 以及 RootFragmentDirections 这两个类，在 generatedJava 这个文件夹下的包内。如果没有自动生成的话，clean 一下或是 rebuild 项目都行。

现在就能直接通过 setNickName 的形式来设置待传递的值了。

```java
String nickName = "master";
RootFragmentDirections.ActionRootFragmentToSettingsFragment action =
        RootFragmentDirections.actionRootFragmentToSettingsFragment().setNickName(nickName);

Navigation.findNavController(btnToSettings)
        .navigate(action);
```

在 SettingsFragment 我们需要把值取出来，然后显示在屏幕上。

```java
String nickName = SettingsFragmentArgs.fromBundle(getArguments()).getNickName();
tvNickName.setText(nickName);
```

怎么样，是不是很简单，这比之前我们用 Bundle 传值要方便的多啦，而且再也不用担心 Key 写错的问题了。真香。

好了，Navigation 的基本学习就到这了，感觉真的挺不错的。可以考虑用用了，不过现在好像主页面都是四个或是五个 Tab 页面，这用 Navigation 怎么实现呀？Google 早就替我们想好了。

## BottomNavigationView

来，我们新建一个 Activity，然后打开布局文件的图形化工具页面，用之前我们添加 NavHostFragment 组件的方式来添加一个 BottomNavigationView，然后让这个组件位于整个页面的底部。页面的其余部分全部都留给 NavHostFragment。因为我们又引入了一个新的 Fragment 管理栈，所以，需要再次新建一个 Navigation Graph 文件 tab_navigation。

下面就是 activity_tab.xml 以及 tab_navigation.xml 的代码。

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".TabActivity">

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/nv_bottom_menu"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        app:itemHorizontalTranslationEnabled="false"
        app:layout_constraintBottom_toBottomOf="parent" />

    <fragment
        android:id="@+id/fragment3"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:defaultNavHost="true"
        app:layout_constraintBottom_toTopOf="@+id/nv_bottom_menu"
        app:layout_constraintTop_toTopOf="parent"
        app:navGraph="@navigation/tab_navigation" />
</androidx.constraintlayout.widget.ConstraintLayout>

<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/tab_navigation">

</navigation>
```

接下来干什么呢？刚才我们创建是容器，用于容纳 Fragment 的，现在来创建三个 Fragment，这三个 Fragment 是用于填充进容器的内容。

分别是 FeedFragment、TimerFragment、MineFragment。这三个 Fragment 我们还是分别显示自己的名称。布局文件里也就一个 TextView，Java 代码中什么也不做，仅仅是用来显示。

有了三个 Fragment，我们现在去 tab_navigation 把这三个 Fragment 都添加进去。

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/tab_navigation"
    app:startDestination="@id/feedFragment">

    <fragment
        android:id="@+id/feedFragment"
        android:name="me.monster.blogtest.tab.FeedFragment"
        android:label="fragment_feed"
        tools:layout="@layout/fragment_feed" />
    <fragment
        android:id="@+id/timerFragment"
        android:name="me.monster.blogtest.tab.TimerFragment"
        android:label="fragment_timer"
        tools:layout="@layout/fragment_timer" />
    <fragment
        android:id="@+id/mineFragment"
        android:name="me.monster.blogtest.tab.MineFragment"
        android:label="fragment_mine"
        tools:layout="@layout/fragment_mine" />
</navigation>
```

现在，我们容器有了，内容有了，只差一个媒介，把它们进行关联了。打开 activity_tab 的图形化界面，在左侧有一些属性，其中有一个属性是 menu。menu？就是那个经常用于页面右上角的 menu？它怎么会出现在这边？点击 menu 行最右边的按钮。

![image-20190628235937692](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190628235937692.png)

弹出一个对话框，好像和一开始创建 NavHostFragment 是一样的，不同的是，当时有待选择的 Navigation Graph 文件，现在我们没有 menu 文件，那就创建一个吧。

![image-20190629000519557](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190629000519557.png)

现在我们也有了 menu 文件。

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/feedFragment"
        android:icon="@drawable/ic_tab_feed"
        android:title="Feed" />
    <item
        android:id="@+id/timerFragment"
        android:icon="@drawable/ic_tab_timer"
        android:title="Timer" />
    <item
        android:id="@+id/mineFragment"
        android:icon="@drawable/ic_tab_mine"
        android:title="Mine" />
</menu>
```

现在再回去看 tab_activity.xml 发现 preview 已经变成了这样的。Cool

![image-20190629110347302](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190629110347302.png)

难道 Menu 就是那个把内容 (Fragment) 与容器 (NavHostFragment) 进行建立关系的媒介？是也不是，有那么一点关系，不过不太准确。还记得之前我们用与 RootFragment 和 SettingsFragment 进行切换页面的方式吗？一个是前进到下一个页面，一个是返回上一个页面，虽然最终的行为不同，但是它们都使用到了一个叫 **NavController** 的类，这个类实际上就是实现在 Fragment 之间进行跳转的类。

```java
Navigation.findNavController(btnToSettings).navigate(action);
Navigation.findNavController(btnToRoot).popBackStack();
```

那我们是不是可以通过 Navigation Controller 并结合底部导航菜单的点击事件来对 Fragment 进行控制，从而实现 Fragment 之间的切换？是这样的，没错，不过 Google 帮助我们完成了很多复杂的事情，我们只需要在 TabActivity 中添加下面这些代码即可。

```java
private void setUpNavBottom() {
    NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager()
        .findFragmentById(R.id.fragment3);
    BottomNavigationView navMenu = findViewById(R.id.nv_bottom_menu);
    if (hostFragment != null) {
        NavController navController = hostFragment.getNavController();
        NavigationUI.setupWithNavController(navMenu, navController);
    }
}
```

第一行，findFragmentById 里填写的 id 就是我们在 tab_activity.xml 中 name 属性是 NavHostFragment 节点的 id。

然后再通过 `NavigationUI.setupWithNavController()` 将二者进行想管理，这样只要我们点击底部导航菜单就是自动实现 Fragment 的之间的切换，完全不需要开发者自己去写那么控制逻辑。事实上，`NavigationUI.setupWithNavController()` 这个方法有很多重载方法，不仅仅只是用在 BottomNavigationView，还有 NavigationView 等，在这里就不一一介绍了感兴趣的可以去试试。现在来看看效果。

![Blognav_bottomNav](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/Blognav_bottomNav-1779718.gif)

## DeepLink

来来来，回顾一下刚才我们介绍的 Navigation Graph，它就是用于描述 Fragment 或者说用于描述内容信息的，刚才我们尝试了子节点 Fragment 的 action（页面跳转）与 arguments（Bundle 传值）节点，其实他还有一个子节点 deepLink。

不知道，你有没有遇到那种情况，朋友在微信上分享你一个连接，你一点开，页面上提示你使用微信的在浏览器打开，你在一点开发现，发现跳转到了一个应用的页面上去了。这种跳转方式在 Navigation 这个导航框架内叫做 deepLink。让我们来实现一下吧。

我们需要准备一个 Fragment，就叫 DeepLinkFragment 好了，这个页面我们跟之前的 Fragment 一样只显示 DeepLinkFragment 这个文字好了。layout 布局文件及 Java 代码就不贴了。现在再来看 Navigation Graph 中怎么写。

```xml
fragment
    android:id="@+id/deepLinkFragment"
    android:name="me.monster.blogtest.fragment.DeepLinkFragment"
    android:label="fragment_deep"
    tools:layout="@layout/fragment_deep" >
    <deepLink
        android:id="@+id/deepLink"
        app:uri="www.example.com/{myarg}" />
</fragment>
```

是的，你没有看错，在 Navigation Graph 中就多了这么点东西，然后记得一定要记得在 manifest 的承载 DeepLinkFragment 的 Activity 节点内引入你的 Navigation Graph。

> 1. 那里填的 url 后面大括号包裹着的是传入 DeepLinkFragment 的值，`myarg` 是 key，通过 Bundle 进行传递；
> 2. 我在写这篇博客的时候，有两个 Navigation Graph 文件，一个是用于 RootFragment 与 SettingsFragment 进行跳转的 navigation.xml，一个是用于底部导航菜单栏的 tab_navigation.xml，我把 DeepLinkFragment 放在了 navigation.xml 中，所以下面的值是 @navigation/navigation。

```xml
<nav-graph android:value="@navigation/navigation" />
```

来试一下，看看效果吧。

![Blognav_deepLink](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/Blognav_deepLink.gif)

好了，我们整个 Navigation 的学习到这里也告一段落了，结束之前让我们用一幅图来回顾一下 Navigation。

![image-20190629150736867](https://monster-image-backup.oss-cn-shanghai.aliyuncs.com/picgo/image-20190629150736867.png)

本文首发于[个人博客](https://jiangjiwei.site/)，文中全部源代码已上传至 [GitHub](https://github.com/CherryLover/BlogTest)，喜欢的麻烦点个🌟。

本文封面图：Photo by [Joseph Barrientos](https://unsplash.com/@jbcreate_?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText) on [Unsplash](https://unsplash.com/search/photos/navigation?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)