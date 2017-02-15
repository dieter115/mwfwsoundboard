package be.flashapps.mwfwsoundboard.Helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import be.flashapps.mwfwsoundboard.R;

/**
 * Created by thomasbeerten on 02/03/16.
 */
public class Helper_Fragments {

    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, boolean addToBackstack, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, fragment.getTag());
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, boolean addToBackstack, boolean animation, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.bottom_up, 0, 0, R.anim.bottom_down);
        fragmentTransaction.replace(R.id.container, fragment, fragment.getTag());
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addFragment(AppCompatActivity activity, Fragment fragment, boolean addToBackstack, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment, TAG);
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(TAG);
        }
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addFragment(AppCompatActivity activity, Fragment fragment, boolean addToBackstack, boolean animation, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, 0, 0, android.R.anim.slide_out_right);
        fragmentTransaction.add(R.id.container, fragment, fragment.getTag());
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeFragment(AppCompatActivity activity, String TAG) {

        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment != null) {
            activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    public static Fragment getFragment(AppCompatActivity activity, String TAG) {

        return activity.getSupportFragmentManager().findFragmentByTag(TAG);
    }
}