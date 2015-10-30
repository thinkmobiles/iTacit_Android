package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.entries.Category;
import com.itacit.healthcare.data.network.AuthManager;
import com.itacit.healthcare.data.network.ServiceGenerator;
import com.itacit.healthcare.data.network.api.NewsApi;
import com.itacit.healthcare.data.network.response.ListResponse;


import rx.Observable;

/**
 * Created by Nerevar on 10/29/2015.
 */
public class GetCategoriesListUseCase extends GetListUseCase<Category> {

    public GetCategoriesListUseCase(int startIndex, int rowCounts) {
        super(startIndex, rowCounts);
    }

    @Override
    protected Observable<ListResponse<Category>> request() {
        return ServiceGenerator.createService(NewsApi.class, AuthManager.accessToken)
                .getCategories(listRequest);
    }

}