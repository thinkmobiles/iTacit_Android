package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.data.entries.Category;
import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.domain.interactor.GetAuthorsListUserCase;
import com.itacit.healthcare.domain.interactor.GetCategoriesListUseCase;
import com.itacit.healthcare.domain.interactor.GetNewsListUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.mapper.NewsModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by root on 13.10.15.
 */
public class NewsFeedPresenter extends BasePresenter<INewsFeedView> implements INewsFeedPresenter {
    public static final int SEARCH_TEXT_MIN_LENGTH = 3;
    private GetNewsListUseCase getNewsListUseCase;
    private NewsModelMapper dataMapper;

    public NewsFeedPresenter(GetNewsListUseCase newsUseCase, NewsModelMapper newsModelDataMapper) {
        getNewsListUseCase = newsUseCase;
        dataMapper = newsModelDataMapper;
    }

    @Override
    public void attachView(INewsFeedView view) {
        super.attachView(view);
        if (getView()!= null) {
            mCompositeSubscription.add(getView().getNewsSearchTextObs()
                    .filter(t -> t.length() > SEARCH_TEXT_MIN_LENGTH)
                    .subscribe(this::searchNews));
        }
    }

    private void showNewsOnView(List<News> news) {
        List<NewsModel> newsModels = dataMapper.transform(news);
        if(getView() != null) getView().showNews(newsModels);
    }

    @Override
    public void loadNews() {
        if(getView() != null) getView().showProgress();
        getNewsListUseCase.execute(new NewsListSubscriber());
    }

    @Override
    public void searchNews(String searchWord) {

    }

    private final class NewsListSubscriber extends Subscriber<List<News>> {

        @Override
        public void onCompleted() {
            if(getView() != null) getView().hideProgress();
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
