package com.itacit.healthcare.domain.interactor.news;

import com.itacit.healthcare.data.entries.Category;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.NewsService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import rx.Observable;

/**
 * Created by Nerevar on 10/29/2015.
 */
public class GetCategoriesUseCase extends GetListUseCase<Category, String> {
    public final static String QUERY_PREFIX = "categoryName:";

    @Override
    protected ListRequest initArgs(String query) {
        ListRequest requestBody = new ListRequest();
        requestBody.setQuery(QUERY_PREFIX + query);
        return null;
    }

    @Override
    protected Observable<ListResponse<Category>> request(ListRequest requestBody) {
        return NewsService.getApi().getCategories(requestBody);
    }
}