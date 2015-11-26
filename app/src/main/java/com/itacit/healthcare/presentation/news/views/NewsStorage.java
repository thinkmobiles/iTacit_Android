package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.domain.models.NewsSearch;

/**
 * Created by root on 26.11.15.
 */
public interface NewsStorage {
	NewsSearch getNews();

	NewsSearch popNews();

	void pushNews(NewsSearch model);
}
