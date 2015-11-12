package com.itacit.healthcare.domain.interactor.news;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.data.network.services.NewsService;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.domain.interactor.GetListUseCase;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Nerevar on 10/29/2015.
 */
public class GetAuthorsUseCase extends GetListUseCase<Author> {
    public final static String QUERY_PREFIX = "authorNameFull:";

    public GetAuthorsUseCase(int startIndex, int rowCounts) {
        super(startIndex, rowCounts);
    }

    @Override
    public void execute(Subscriber<List<Author>> useCaseSubscriber, String query) {
        super.execute(useCaseSubscriber, QUERY_PREFIX + query);
    }

    @Override
    protected Observable<ListResponse<Author>> request() {
        return NewsService.getApi().getAuthors(requestBody);
    }

}
