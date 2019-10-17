package me.monster.blogtest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import me.monster.blogtest.nav.KeepStateNavigator;

/**
 * @description
 * @author: Created jiangjiwei in 2019-06-28 15:13
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        KeepStateNavigator navigator = new KeepStateNavigator(this, navHostFragment.getChildFragmentManager(), R.id.fragment);
        navController.getNavigatorProvider().addNavigator(navigator);
        navController.setGraph(R.navigation.navigation);
    }
}
