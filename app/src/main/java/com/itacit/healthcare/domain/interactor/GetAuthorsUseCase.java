package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.data.network.AuthManager;
import com.itacit.healthcare.data.network.ServiceGenerator;
import com.itacit.healthcare.data.network.api.NewsApi;
import com.itacit.healthcare.data.network.response.ListResponse;

import rx.Observable;

/**
 * Created by Nerevar on 10/29/2015.
 */
public class GetAuthorsUseCase extends GetListUseCase<Author> {

    public GetAuthorsUseCase(int startIndex, int rowCounts) {
        super(startIndex, rowCounts);
    }

    @Override
    protected Observable<ListResponse<Author>> request() {
        return ServiceGenerator.createService(NewsApi.class, AuthManager.accessToken)
                .getAuthors(listRequest);
    }

}
