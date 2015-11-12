package com.itacit.healthcare.presenters;

import com.itacit.healthcare.domain.interactor.news.GetNewsUseCase;
import com.itacit.healthcare.presentation.news.mappers.NewsMapper;
import com.itacit.healthcare.presentation.news.presenters.NewsFeedPresenter;
import com.itacit.healthcare.presentation.news.views.NewsFeedView;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.Subscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by root on 13.10.15.
 */
public class NewsFeedPresenterTest {
    private String LONG_TEXT = "longText";
    private String SHORT_TEXT = "AA";

    NewsFeedView newsFeedView;
    GetNewsUseCase getNewsUseCase;
    NewsMapper newsMapper;

    @Before
    public void setUp() {
        newsFeedView = mock(NewsFeedView.class);
        getNewsUseCase = mock(GetNewsUseCase.class);
        newsMapper = mock(NewsMapper.class);
    }

    @Test
    public void shouldSearchNews() {
        Observable<String> observable = Observable.just(LONG_TEXT);

        NewsFeedPresenter presenter = new NewsFeedPresenter(getNewsUseCase, newsMapper);

        when(newsFeedView.getNewsSearchTextObs()).thenReturn(observable);
        presenter.attachView(newsFeedView);
        verify(getNewsUseCase).execute(isA(Subscriber.class), eq(LONG_TEXT));
        verify(newsFeedView).showProgress();
    }

    @Test
    public void shouldNotSearchNews() {
        Observable<String> observable = Observable.just(SHORT_TEXT);

        NewsFeedPresenter presenter = new NewsFeedPresenter(getNewsUseCase, newsMapper);

        when(newsFeedView.getNewsSearchTextObs()).thenReturn(observable);
        presenter.attachView(newsFeedView);
        verify(getNewsUseCase, never()).execute(isA(Subscriber.class), anyString());
        verify(newsFeedView, never()).showProgress();
    }





}
