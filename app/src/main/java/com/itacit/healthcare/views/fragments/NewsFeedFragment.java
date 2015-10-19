package com.itacit.healthcare.views.fragments;

import com.itacit.healthcare.models.News;
import com.itacit.healthcare.presenters.NewsFeedPresenter;
import com.itacit.healthcare.presenters.NewsFeedPresenterImpl;
import com.itacit.healthcare.views.INewsFeedView;

import java.util.List;


/**
 * Created by root on 13.10.15.
 */
public class NewsFeedFragment extends BaseFragmentView<NewsFeedPresenter> implements INewsFeedView{

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected NewsFeedPresenter createPresenter() {
        return new NewsFeedPresenterImpl();
    }


    @Override
    public void showNews(List<News> news) {

    }

    @Override
    public void newsItemSelected(News newsItem) {

    }
}
