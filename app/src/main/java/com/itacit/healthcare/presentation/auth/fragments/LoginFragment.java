package com.itacit.healthcare.presentation.auth.fragments;

import android.support.v7.app.ActionBar;

import com.itacit.healthcare.presentation.auth.AuthActivity;
import com.itacit.healthcare.presentation.auth.presenters.LoginPresenter;
import com.itacit.healthcare.presentation.auth.views.LoginView;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;

/**
 * Created by root on 10.11.15.
 */
public class LoginFragment extends BaseFragmentView<LoginPresenter, AuthActivity> implements LoginView {
    @Override
    protected void setUpView() {
    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {

    }

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
