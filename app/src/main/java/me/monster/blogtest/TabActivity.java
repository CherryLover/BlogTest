package me.monster.blogtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import me.monster.blogtest.nav.KeepStateNavigator;

/**
 * @author jiangjiwei
 */
public class TabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        NavController navController = Navigation.findNavController(this, R.id.fragment3);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        KeepStateNavigator navigator = new KeepStateNavigator(this, navHostFragment.getChildFragmentManager(), R.id.fragment3);
        navController.getNavigatorProvider().addNavigator(navigator);
        navController.setGraph(R.navigation.tab_navigation);

        setUpNavBottom(navHostFragment);
    }

    private void setUpNavBottom(NavHostFragment hostFragment) {
//        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        BottomNavigationView navMenu = findViewById(R.id.nv_bottom_menu);
        if (hostFragment != null) {
            NavController navController = hostFragment.getNavController();
            NavigationUI.setupWithNavController(navMenu, navController);
        }
    }
}
