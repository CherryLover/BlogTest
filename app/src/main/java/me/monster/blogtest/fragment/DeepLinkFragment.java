package me.monster.blogtest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import me.monster.blogtest.R;

/**
 * @description
 * @author: Created jiangjiwei in 2019-06-28 20:34
 */
public class DeepLinkFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_deep, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String myarg = arguments.getString("myarg");
//            Toast.makeText(getContext(), myarg, Toast.LENGTH_LONG)
//                    .show();
        }
        return rootView;
    }
}
