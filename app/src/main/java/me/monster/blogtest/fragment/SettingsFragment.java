package me.monster.blogtest.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.jeremyliao.liveeventbus.LiveEventBus;

import me.monster.blogtest.ActionActivity;
import me.monster.blogtest.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author jiangjiwei
 */
public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    Button btnToRoot;
    Button btnToAction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        btnToRoot = rootView.findViewById(R.id.btn_to_root);
        TextView tvNickName = rootView.findViewById(R.id.tv_nick_name);
        if (getArguments() != null) {
            String nickName = SettingsFragmentArgs.fromBundle(getArguments()).getNickName();
            tvNickName.setText(nickName);
        }
        goBack();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnToAction = view.findViewById(R.id.btn_to_action);
        btnToAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionActivity.start(v.getContext());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LiveEventBus.get("close", Boolean.class)
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean flag) {
                        if (flag) {
                            Navigation.findNavController(btnToRoot)
                                    .navigateUp();
                        } else {
                            Log.e(TAG, "onMessageEvent: receive message but not do anything");
                        }
                    }
                });
    }

    private void goBack() {
        btnToRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(btnToRoot)
                        .navigateUp();
            }
        });
    }

}
