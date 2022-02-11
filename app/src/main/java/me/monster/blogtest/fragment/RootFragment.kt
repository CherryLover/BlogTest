package me.monster.blogtest.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.SeekBar
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentRootBinding.inflate(inflater, container, false)
        toSettings()
        return mBinding.root
    }

    private val statusBarColor = listOf(Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY)

    private val normalNavigatorBgColor by lazy { window.navigationBarColor }
    private val normalStatusBgColor by lazy { window.statusBarColor }

    private fun toSettings() {
        Log.d(TAG, "StatusBar 默认颜色 ${Integer.toHexString(normalStatusBgColor)} NavigationBar 默认颜色 ${Integer.toHexString(normalNavigatorBgColor)}")
        mBinding.btnToSetting.setOnClickListener {
            val nickName = "master"
            val action: RootFragmentDirections.ActionRootFragmentToSettingsFragment = RootFragmentDirections.actionRootFragmentToSettingsFragment().setNickName(nickName)
            Navigation.findNavController(mBinding.btnToSetting)
                .navigate(action)
        }
        mBinding.btnStatusBarColor.setOnClickListener {
            var newColor = statusBarColor.random()
            while (window.statusBarColor == newColor) {
                newColor = statusBarColor.random()
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

        mBinding.btnNavigatorTranslate.setOnClickListener {
            Log.d(TAG, "导航栏默认颜色 ${Integer.toHexString(normalNavigatorBgColor)}")
            window.navigationBarColor = Color.TRANSPARENT
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