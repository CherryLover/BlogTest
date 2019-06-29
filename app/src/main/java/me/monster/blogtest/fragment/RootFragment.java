package me.monster.blogtest.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import me.monster.blogtest.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author jiangjiwei
 */
public class RootFragment extends Fragment {
    Button btnToSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_root, container, false);
        btnToSettings = rootView.findViewById(R.id.btn_to_setting);
        toSettings();
        return rootView;
    }

    private void toSettings() {
        btnToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Navigation.findNavController(btnToSettings)
//                        .navigate(R.id.action_rootFragment_to_settingsFragment);
//                上面是原来的代码，下面是新代码

                String nickName = "master";
                RootFragmentDirections.ActionRootFragmentToSettingsFragment action =
                        RootFragmentDirections.actionRootFragmentToSettingsFragment().setNickName(nickName);

                Navigation.findNavController(btnToSettings)
                        .navigate(action);
            }
        });
    }

}
