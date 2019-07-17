package me.monster.blogtest.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import me.monster.blogtest.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author jiangjiwei
 */
public class RootFragment extends Fragment {

    private View btnToDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_root, container, false);
        btnToDetail = rootView.findViewById(R.id.btn_to_detail);
        btnToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(btnToDetail)
                        .navigate(R.id.action_rootFragment_to_detailFragment);
            }
        });
        return rootView;
    }

}
