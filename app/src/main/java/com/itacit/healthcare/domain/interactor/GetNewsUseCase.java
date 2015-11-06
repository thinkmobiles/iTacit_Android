package com.itacit.healthcare.domain.interactor;

import com.itacit.healthcare.data.network.AuthManager;
import com.itacit.healthcare.data.network.ServiceGenerator;
import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.data.network.api.NewsApi;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Filter;
import com.itacit.healthcare.presentation.news.models.NewsSearch;

import java.text.SimpleDateFormat;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by root on 21.10.15.
 */
public class GetNewsUseCase extends GetListUseCase<News> {

    public static final String AUTHORS_PREFIX = "authorId:";
    public static final String CATEGORY_PREFIX = "categoryId:";
    public static final String SEARCH_PREFIX = "search:";
    public static final String START_DATE_PREFIX = "startDate:";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public GetNewsUseCase(int startIndex, int rowCounts) {
        super(startIndex, rowCounts);
    }


    public void execute(Subscriber<List<News>> useCaseSubscriber, NewsSearch search) {
        String query = "";
        if (!search.getSearch().isEmpty()) {
            query = SEARCH_PREFIX + search.getSearch();
        }

        String authorsIds = "";
        String categoriesIds = "";
        for (Filter filter :search.getFilters()) {
            switch (filter.getFilterType()) {
                case Author:
                    if (!authorsIds.isEmpty()) {
                        authorsIds += ",";
                    }
                    authorsIds+=String.valueOf(filter.getId());
                    break;
                case Category:
                    if (!categoriesIds.isEmpty()) {
                        categoriesIds += ",";
                    }
                    categoriesIds+=String.valueOf(filter.getId());
                    break;
            }
        }

        if (!authorsIds.isEmpty()) {
            if (!query.isEmpty()) {
                query += "|";
            }

            query += AUTHORS_PREFIX + authorsIds;
        }

        if (!categoriesIds.isEmpty()) {
            if (!query.isEmpty()) {
                query += "|";
            }

            query += CATEGORY_PREFIX + categoriesIds;
        }

        String date = "";
        if (search.getDateFrom() != null && search.getDateFrom().getTime() > 0) {
            String from = dateFormat.format(search.getDateFrom());
            date += START_DATE_PREFIX + from;
        }

        if (search.getDateTo() != null && search.getDateTo().getTime() > 0) {
            String to = dateFormat.format(search.getDateTo());
            if (!date.isEmpty()) {
                date += ",";
            } else  {
                date += START_DATE_PREFIX;
            }

            date += to;
        }

        if(!date.isEmpty()) {
            if (!query.isEmpty()) {
                query += "|";
            }

            query += date;
        }

        super.execute(useCaseSubscriber, query);
    }

    @Override
    protected Observable<ListResponse<News>> request() {
        return ServiceGenerator.createService(NewsApi.class, AuthManager.accessToken)
                .getNews(listRequest);
    }

}