package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.network.request.ItemRequest;

/**
 * Created by root on 11.11.15.
 */
public abstract class GetItemUseCase<T> extends UseCase<T> {
    protected static final String DEFAULT_FIELDS = "DEFAULT";
    private static final String COMBINE_CHAR = "|";

    protected ItemRequest requestBody = new ItemRequest();

    public GetItemUseCase(String id) {
        requestBody.setId(id);
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
}
