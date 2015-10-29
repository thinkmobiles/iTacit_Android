package com.itacit.healthcare;

import com.itacit.healthcare.domain.interactor.GetNewsListUseCase;
import com.itacit.healthcare.presentation.news.mapper.NewsModelMapper;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by root on 13.10.15.
 */
public class NewsFeedPresenterTest {
    private String LONG_TEXT = "longText";
    private String SHORT_TEXT = "AA";

    @Test
    public void shouldSearchNews() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(LONG_TEXT);
            }
        });

        INewsFeedView newsFeedView = mock(INewsFeedView.class);

        NewsFeedPresenter presenter = new NewsFeedPresenter(new GetNewsListUseCase(), new NewsModelMapper());

        when(newsFeedView.getNewsSearchTextObs()).thenReturn(observable);
        presenter.attachView(newsFeedView);
        //verify(presenter).searchNews(LONG_TEXT);
    }
}
