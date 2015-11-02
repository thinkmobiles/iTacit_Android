package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.presentation.base.views.IView;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.CategoryModel;

import java.util.List;

import rx.Observable;

/**
 * Created by root on 21.10.15.
 */
public interface INewsSearchView extends IView {
	void showDatePicker();
	void showAuthors(List<AuthorModel> authors);
	void showCategories(List<CategoryModel> categories);
	Observable<String> getSearchTextObs();
	Observable<Integer> getListClickObs();
}
