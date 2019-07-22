package me.monster.blogtest.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import me.monster.blogtest.R;
import me.monster.blogtest.databinding.FragmentDetailBinding;
import me.monster.blogtest.model.MomentViewModel;

/**
 * @description
 * @author: Created jiangjiwei in 2019-07-16 23:03
 */
public class DetailFragment extends Fragment {
    public static final int SHOW_CONTENT = 589;
    private static final String TAG = "DetailFragment";
    private MomentViewModel momentViewModel;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_CONTENT:
                    momentViewModel.setGoodValue(msg.what);
                    momentViewModel.setNameValue(getString(R.string.content));
                    break;
                default:
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentDetailBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        View root = viewDataBinding.getRoot();
        momentViewModel = ViewModelProviders.of(this)
                .get(MomentViewModel.class);
        viewDataBinding.setViewModel(momentViewModel);
        viewDataBinding.setLifecycleOwner(DetailFragment.this);

        mHandler.sendEmptyMessageDelayed(SHOW_CONTENT, 3 * 1000);

        return root;
    }
}
