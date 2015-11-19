package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.domain.models.NewsSearch;
import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.CategoryModel;

import java.text.SimpleDateFormat;
import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

import static com.itacit.healthcare.presentation.news.presenters.NewsSearchPresenter.DateType;

/**
 * Created by root on 21.10.15.
 */
public interface NewsSearchView extends View {
	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

	void showDate(DateType dateType, String toDate);
	void showAuthors(List<AuthorModel> authors);
	void showCategories(List<CategoryModel> categories);
	void showFilter(Filter filter);
	void hideFilter(Filter filter);
	void resetDate(DateType dateType);
	void showInvalidDateWarning();
	void showSelectDateWarning();
	void unselectAuthor(String id);
	void unselectCategory(String id);
	Observable<String> getSearchTextObs();
	Observable<Filter> getFilterRemovedObs();
	BehaviorSubject<NewsSearch> getNewsSearchSubj();
	List<Filter> getFilters();
}
