package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.data.network.AuthManager;
import com.itacit.healthcare.data.network.ServiceGenerator;
import com.itacit.healthcare.data.network.api.NewsApi;
import com.itacit.healthcare.data.network.request.ListRequest;

import java.util.List;

import rx.Observable;

/**
 * Created by Nerevar on 10/29/2015.
 */
public class GetAuthorsListUserCase extends UseCase<List<Author>> {
    private ListRequest listRequest;

    public GetAuthorsListUserCase(int startIndex, int rowCounts) {
        listRequest = new ListRequest();
        listRequest.setStartIndex(startIndex);
        listRequest.setRowCount(rowCounts);
        //listRequest.setQuery("Admin");
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return ServiceGenerator.createService(NewsApi.class, AuthManager.accessToken)
                .getAuthors(listRequest)
                .filter(r -> r != null);
    }
}
