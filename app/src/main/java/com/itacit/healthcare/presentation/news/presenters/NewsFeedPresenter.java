package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.data.News;
import com.itacit.healthcare.domain.interactor.GetNewsInteractor;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.mapper.NewsModelDataMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by root on 13.10.15.
 */
public class NewsFeedPresenter extends BasePresenter<INewsFeedView> implements INewsFeedPresenter {
    public static final int SEARCH_TEXT_MIN_LENGTH = 3;
    private Subscription mSubscriptionSearchText;
    private GetNewsInteractor mNewsInteractor;
    private NewsModelDataMapper mDataMapper;

    public NewsFeedPresenter(GetNewsInteractor newsInteractor, NewsModelDataMapper newsModelDataMapper) {
        mNewsInteractor = newsInteractor;
        mDataMapper = newsModelDataMapper;
    }

    @Override
    public void attachView(INewsFeedView view) {
        super.attachView(view);
        if (mSubscriptionSearchText == null && getView()!= null) {
            mSubscriptionSearchText = getView().getNewsSearchTextObs()
                    .filter(t -> t.length() > SEARCH_TEXT_MIN_LENGTH)
                    .subscribe(this::searchNews);
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

    private void showNewsOnView(List<News> news) {
        List<NewsModel> newsModels = mDataMapper.transform(news);
        if(getView() != null) getView().showNews(newsModels);
    }

    @Override
    public void loadNews() {
        mNewsInteractor.execute(new NewsListSubscriber());
    }

    @Override
    public void searchNews(String searchWord) {

    }

    private final class NewsListSubscriber extends Subscriber<List<News>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<News> newses) {
            showNewsOnView(newses);
        }
    }
}
