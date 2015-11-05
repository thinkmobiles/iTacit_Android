package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.presentation.base.views.IView;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.CategoryModel;

import java.text.SimpleDateFormat;
import java.util.List;

import rx.Observable;

import static com.itacit.healthcare.presentation.news.presenters.INewsSearchPresenter.DateType;

/**
 * Created by root on 21.10.15.
 */
public interface INewsSearchView extends IView {
	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

	void showFromDate(String fromDate);
	void showToDate(String toDate);
	void showAuthors(List<AuthorModel> authors);
	void showCategories(List<CategoryModel> categories);
	void addFilter(Filter filter);
	void resetDate(DateType dateType);
	Observable<String> getSearchTextObs();
	Observable<Integer> getListClickObs();
}
