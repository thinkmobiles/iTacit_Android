package com.itacit.healthcare.domain.interactor.news;

import com.itacit.healthcare.data.entries.Category;
import com.itacit.healthcare.data.network.services.NewsService;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.domain.interactor.GetListUseCase;


import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Nerevar on 10/29/2015.
 */
public class GetCategoriesUseCase extends GetListUseCase<Category> {
    public final static String QUERY_PREFIX = "categoryName:";

    public GetCategoriesUseCase(int startIndex, int rowCounts) {
        super(startIndex, rowCounts);
    }

    @Override
    public void execute(Subscriber<List<Category>> useCaseSubscriber, String query) {
        super.execute(useCaseSubscriber, QUERY_PREFIX + query);
    }

    @Override
    protected Observable<ListResponse<Category>> request() {
        return NewsService.getApi().getCategories(listRequest);
    }

}