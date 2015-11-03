package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.domain.interactor.GetAuthorsUseCase;
import com.itacit.healthcare.domain.interactor.GetCategoriesUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.views.INewsSearchView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.TimeInterval;

/**
 * Created by root on 26.10.15.
 */
public class NewsSearchPresenter extends BasePresenter<INewsSearchView> implements INewsSearchPresenter {
    private GetAuthorsUseCase getAuthorsUseCase;
    private GetCategoriesUseCase getCategoriesUseCase;

    public NewsSearchPresenter(GetAuthorsUseCase getAuthorsUseCase, GetCategoriesUseCase getCategoriesUseCase) {
        this.getAuthorsUseCase = getAuthorsUseCase;
        this.getCategoriesUseCase = getCategoriesUseCase;
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
        getAuthorsUseCase.execute(new GetAuthorsSubscriber());
    }

    @Override
    public void getCategories(String query) {
        getCategoriesUseCase.execute(new GetCategoriesSubscriber());
    }


    private final class GetAuthorsSubscriber extends Subscriber {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Object o) {

        }
    }
    private final class GetCategoriesSubscriber extends Subscriber {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Object o) {

        }
    }

}
