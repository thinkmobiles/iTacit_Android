package com.itacit.healthcare.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.news.views.NewsFeedFragment;



/**
 * Created by root on 20.10.15.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.fl_content_AM, new NewsFeedFragment()).commit();


    }
}
