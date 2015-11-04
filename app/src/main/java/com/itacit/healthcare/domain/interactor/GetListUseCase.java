package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Nerevar on 10/29/2015.
 */
public abstract class GetListUseCase<T> extends UseCase<List<T>> {
    protected ListRequest listRequest;

    public GetListUseCase(int startIndex, int rowCounts) {
        listRequest = new ListRequest();
        listRequest.setStartIndex(startIndex);
        listRequest.setRowCount(rowCounts);
    }

    public void execute(Subscriber<List<T>> useCaseSubscriber, String query) {
        listRequest.setQuery(query);
        super.execute(useCaseSubscriber);
    }

    protected abstract Observable<ListResponse<T>> request();

    @Override
    protected Observable<List<T>> buildUseCaseObservable() {
        return request()
                .filter(r -> r != null)
                .filter(r -> r.getResponseRows() != null)
                .map(ListResponse::getResponseRows);
    }
}
