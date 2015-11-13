package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.data.entries.Category;
import com.itacit.healthcare.domain.interactor.news.GetAuthorsUseCase;
import com.itacit.healthcare.domain.interactor.news.GetCategoriesUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.news.mappers.AuthorMapper;
import com.itacit.healthcare.presentation.news.mappers.CategoryMapper;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.CategoryModel;
import com.itacit.healthcare.presentation.news.models.NewsSearch;
import com.itacit.healthcare.presentation.news.views.NewsSearchView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by root on 26.10.15.
 */
public class NewsSearchPresenter extends BasePresenter<NewsSearchView> {
    public enum DateType {From, To}
    public static final int TIMEOUT = 1;
    //interactors
    private GetAuthorsUseCase getAuthorsUseCase;
    private GetCategoriesUseCase getCategoriesUseCase;
    //mappers
    private AuthorMapper authorMapper;
    private CategoryMapper categoryMapper;
    //models
    private List<AuthorModel> authorModels = new ArrayList<>();
    private List<CategoryModel> categoryModels = new ArrayList<>();

    private Calendar fromDate;
    private Calendar toDate;

    public NewsSearchPresenter(GetAuthorsUseCase getAuthorsUseCase,
                               GetCategoriesUseCase getCategoriesUseCase,
                               AuthorMapper authorMapper, CategoryMapper categoryMapper) {
        this.getAuthorsUseCase = getAuthorsUseCase;
        this.getCategoriesUseCase = getCategoriesUseCase;
        this.authorMapper = authorMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    protected void onViewAttach() {
        compositeSubscription.add(getSearchObs().subscribe(this::getAuthors, e -> e.printStackTrace()));
        compositeSubscription.add(getSearchObs().subscribe(this::getCategories, e -> e.printStackTrace()));
        compositeSubscription.add(getView().getFilterRemovedObs().subscribe(this::removeFilter, e -> e.printStackTrace()));
    }

    private Observable<String> getSearchObs() {
        if (getView() != null) {
            return getView().getSearchTextObs()
                    .filter(t -> t.length() >= NewsFeedPresenter.SEARCH_TEXT_MIN_LENGTH)
                    .debounce(TIMEOUT, TimeUnit.SECONDS);
        }
        return Observable.empty();
    }

    public NewsSearch getNewsSearch() {
        List<Filter> filters = new ArrayList<>();
        if (getView() != null) filters = getView().getFilters();

        return new NewsSearch(filters, fromDate, toDate);
    }

    public void getAuthors(String query) {
        getAuthorsUseCase.execute(new GetAuthorsSubscriber(), query);
    }

    public void getCategories(String query) {
        getCategoriesUseCase.execute(new GetCategoriesSubscriber(), query);
    }

	public void removeFilter(Filter filter) {
		switch (filter.getFilterType()) {
			case Author: if (getView() != null) getView().unselectAuthor(filter.getId());
				break;
			case Category: if (getView() != null) getView().unselectCategory(filter.getId());
				break;
		}
	}

    public void selectAuthorFilterById(String id) {
        for (AuthorModel authorModel : authorModels) {
            if (authorModel.getId().equals(id)) {
                Filter filter = new Filter(id, authorModel.getFullName(), Filter.FilterType.Author);
                if (getView() != null) getView().addFilter(filter);
            }
        }
    }

    public void unselectAuthorFilterById(String id) {
        for (AuthorModel authorModel : authorModels) {
            if (authorModel.getId().equals(id)) {
                Filter filter = new Filter(id, authorModel.getFullName(), Filter.FilterType.Author);
                if (getView() != null) getView().removeFilter(filter);
            }
        }
    }

    public void selectCategoryFilterById(String id) {
        for (CategoryModel categoryModel : categoryModels) {
            if (categoryModel.getId().equals(id)) {
                Filter filter = new Filter(id, categoryModel.getName(), Filter.FilterType.Category);
                if (getView() != null) getView().addFilter(filter);
            }
        }
    }

    public void unselectCategoryFilterById(String id) {
        for (CategoryModel categoryModel : categoryModels) {
            if (categoryModel.getId().equals(id)) {
                Filter filter = new Filter(id, categoryModel.getName(), Filter.FilterType.Category);
                if (getView() != null) getView().removeFilter(filter);
            }
        }
    }

    public void onDateSelected(DateType dateType, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = null;
        switch (dateType) {
            case From:
                calendar = fromDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                break;
            case To:
                calendar = toDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                break;
        }

        if (getView() != null) {
            getView().showDate(dateType,
                    NewsSearchView.dateFormat.format(calendar.getTime()));
        }
    }

    public void onDateClear(DateType dateType) {
        if (getView() != null) getView().resetDate(dateType);
        switch (dateType) {
            case From:
                fromDate = null;
                break;
            case To:
                toDate = null;
                break;
        }
    }

    public boolean isDateValid() {
        if (fromDate == null && toDate == null) {
            return true;
        }

        if (fromDate == null) {
            if (getView() != null) getView().showSelectDateWarning();
            return false;
        }

        if (toDate == null) {
            return true;
        }

        if (fromDate.getTime().after(toDate.getTime())) {
            if (getView() != null) getView().showInvalidDateWarning();
            return false;
        }
        return true;
    }

    private void showAuthorsOnView() {
        if (getView() != null) getView().showAuthors(authorModels);
    }

    private void showCategoriesOnView() {
        if (getView() != null) getView().showCategories(categoryModels);
    }

    private final class GetAuthorsSubscriber extends Subscriber<List<Author>> {

        @Override
        public void onCompleted() {
            showAuthorsOnView();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Author> authors) {
            authorModels.clear();
            List<AuthorModel> authorModels = authorMapper.transform(authors);
            NewsSearchPresenter.this.authorModels.addAll(authorModels);
        }
    }

    private final class GetCategoriesSubscriber extends Subscriber<List<Category>> {

        @Override
        public void onCompleted() {
            showCategoriesOnView();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Category> categories) {
            categoryModels.clear();
            List<CategoryModel> categoryModels = categoryMapper.transform(categories);
            NewsSearchPresenter.this.categoryModels.addAll(categoryModels);
        }
    }
}
