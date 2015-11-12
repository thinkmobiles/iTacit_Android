package com.itacit.healthcare.presenters;


import com.itacit.healthcare.domain.interactor.news.GetAuthorsUseCase;
import com.itacit.healthcare.domain.interactor.news.GetCategoriesUseCase;
import com.itacit.healthcare.presentation.news.mappers.AuthorMapper;
import com.itacit.healthcare.presentation.news.mappers.CategoryMapper;
import com.itacit.healthcare.presentation.news.mappers.NewsMapper;
import com.itacit.healthcare.presentation.news.presenters.NewsSearchPresenter;
import com.itacit.healthcare.presentation.news.views.INewsFeedView;
import com.itacit.healthcare.presentation.news.views.INewsSearchView;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.mock;

/**
 * Created by root on 04.11.15.
 */
public class NewsSearchPresenterTest {

	INewsSearchView newsSearchView;
	GetAuthorsUseCase getAuthorsUseCase;
	GetCategoriesUseCase getCategoriesUseCase;
	AuthorMapper authorMapper;
	CategoryMapper categoryMapper;
	private final long ID = 1;

	@Before
	public void setUp() {
		newsSearchView = mock(INewsSearchView.class);
		getAuthorsUseCase = mock(GetAuthorsUseCase.class);
		getCategoriesUseCase = mock(GetCategoriesUseCase.class);
		authorMapper = mock(AuthorMapper.class);
		categoryMapper = mock(CategoryMapper.class);
	}

	@Test
	public void shouldAddFilter() {
		NewsSearchPresenter presenter = new NewsSearchPresenter(getAuthorsUseCase, getCategoriesUseCase, authorMapper, categoryMapper);
		presenter.attachView(newsSearchView);
		presenter.selectAuthorFilterById(ID);
//		verify(newsSearchView).addFilter(eq());

	}
}
