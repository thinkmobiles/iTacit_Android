package com.itacit.healthcare.presenters;

import com.itacit.healthcare.domain.interactor.GetNewsUseCase;
import com.itacit.healthcare.presentation.news.mapper.NewsModelMapper;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by root on 13.10.15.
 */
public class NewsFeedPresenterTest {
    private String LONG_TEXT = "longText";
    private String SHORT_TEXT = "AA";
    INewsFeedView newsFeedView = mock(INewsFeedView.class);
    GetNewsUseCase getNewsUseCase = mock(GetNewsUseCase.class);
    NewsModelMapper newsModelMapper = mock(NewsModelMapper.class);


    @Test
    public void shouldSearchNews() {
        Observable<String> observable = Observable.just(LONG_TEXT);

        NewsFeedPresenter presenter = new NewsFeedPresenter(getNewsUseCase, newsModelMapper);

        when(newsFeedView.getNewsSearchTextObs()).thenReturn(observable);
        presenter.attachView(newsFeedView);
        verify(getNewsUseCase).execute(isA(Subscriber.class), eq(LONG_TEXT));
    }

    @Test
    public void shouldNotSearchNews() {
        Observable<String> observable = Observable.just(SHORT_TEXT);

        NewsFeedPresenter presenter = new NewsFeedPresenter(getNewsUseCase, newsModelMapper);

        when(newsFeedView.getNewsSearchTextObs()).thenReturn(observable);
        presenter.attachView(newsFeedView);
        verify(getNewsUseCase, never()).execute(isA(Subscriber.class), anyString());
    }

    @Test
    public void shouldMapNewsModels() {

        NewsFeedPresenter presenter = new NewsFeedPresenter(getNewsUseCase, newsModelMapper);
    }
}