package com.itacit.healthcare.domain.interactor.news;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.data.network.request.ItemRequest;
import com.itacit.healthcare.data.network.services.NewsService;
import com.itacit.healthcare.domain.interactor.GetItemUseCase;

import rx.Observable;

/**
 * Created by root on 29.10.15.
 */
public class GetNewsDetailsUseCase extends GetItemUseCase<News, Void> {
    private static final String BODY_FIELD = "body";
    private static final String AUTHOR_FIELD = "author";
    private final String newsId;

    public GetNewsDetailsUseCase(String userId) {
        this.newsId = userId;
    }


    @Override
    protected Observable<News> buildUseCaseObservable(ItemRequest requestBody) {
        return NewsService.getApi()
                .getNewsDetails(requestBody)
                .filter(r -> r != null);
    }

    @Override
    protected ItemRequest initArgs(Void args) {
        ItemRequest requestBody = new ItemRequest();
        requestBody.setId(newsId);
        setRequestFields(requestBody, DEFAULT_FIELDS, BODY_FIELD, AUTHOR_FIELD);
        return requestBody;
    }
}
