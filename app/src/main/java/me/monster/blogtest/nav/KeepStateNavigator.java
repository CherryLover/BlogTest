package me.monster.blogtest.nav;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;

import java.util.ArrayDeque;

/**
 * @description
 * @author: Created jiangjiwei in 2019-07-23 15:05
 */
@Navigator.Name("keep_state_fragment")
public class KeepStateNavigator extends FragmentNavigator {
    private static final String TAG = "KeepStateNavigator";
    private Context context;
    private FragmentManager manager;
    private int containerId;
    private ArrayDeque<String> mBackStack = new ArrayDeque<>();

    public KeepStateNavigator(@NonNull Context context, @NonNull FragmentManager manager, int containerId) {
        super(context, manager, containerId);
        this.context = context;
        this.manager = manager;
        this.containerId = containerId;
    }

    @Nullable
    @Override
    public NavDestination navigate(@NonNull Destination destination, @Nullable Bundle args, @Nullable NavOptions navOptions, @Nullable Navigator.Extras navigatorExtras) {
        String tag = String.valueOf(destination.getId());
        FragmentTransaction transaction = manager.beginTransaction();
        boolean initialNavigate = false;
        Fragment currentFragment = manager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }/* else {
            initialNavigate = true;
        }*/
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment == null) {
            String className = destination.getClassName();
            fragment = manager.getFragmentFactory().instantiate(context.getClassLoader(), className);
            transaction.add(containerId, fragment, tag);
            initialNavigate = true;
            mBackStack.add(tag);
        } else {
//            transaction.attach(fragment);
            transaction.show(fragment);
        }

        transaction.setPrimaryNavigationFragment(fragment);
        transaction.setReorderingAllowed(true);
        transaction.commitNow();
        return initialNavigate ? destination : null;
    }

    @Override
    public boolean popBackStack() {
        if (mBackStack.isEmpty()) {
            return false;
        }
        if (manager.isStateSaved()) {
            Log.i(TAG, "Ignoring popBackStack() call: FragmentManager has already"
                    + " saved its state");
            return false;
        }
//        if (manager.getBackStackEntryCount() > 0) {
//            manager.popBackStack(
//                    generateBackStackName(mBackStack.size(), mBackStack.peekLast()),
//                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        } // else, we're on the first Fragment, so there's nothing to pop from FragmentManager
        String removeTag = mBackStack.removeLast();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment removeFrag = manager.findFragmentByTag(removeTag);
        if (removeFrag != null) {
            transaction.remove(removeFrag);
        } else {
            return false;
        }
        String showTag = mBackStack.getLast();
        Fragment showFrag = manager.findFragmentByTag(showTag);
        if (showFrag != null) {
            transaction.show(showFrag);
            transaction.setPrimaryNavigationFragment(showFrag);
            transaction.setReorderingAllowed(true);
            transaction.commitNow();
        } else {
            return false;
        }
        return true;
    }
}
