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
    protected ListRequest requestBody;

    protected static final String DEFAULT_FIELDS = "DEFAULT";
    private static final String COMBINE_CHAR = "|";

    public GetListUseCase(Integer startIndex, Integer rowCounts) {
        requestBody = new ListRequest();
        requestBody.setStartIndex(startIndex);
        requestBody.setRowCount(rowCounts);
    }

    public void execute(Subscriber<List<T>> useCaseSubscriber, String query) {
        requestBody.setQuery(query);
        super.execute(useCaseSubscriber);
    }

    protected void setRequestFields(String... fields) {
        String requestFields = "";
        for (String field : fields) {
            if (!requestFields.isEmpty()) {
                requestFields += COMBINE_CHAR;
            }
            requestFields += field;
        }

        requestBody.setFields(requestFields);
    }

    protected void setSortField(String sortField){
        if(sortField != null){
            requestBody.setSort(sortField);
        }
    }

    @Override
    public void execute(Subscriber<List<T>> useCaseSubscriber) {
        requestBody.setQuery(null);
        super.execute(useCaseSubscriber);
    }

    protected abstract Observable<ListResponse<T>> request();

    @Override
    protected Observable<List<T>> buildUseCaseObservable() {
        return request()
                .map(r -> {
                    if (r != null) {
                        return r.getResponseRows();
                    }
                    return null;
                });
    }
}
