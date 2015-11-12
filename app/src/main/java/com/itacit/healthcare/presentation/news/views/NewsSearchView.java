package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.CategoryModel;

import java.text.SimpleDateFormat;
import java.util.List;

import rx.Observable;

import static com.itacit.healthcare.presentation.news.presenters.NewsSearchPresenter.*;

/**
 * Created by root on 21.10.15.
 */
public interface NewsSearchView extends View {
	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

	void showDate(DateType dateType, String toDate);
	void showAuthors(List<AuthorModel> authors);
	void showCategories(List<CategoryModel> categories);
	void addFilter(Filter filter);
	void removeFilter(Filter filter);
	void resetDate(DateType dateType);
	void showInvalidDateWarning();
	void showSelectDateWarning();
	void unselectAuthor(long id);
	void unselectCategory(long id);
	Observable<String> getSearchTextObs();
	Observable<Filter> getFilterRemovedObs();
	List<Filter> getFilters();
}