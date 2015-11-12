package com.itacit.healthcare.presentation.news.presenters;

import com.itacit.healthcare.data.entries.Author;
import com.itacit.healthcare.data.entries.NewsDetails;
import com.itacit.healthcare.domain.interactor.news.GetAuthorsUseCase;
import com.itacit.healthcare.domain.interactor.news.GetNewsDetailsUseCase;
import com.itacit.healthcare.presentation.base.presenters.BasePresenter;
import com.itacit.healthcare.presentation.news.mappers.AuthorMapper;
import com.itacit.healthcare.presentation.news.mappers.NewsDetailsMapper;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.itacit.healthcare.presentation.news.models.NewsDetailsModel;
import com.itacit.healthcare.presentation.news.views.NewsDetailsView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by root on 26.10.15.
 */
public class NewsDetailsPresenter extends BasePresenter<NewsDetailsView> {
    private GetNewsDetailsUseCase newsDetailsUseCase;
    private GetAuthorsUseCase getAuthorsUseCase;
    private NewsDetailsMapper dataMapper;
    private AuthorMapper authorMapper;
    private NewsDetails newsDetails;

    public NewsDetailsPresenter(GetNewsDetailsUseCase newsDetailsUseCase, GetAuthorsUseCase getAuthorsUseCase, NewsDetailsMapper dataMapper, AuthorMapper authorMapper) {
        this.newsDetailsUseCase = newsDetailsUseCase;
        this.getAuthorsUseCase = getAuthorsUseCase;
        this.dataMapper = dataMapper;
        this.authorMapper = authorMapper;
    }

    private void showDetailsOnView(NewsDetails newsDetails) {
        NewsDetailsModel model = dataMapper.transform(newsDetails);
        if(getView() != null) getView().showNewsDetails(model);
    }

    private void setAuthorsRole(List<Author> authors) {
        for (Author author : authors) {
            if (author.getId().equals(newsDetails.getAuthorId())) {
                AuthorModel authorModel = authorMapper.transform(author);
                if(getView() != null) getView().showAuthorDetails(authorModel);
                break;
            }
        }
    }

    public void loadNewsDetails() {
        newsDetailsUseCase.execute(new NewDetailsSubscriber());
    }

    private final class NewDetailsSubscriber extends Subscriber<NewsDetails> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(NewsDetails newsDetails) {
            showDetailsOnView(newsDetails);
            NewsDetailsPresenter.this.newsDetails = newsDetails;
            getAuthorsUseCase.execute(new GetAuthorsSubscriber(), newsDetails.getAuthorName());
        }
    }

    private final class GetAuthorsSubscriber extends Subscriber<List<Author>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Author> authors) {
            setAuthorsRole(authors);
        }

    }
}
