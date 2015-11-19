package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.network.request.ItemRequest;

/**
 * Created by root on 11.11.15.
 */
public abstract class GetItemUseCase<T, P> extends UseCase<T, P, ItemRequest> {
    protected static final String DEFAULT_FIELDS = "DEFAULT";
    private static final String COMBINE_CHAR = "|";

    protected void setRequestFields(ItemRequest requestBody, String... fields) {
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
