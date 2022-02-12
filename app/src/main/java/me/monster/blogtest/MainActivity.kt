package me.monster.blogtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import me.monster.blogtest.databinding.ActivityMainBinding
import me.monster.blogtest.fragment.RootFragment

/**
 * @description
 * @author: Created jiangjiwei in 2019-06-28 15:13
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d(TAG, "StatusBar 默认颜色 ${Integer.toHexString(window.statusBarColor)} NavigationBar 默认颜色 ${Integer.toHexString(window.navigationBarColor)}")
    }
}