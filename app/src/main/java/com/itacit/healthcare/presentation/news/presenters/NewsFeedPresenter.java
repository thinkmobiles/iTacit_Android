package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.domain.interactor.GetNewsUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.mapper.NewsModelMapper;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.models.NewsSearch;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;

/**
 * Created by root on 13.10.15.
 */
public class NewsFeedPresenter extends BasePresenter<INewsFeedView> implements INewsFeedPresenter {
    public static final int SEARCH_TEXT_MIN_LENGTH = 3;
    private GetNewsUseCase getNewsUseCase;
    private NewsModelMapper dataMapper;
    public List<NewsModel> newsModels;

    public NewsFeedPresenter(GetNewsUseCase newsUseCase, NewsModelMapper newsModelDataMapper) {
        getNewsUseCase = newsUseCase;
        dataMapper = newsModelDataMapper;
    }

    @Override
    protected void onViewAttach() {
        if (getView()!= null) {
            compositeSubscription.add(getView().getNewsSearchTextObs()
                    .filter(text -> text.length() >= SEARCH_TEXT_MIN_LENGTH)
                    .debounce(1, TimeUnit.SECONDS)
                    .subscribe(this::showSearchHints));

            compositeSubscription.add(getView().getNewsSearch().subscribe(this::searchNews));
        }
    }

    private void searchNews(NewsSearch search) {
        if (getView()!= null) getView().showFilters(search.getFilters());

        getNewsUseCase.execute(new NewsListSubscriber(), search);
    }

    private void showNewsOnView() {
        if(getView() != null) getView().showNews(newsModels);
    }

    @Override
    public void loadNews() {
        if(getView() != null) getView().showProgress();
        getNewsUseCase.execute(new NewsListSubscriber());
    }

    @Override
    public void searchNews(String query) {

    }

	private void showSearchHints(String searchWord) {

		List<NewsModel> resultNewsModels = new ArrayList<>();

		for (NewsModel model : newsModels) {
			if (model.getHeadline().contains(searchWord)) {
				resultNewsModels.add(model);
			}
		}
		if (!resultNewsModels.isEmpty()) {
			if (getView() != null) getView().showSearchResults(resultNewsModels);
		}
	}

    private final class NewsListSubscriber extends Subscriber<List<News>> {

        @Override
        public void onCompleted() {
            if(getView() != null) getView().hideProgress();
	        showNewsOnView();
        }

        @Override
        public void onError(Throwable e) {
            if(getView() != null) getView().hideProgress();
        }

        @Override
        public void onNext(List<News> newses) {
	        newsModels = dataMapper.transform(newses);
        }
    }
}
