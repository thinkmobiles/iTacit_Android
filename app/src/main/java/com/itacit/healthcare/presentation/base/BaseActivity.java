package com.itacit.healthcare.presentation.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.itacit.healthcare.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by root on 20.10.15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.action_bar_shadow)
    View actionBarShadow;

    public void setActionBarShadowVisibile(boolean visibile) {
        actionBarShadow.setVisibility(visibile ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    protected abstract @LayoutRes int getLayoutRes();

    public void setTitle(@NonNull String title) {
        toolbar.setTitle(title);
    }

    public void switchContent(Class<?> fragmentClass, boolean addToBackStack, Bundle args) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.content);
        if (currentFragment != null && currentFragment.getClass().equals(fragmentClass)) {
            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
        if (fragment != null) {
            transaction.remove(fragment);
        }

        Constructor<?> ctor = null;
        try {
            ctor = fragmentClass.getConstructor();
            fragment = (Fragment) ctor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (args != null) {
            fragment.setArguments(args);
        }

        transaction.replace(R.id.content, fragment, fragmentClass.getName());
        if (false) {
            transaction.addToBackStack(fragmentClass.getName());
        }

        transaction.commit();
    }

    public void switchContent(Class<?> fragmentClass, boolean addToBackStack) {
        switchContent(fragmentClass, addToBackStack, null);
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

}
