package com.itacit.healthcare.domain.interactor.news;

import com.itacit.healthcare.data.entries.News;
import com.itacit.healthcare.data.network.request.ListRequest;
import com.itacit.healthcare.data.network.response.ListResponse;
import com.itacit.healthcare.data.network.services.NewsService;
import com.itacit.healthcare.domain.interactor.GetListUseCase;
import com.itacit.healthcare.domain.models.NewsSearch;
import com.itacit.healthcare.presentation.base.widgets.chipsView.Chip;

import java.text.SimpleDateFormat;

import rx.Observable;

/**
 * Created by root on 21.10.15.
 */
public class GetNewsUseCase extends GetListUseCase<News, NewsSearch> {
    public static final String AUTHORS_PREFIX = "authorId:";
    public static final String CATEGORY_PREFIX = "categoryId:";
    public static final String SEARCH_PREFIX = "search:";
    public static final String START_DATE_PREFIX = "startDate:";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected Observable<ListResponse<News>> request(ListRequest requestBody) {
        return NewsService.getApi().getNews(requestBody);
    }

    @Override
    protected ListRequest initArgs(NewsSearch args) {
        ListRequest listRequest = new ListRequest();
        listRequest.setQuery(parseSearch(args));
        return listRequest;
    }

    private String parseSearch(NewsSearch search) {
        String query = "";
        if (!search.getSearch().isEmpty()) {
            query = SEARCH_PREFIX + search.getSearch();
        }

        if (search.getChips() != null) {
            String authorsIds = "";
            String categoriesIds = "";
            for (Chip chip : search.getChips()) {
                switch (chip.getFilterType()) {
                    case Author:
                        if (!authorsIds.isEmpty()) {
                            authorsIds += ",";
                        }
                        authorsIds += String.valueOf(chip.getId());
                        break;
                    case Category:
                        if (!categoriesIds.isEmpty()) {
                            categoriesIds += ",";
                        }
                        categoriesIds += String.valueOf(chip.getId());
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
        }

        String date = "";
        if (search.getDateFrom() != null) {
            String from = dateFormat.format(search.getDateFrom().getTime());
            date += START_DATE_PREFIX + from;
        }

        if (search.getDateTo() != null) {
            String to = dateFormat.format(search.getDateTo().getTime());
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

        return query;
    }
}