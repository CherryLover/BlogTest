package me.monster.blogtest.motion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import me.monster.blogtest.R

/**
 * @description
 * @author: Created jiangjiwei in 2019-07-11 11:31
 */
class MotionRootFragment : Fragment(), View.OnClickListener {
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        val id = v?.id
        if (id == R.id.btn_motion_login) {
            Navigation.findNavController(v)
                    .navigate(R.id.action_motionRootFragment_to_loginFragment)
        } else if (id == R.id.btn_motion_swipe) {
            Navigation.findNavController(v)
                    .navigate(R.id.action_motionRootFragment_to_swipeFragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_motion_root, container, false)
        rootView.findViewById<Button>(R.id.btn_motion_login)
                .setOnClickListener(this)
        rootView.findViewById<Button>(R.id.btn_motion_swipe)
                .setOnClickListener(this)
        return rootView
    }
}