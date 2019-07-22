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

import me.monster.blogtest.R;
import me.monster.blogtest.databinding.FragmentDetailBinding;
import me.monster.blogtest.model.MomentDetail;
import me.monster.blogtest.model.MomentObs;

/**
 * @description
 * @author: Created jiangjiwei in 2019-07-16 23:03
 */
public class DetailFragment extends Fragment {
    public static final int SHOW_CONTENT = 589;
    private static final String TAG = "DetailFragment";
    private MomentDetail mMomentDetail;
    private MomentObs mMomentObs;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_CONTENT:
                    mMomentDetail.setContent("Test in Observable owner");
                    mMomentDetail.updateGood(1);
//                    mMomentObs.setContent("Test in Observable Data Class");
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
        mMomentDetail = new MomentDetail();

        viewDataBinding.setDetailObs(mMomentDetail);
        viewDataBinding.setLifecycleOwner(DetailFragment.this);

        mHandler.sendEmptyMessageDelayed(SHOW_CONTENT, 3 * 1000);

        return root;
    }
}
