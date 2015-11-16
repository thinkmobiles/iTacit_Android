package com.itacit.healthcare.presentation.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.news.views.activity.NewsActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 20.10.15.
 */
public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.action_bar_shadow)
    View actionBarShadow;
    //    For navigation drawer
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    ActionBarDrawerToggle toggle;

    public void setActionBarShadowVisible(boolean visible) {
        actionBarShadow.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    protected abstract @LayoutRes int getLayoutRes();

    public void setTitle(@NonNull String title) {
        toolbar.setTitle(title);
    }

    public ActionBarDrawerToggle getToggle() {return toggle;}

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void switchContent(Class<?> fragmentClass, boolean addToBackStack, Bundle args) {
        hideKeyboard(this);
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

        if (count == 0 && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else

            if (count != 0 && drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
            } else

                if (count != 0 && !drawerLayout.isDrawerOpen(GravityCompat.START)){
                    getFragmentManager().popBackStack();
                } else

                    if(count == 0){
                        super.onBackPressed();
                    }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
            case R.id.nav_dashboard:
                break;
            case R.id.nav_news:
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                break;
            case R.id.nav_message:
                startActivity(new Intent(getApplicationContext(), MessagesActivity.class));
                break;
            case R.id.nav_training:
                break;
            case R.id.nav_log_out:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
