package com.itacit.healthcare.presentation.news.views;

import com.itacit.healthcare.presentation.base.views.IView;

/**
 * Created by root on 21.10.15.
 */
public interface INewsFilterView extends IView {

	public void showDatePicker();
	public void showAuthors();
	public void showCategory();
}
