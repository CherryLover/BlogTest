package me.monster.blogtest.tab;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.monster.blogtest.R;

/**
 * @description
 * @author: Created jiangjiwei in 2019-06-28 23:42
 */
public class TimerFragment extends Fragment {
    private static final String TAG = "TimerFragment";
    private TextView tvCounter;
    private CountDownTimer mDownTimer = new CountDownTimer(10 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            String text = String.valueOf((millisUntilFinished / 1000) + 1);
            tvCounter.setText(text);
        }

        @Override
        public void onFinish() {
            tvCounter.setText("finished");
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged: " + hidden);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        View rootView = inflater.inflate(R.layout.fragment_timer, container, false);

        tvCounter = rootView.findViewById(R.id.tv_timer_down);
        tvCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownTimer.start();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }
}
