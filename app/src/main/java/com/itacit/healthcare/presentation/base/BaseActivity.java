package com.itacit.healthcare.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.news.fragments.NewsFeedFragment;
import com.itacit.healthcare.presentation.news.fragments.NewsFilterFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by root on 20.10.15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
    }

    protected abstract @LayoutRes int getLayoutRes();

    public void setTitle(@NonNull String title) {
        mToolBar.setTitle(title);
    }

}
