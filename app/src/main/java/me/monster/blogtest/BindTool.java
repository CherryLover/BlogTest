package me.monster.blogtest;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;

/**
 * @description
 * @author: Created jiangjiwei in 2019-07-23 10:23
 */
public class BindTool {

    @BindingAdapter({"imageRes"})
    public static void loadImage(ImageView imageView, int resId) {
        Glide.with(imageView)
                .load(resId)
                .into(imageView);
    }
}
