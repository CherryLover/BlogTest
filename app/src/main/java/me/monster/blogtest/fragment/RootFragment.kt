package me.monster.blogtest.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.SeekBar
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import me.monster.blogtest.R
import me.monster.blogtest.databinding.FragmentRootBinding
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 *
 * @author jiangjiwei
 */
class RootFragment : Fragment() {

    companion object {
        private const val TAG = "RootFragment"
    }

    private lateinit var mBinding: FragmentRootBinding

    private var normalNavigatorBgColor = 0
    private var normalStatusBgColor = 0

    private val backupColors = listOf(Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY)

    private val visibilityList by lazy { resources.getStringArray(R.array.status_navigation_visibility) }
    private val visibilityMap = mapOf(
        "SYSTEM_UI_FLAG_VISIBLE" to View.SYSTEM_UI_FLAG_VISIBLE,
        "SYSTEM_UI_FLAG_LOW_PROFILE" to View.SYSTEM_UI_FLAG_LOW_PROFILE,
        "SYSTEM_UI_FLAG_HIDE_NAVIGATION" to View.SYSTEM_UI_FLAG_HIDE_NAVIGATION,
        "SYSTEM_UI_FLAG_FULLSCREEN" to View.SYSTEM_UI_FLAG_FULLSCREEN,
        "SYSTEM_UI_FLAG_LAYOUT_STABLE" to View.SYSTEM_UI_FLAG_LAYOUT_STABLE,
        "SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION" to View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION,
        "SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN" to View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN,
        "SYSTEM_UI_FLAG_IMMERSIVE" to View.SYSTEM_UI_FLAG_IMMERSIVE,
        "SYSTEM_UI_FLAG_IMMERSIVE_STICKY" to View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY,
        "SYSTEM_UI_FLAG_LIGHT_STATUS_BAR" to View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR,
        "SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR" to View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    )
    private val visibilityMeanMap = mapOf(
        "SYSTEM_UI_FLAG_VISIBLE" to "默认，StatusBar NavigationBar 持续可见",
        "SYSTEM_UI_FLAG_LOW_PROFILE" to "StatusBar 低可见性，部分图标不可见，展示的图标也接近透明",
        "SYSTEM_UI_FLAG_HIDE_NAVIGATION" to "隐藏 NavigationBar，点击屏幕后会再次出现。相当于临时设置，若配合 SYSTEM_UI_FLAG_LAYOUT_STABLE 这个使用，会降低由于 View 隐藏导致屏幕闪烁区域，保持稳定性。",
        "SYSTEM_UI_FLAG_FULLSCREEN" to "隐藏 StatusBar，点击屏幕后不再再次出现，配合 SYSTEM_UI_FLAG_LAYOUT_STABLE 使用无效果",
        "SYSTEM_UI_FLAG_LAYOUT_STABLE" to "保持页面结构稳定性，辅助使用。",
        "SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION" to "可用区域延伸至 NavigationBar 区域，配合 SYSTEM_UI_FLAG_LAYOUT_STABLE 使用有效果",
        "SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN" to "可用区域延伸至 StatusBar 区域,配合 SYSTEM_UI_FLAG_LAYOUT_STABLE 使用无效果",
        "SYSTEM_UI_FLAG_IMMERSIVE" to "SYSTEM_UI_FLAG_HIDE_NAVIGATION 和 SYSTEM_UI_FLAG_FULLSCREEN 的辅助状态，开启后，NavigationBar 隐藏且点击屏幕不会出现，只有从 StatusBar 或 NavigationBar 方向滑动时才会出现。",
        "SYSTEM_UI_FLAG_IMMERSIVE_STICKY" to "基础效果等同于 SYSTEM_UI_FLAG_IMMERSIVE，不同的时，SystemBar 出现时以半透明方式出现，并在固定时间后自动隐藏。",
        "SYSTEM_UI_FLAG_LIGHT_STATUS_BAR" to "黑色样式的 StatusBar，用 SYSTEM_UI_FLAG_VISIBLE 恢复",
        "SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR" to "黑色样式的 NavigationBar，用 SYSTEM_UI_FLAG_VISIBLE 恢复"
    )
    private val systemBehaviorList by lazy { resources.getStringArray(R.array.system_bar_behavior) }
    private val systemBarBehaviorMap = mapOf(
        "SHOW_BARS_BY_TOUCH" to WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH,
        "SHOW_BARS_BY_SWIPE" to WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE,
        "SHOW_TRANSIENT_BARS_BY_SWIPE" to WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentRootBinding.inflate(inflater, container, false)
        normalStatusBgColor = window.statusBarColor
        normalNavigatorBgColor = window.navigationBarColor

        mBinding.root.postDelayed({
            normalStatusBgColor = window.statusBarColor
            normalNavigatorBgColor = window.navigationBarColor
            Log.d(TAG, "delay StatusBar 默认颜色 ${Integer.toHexString(normalStatusBgColor)} NavigationBar 默认颜色 ${Integer.toHexString(normalNavigatorBgColor)}")
        }, 1000)
        toSettings()
        windowInsetsArea()
        return mBinding.root
    }

