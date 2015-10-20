package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.presentation.news.views.INewsFeedView;

import rx.Subscription;

/**
 * Created by root on 13.10.15.
 */
public class NewsFeedPresenterImpl extends NewsFeedPresenter {

    public static final int SEARCH_TEXT_MIN_LENGTH = 3;
    private Subscription mSubscriptionSearchText;


    @Override
    public void attachView(INewsFeedView view) {
        super.attachView(view);
        if (mSubscriptionSearchText == null) {
            mSubscriptionSearchText = getView().getNewsSearchTextObs()
                    .filter(t -> t.length() > SEARCH_TEXT_MIN_LENGTH)
                    .subscribe(t -> searchNews(t));
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscriptionSearchText != null && mSubscriptionSearchText.isUnsubscribed()) {
            mSubscriptionSearchText.unsubscribe();
            mSubscriptionSearchText = null;
        }
    }

    @Override
    public void loadNews() {

    }

    @Override
    public void searchNews(String searchWord) {

    }
}
