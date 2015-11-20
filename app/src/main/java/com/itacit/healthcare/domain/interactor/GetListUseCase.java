package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by Nerevar on 10/29/2015.
 */
public abstract class GetListUseCase<T, P> extends UseCase<List<T>, P, ListRequest> {
    protected static final String DEFAULT_FIELDS = "DEFAULT";
    private static final String COMBINE_CHAR = "|";

    protected void setRequestFields(ListRequest requestBody, String... fields) {
        String requestFields = "";
        for (String field : fields) {
            if (!requestFields.isEmpty()) {
                requestFields += COMBINE_CHAR;
            }
            requestFields += field;
        }

        requestBody.setFields(requestFields);
    }

    protected abstract Observable<ListResponse<T>> request(ListRequest requestBody);

    @Override
    protected Observable<List<T>> buildUseCaseObservable(ListRequest requestBody) {
        return request(requestBody)
                .map(r -> {
                    if (r != null) {
                        return r.getResponseRows();
                    }
                    return null;
                });
    }
}
