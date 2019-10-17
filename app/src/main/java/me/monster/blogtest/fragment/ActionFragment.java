package me.monster.blogtest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.jeremyliao.liveeventbus.LiveEventBus;

import me.monster.blogtest.R;

/**
 * @description
 * @author: Created jiangjiwei in 2019-10-17 10:44
 */
public class ActionFragment extends Fragment {


    Button btnClose;
    int count = 0;
    Button btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_action, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnClose = view.findViewById(R.id.btn_action_closeBefore);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
//                EventBus.getDefault().post(count == 2);
                LiveEventBus.get("close")
                        .post(count == 2);
            }
        });

        btnBack = view.findViewById(R.id.btn_action_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigateUp();
            }
        });

    }

}
