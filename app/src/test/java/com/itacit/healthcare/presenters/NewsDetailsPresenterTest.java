package com.itacit.healthcare.presenters;

import com.itacit.healthcare.domain.interactor.news.GetAuthorsUseCase;
import com.itacit.healthcare.domain.interactor.news.GetNewsDetailsUseCase;
import com.itacit.healthcare.presentation.news.mappers.AuthorMapper;
import com.itacit.healthcare.presentation.news.mappers.NewsDetailsMapper;
import com.itacit.healthcare.presentation.news.presenters.NewsDetailsPresenter;

import org.junit.Before;
import org.junit.Test;

import rx.Subscriber;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by root on 04.11.15.
 */
public class NewsDetailsPresenterTest {
    private GetNewsDetailsUseCase getNewsDetails;
    private GetAuthorsUseCase getAuthors;
    private NewsDetailsMapper newsDetailsMapper;
    private AuthorMapper authorMapper;

    @Before
    public void setUp() {
        getNewsDetails = mock(GetNewsDetailsUseCase.class);
        getAuthors = mock(GetAuthorsUseCase.class);
        newsDetailsMapper = mock(NewsDetailsMapper.class);
        authorMapper = mock(AuthorMapper.class);
    }

    @Test
    public void shouldLoadDetails() {
        NewsDetailsPresenter presenter = new NewsDetailsPresenter(getNewsDetails, getAuthors, newsDetailsMapper, authorMapper);
        presenter.loadNewsDetails();
        verify(getNewsDetails).execute(isA(Subscriber.class));
    }
}
