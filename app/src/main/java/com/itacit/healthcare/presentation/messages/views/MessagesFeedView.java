package com.itacit.healthcare.presentation.messages.views;

import com.itacit.healthcare.domain.interactor.messages.GetMessagesUseCase;
import com.itacit.healthcare.presentation.base.views.View;
import com.itacit.healthcare.presentation.messages.models.MessagesModel;
import com.itacit.healthcare.presentation.news.models.NewsModel;
import com.itacit.healthcare.presentation.news.models.NewsSearch;

import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Den on 12.11.15.
 */
public interface MessagesFeedView extends View {
    void showMessages(List<MessagesModel> messages);
}
