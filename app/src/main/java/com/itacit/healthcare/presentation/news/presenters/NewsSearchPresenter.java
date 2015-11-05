package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.data.entries.Category;
import com.itacit.healthcare.domain.interactor.GetAuthorsUseCase;
import com.itacit.healthcare.domain.interactor.GetCategoriesUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.news.fragments.NewsSearchFragment;
import com.itacit.healthcare.presentation.news.mapper.AuthorModelMapper;
import com.itacit.healthcare.presentation.news.mapper.CategoryModelMapper;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.CategoryModel;
import com.itacit.healthcare.presentation.news.views.INewsSearchView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.TimeInterval;

/**
 * Created by root on 26.10.15.
 */
public class NewsSearchPresenter extends BasePresenter<INewsSearchView> implements INewsSearchPresenter {
    //interactors
    private GetAuthorsUseCase getAuthorsUseCase;
    private GetCategoriesUseCase getCategoriesUseCase;
    //mappers
    private AuthorModelMapper authorMapper;
    private CategoryModelMapper categoryMapper;
    //models
	private List<AuthorModel> authorModels;
	private List<CategoryModel> categoryModels;

    private Calendar fromDate = new GregorianCalendar();
    private Calendar toDate = new GregorianCalendar();


    public NewsSearchPresenter(GetAuthorsUseCase getAuthorsUseCase, GetCategoriesUseCase getCategoriesUseCase, AuthorModelMapper authorModelMapper, CategoryModelMapper categoryModelMapper) {
        this.getAuthorsUseCase = getAuthorsUseCase;
        this.getCategoriesUseCase = getCategoriesUseCase;
        authorMapper = authorModelMapper;
        categoryMapper = categoryModelMapper;
    }

    @Override
    protected void onViewAttach() {
        compositeSubscription.add(getSearchObs().subscribe(this::getAuthors));
        compositeSubscription.add(getSearchObs().subscribe(this::getCategories));
    }

    private Observable<String> getSearchObs() {
        if(getView() != null) {
            return getView().getSearchTextObs()
                    .filter(t -> t.length() > NewsFeedPresenter.SEARCH_TEXT_MIN_LENGTH)
                    .debounce(1, TimeUnit.SECONDS);
        }
        return Observable.empty();
    }

    @Override
    public void getAuthors(String query) {
        getAuthorsUseCase.execute(new GetAuthorsSubscriber(), query);
    }

    @Override
    public void getCategories(String query) {
        getCategoriesUseCase.execute(new GetCategoriesSubscriber(), query);
    }

    @Override
    public void selectAuthorFilterById(long id) {

	    Filter filter;
	    for (AuthorModel authorModel : authorModels) {
		    if (authorModel.getId() == id) {
			    filter = new Filter(id, authorModel.getFullName(), Filter.FilterType.Author);
			    if(getView() != null) getView().addItemToSearchList(filter);
		    }
	    }

    }

	@Override
	public void selectCategoryFilterById(long id) {

		Filter filter;
		for (CategoryModel categoryModel : categoryModels) {
			if (categoryModel.getId() == id) {
				filter = new Filter(id, categoryModel.getName(), Filter.FilterType.Category);
				if(getView() != null) getView().addItemToSearchList(filter);
			}
		}

	}

    @Override
    public void onDateSelected(DateType dateType, int year, int monthOfYear, int dayOfMonth) {
        switch (dateType) {
            case From:
                fromDate.set(year, monthOfYear, dayOfMonth);
                if(getView() != null) getView().showFromDate(INewsSearchView.dateFormat.format(fromDate.getTime()));
                break;
            case To:
                toDate.set(year, monthOfYear, dayOfMonth);
                if(getView() != null) getView().showToDate(INewsSearchView.dateFormat.format(toDate.getTime()));
                break;
        }
    }

    @Override
    public void onDateClear(DateType dateType) {
        switch (dateType) {
            case From:
                fromDate.clear();
                if(getView() != null) getView().showFromDate("ADD DATE");
                break;
            case To:
                fromDate.clear();
                if(getView() != null) getView().showToDate("ADD DATE");
                break;
        }
    }

    private void showAuthorsOnView(List<Author> authors) {
        List<AuthorModel> authorModels = authorMapper.transform(authors);
	    this.authorModels = authorModels;
        if(getView() != null) getView().showAuthors(authorModels);
    }

    private void showCategoriesOnView(List<Category> categories) {
        List<CategoryModel> categoryModels = categoryMapper.transform(categories);
	    this.categoryModels = categoryModels;
        if(getView() != null) getView().showCategories(categoryModels);
    }

    private final class GetAuthorsSubscriber extends Subscriber<List<Author>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Author> authors) {
            showAuthorsOnView(authors);
        }
    }
    private final class GetCategoriesSubscriber extends Subscriber<List<Category>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Category> categories) {
            showCategoriesOnView(categories);
        }

    }

}