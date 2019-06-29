package me.monster.blogtest.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import me.monster.blogtest.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author jiangjiwei
 */
public class SettingsFragment extends Fragment {

    Button btnToRoot;

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

    private void goBack() {
        btnToRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(btnToRoot)
                        .popBackStack();
            }
        });
    }

}