    private fun toSettings() {
        Log.d(TAG, "StatusBar 默认颜色 ${Integer.toHexString(normalStatusBgColor)} NavigationBar 默认颜色 ${Integer.toHexString(normalNavigatorBgColor)}")
        mBinding.btnToSetting.setOnClickListener {
            val nickName = "master"
            val action: RootFragmentDirections.ActionRootFragmentToSettingsFragment = RootFragmentDirections.actionRootFragmentToSettingsFragment().setNickName(nickName)
            Navigation.findNavController(mBinding.btnToSetting)
                .navigate(action)
        }
        mBinding.btnStatusBarColor.setOnClickListener {
            var newColor = backupColors.random()
            while (window.statusBarColor == newColor) {
                newColor = backupColors.random()
            }
            window.statusBarColor = newColor
        }
        mBinding.btnStatusBarColorReset.setOnClickListener {
            window.statusBarColor = getColor(R.color.colorPrimaryDark)
        }

        mBinding.btnStatusBarUseHeight.setOnClickListener {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
        mBinding.btnStatusBarUseHeightReset.setOnClickListener {
            window.decorView.systemUiVisibility = 0
            window.statusBarColor = getColor(R.color.colorPrimaryDark)
            window.navigationBarColor = normalNavigatorBgColor

            val fitsSystemWindows = mBinding.root.fitsSystemWindows
            Log.d(TAG, "根布局使用 fitSystemWindow $fitsSystemWindows")

            Log.d(TAG, "根布局 marginTop：${(mBinding.root.layoutParams as ViewGroup.MarginLayoutParams).topMargin}")
            Log.d(TAG, "根布局 paddingTop：${mBinding.root.paddingTop}")
            if (mBinding.root.paddingTop > 0) {
                mBinding.root.setPadding(mBinding.root.paddingLeft, 0, mBinding.root.paddingRight, mBinding.root.paddingBottom)
            }
        }

        mBinding.btnNavigatorColor.setOnClickListener {
            var newColor = backupColors.random()
            while (window.navigationBarColor == newColor) {
                newColor = backupColors.random()
            }
            window.navigationBarColor = newColor
        }
        mBinding.btnNavigatorReset.setOnClickListener {
            window.navigationBarColor = normalNavigatorBgColor
        }
        mBinding.sbNavigatorAlpha.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val cProgress = seekBar?.progress ?: 50
                val fl = cProgress / 255f
                mBinding.tvNavigatorAlpha.text = "导航栏透明度：$cProgress"
                window.navigationBarColor = adjustAlpha(normalNavigatorBgColor, 1 - fl)
            }
        })
        mBinding.sbStatusAlpha.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val cProgress = seekBar?.progress ?: 50
                val fl = cProgress / 255f
                mBinding.tvStatusAlpha.text = "状态栏透明度：$cProgress"
                window.statusBarColor = adjustAlpha(normalStatusBgColor, 1 - fl)
            }
        })

        var isDark = false
        mBinding.btnLightDarkIcon.setOnClickListener {
            val r = if (isDark) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.navigationBarColor = Color.WHITE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            } else {
                window.navigationBarColor = Color.BLACK
                View.SYSTEM_UI_FLAG_VISIBLE
            }
            window.decorView.systemUiVisibility = r
            isDark = isDark.not()
        }

        mBinding.btnApplyVisibility.setOnClickListener {
            val s = visibilityList[mBinding.spSystemBarVisibility.selectedItemPosition]
            visibilityMap[s]?.let {
                val result = when (it) {
                    View.SYSTEM_UI_FLAG_VISIBLE -> {
                        window.navigationBarColor = normalNavigatorBgColor
                        window.statusBarColor = normalStatusBgColor
                        if (mBinding.root.paddingTop > 0) {
                            mBinding.root.setPadding(mBinding.root.paddingLeft, 0, mBinding.root.paddingRight, mBinding.root.paddingBottom)
                        }
                        it or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    }
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION -> {
                        window.navigationBarColor = Color.TRANSPARENT
                        it or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    }
                    View.SYSTEM_UI_FLAG_FULLSCREEN -> {
                        window.statusBarColor = Color.TRANSPARENT
                        it or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    }
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION -> {
                        window.navigationBarColor = Color.TRANSPARENT
                        it or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    }
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN -> {
                        window.statusBarColor = Color.TRANSPARENT
                        it or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    }
                    View.SYSTEM_UI_FLAG_IMMERSIVE -> {
                        it or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
                    }
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY -> {
                        it or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
                    }
                    else -> {
                        it
                    }
                }
                window.decorView.systemUiVisibility = result
                mBinding.tvHint.text = visibilityMeanMap[s] ?: ""
            }
        }
    }

    /**
     * WindowInsets 相关 API 演示
     */
    private fun windowInsetsArea() {
        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView)
        Log.d(TAG, "windowInsetsController is null ${windowInsetsController == null}")
        mBinding.spBehavior.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val behavior = systemBehaviorList[position]
                systemBarBehaviorMap[behavior]?.let {
                    windowInsetsController?.systemBarsBehavior = it
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        mBinding.btnInsetsHideSystem.setOnClickListener {
            windowInsetsController?.let { controller ->
    //                controller.hide(WindowInsetsCompat.Type.statusBars())
    //                controller.hide(WindowInsetsCompat.Type.statusBars())
                // statusBars + statusBars = systemBars
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.show(WindowInsetsCompat.Type.ime())
                mBinding.etInput.requestFocus()
            }
        }
        mBinding.btnInsetsShowSystem.setOnClickListener {
            windowInsetsController?.let { controller ->
    //                controller.show(WindowInsetsCompat.Type.statusBars())
    //                controller.show(WindowInsetsCompat.Type.navigationBars())
                // statusBars + statusBars = systemBars
                controller.show(WindowInsetsCompat.Type.systemBars())

                // 判断 window insets 是否可见性，及控制 ime 的展示
                ViewCompat.getRootWindowInsets(window.decorView)?.let {
                    val statusVisible = it.isVisible(WindowInsetsCompat.Type.statusBars())
                    val navigationVisible = it.isVisible(WindowInsetsCompat.Type.navigationBars())
                    val imeVisible = it.isVisible(WindowInsetsCompat.Type.ime())
                    Log.d(TAG, "statusBar visible $statusVisible")
                    Log.d(TAG, "navigationBar visible $navigationVisible")
                    Log.d(TAG, "ime visible $imeVisible")

                    controller.hide(WindowInsetsCompat.Type.ime())
                    mBinding.etInput.clearFocus()
                }
            }
        }
        var isLight = false
        mBinding.btnLightDarkIconInsets.setOnClickListener {
            windowInsetsController?.let { controller ->
                val isLightStatusBar = controller.isAppearanceLightStatusBars
                val isLightNavigationBar = controller.isAppearanceLightStatusBars
                Log.d(TAG, "status bar is light $isLightStatusBar")
                Log.d(TAG, "navigation bar is light $isLightNavigationBar")
                controller.isAppearanceLightStatusBars = isLight
                controller.isAppearanceLightNavigationBars = isLight
                isLight = isLight.not()
            }
        }
        mBinding.btnEdgeToEdge.setOnCheckedChangeListener { buttonView, edgeToEdge ->
            if (edgeToEdge) {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                window.statusBarColor = Color.TRANSPARENT
                window.navigationBarColor = Color.TRANSPARENT
                mBinding.tvHint.gravity = Gravity.BOTTOM // 设置 gravity 的目的在于可以区分展示出没有适配 Navigation inset 的效果，需注释掉 ViewCompat.setOnApplyWindowInsetsListener(mBinding.tvHint)
                ViewCompat.setOnApplyWindowInsetsListener(mBinding.btnToSetting) { v, insets ->
                    val statusInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
                    Log.d(TAG, "statusInsets area ${statusInsets.top}")
                    v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        topMargin = statusInsets.top
                    }
                    insets
                }
                ViewCompat.setOnApplyWindowInsetsListener(mBinding.tvHint) { v, insets ->
                    val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
                    Log.d(TAG, "navigationInsets area ${navigationBarInsets.bottom}")
                    v.updatePadding(bottom = navigationBarInsets.bottom)
                    insets
                }
            } else {
                mBinding.btnToSetting.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    topMargin = 0
                }
                mBinding.tvHint.updatePadding(bottom = 0)
                mBinding.tvHint.gravity = Gravity.CENTER
                WindowCompat.setDecorFitsSystemWindows(window, true)
                window.statusBarColor = normalStatusBgColor
                window.navigationBarColor = normalNavigatorBgColor
                ViewCompat.setOnApplyWindowInsetsListener(mBinding.btnToSetting, null)
                ViewCompat.setOnApplyWindowInsetsListener(mBinding.tvHint, null)
            }
        }
    }

    private fun adjustAlpha(@ColorInt color: Int, factor: Float): Int {
        val alpha = (Color.alpha(color) * factor).roundToInt()
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }


    fun Fragment.getColor(id: Int): Int {
        return ContextCompat.getColor(this.requireContext(), id)
    }

    val Fragment.window: Window
        get() {
            return requireActivity().window
        }
}