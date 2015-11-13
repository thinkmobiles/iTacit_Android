package com.itacit.healthcare.presentation.messages.views.fragments;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.messages.GetMessagesUseCase;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.models.MessagesModel;
import com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter;
import com.itacit.healthcare.presentation.messages.views.MessagesFeedView;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.views.adapters.MessagesAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesFeedFragment extends BaseFragmentView<MessagesFeedPresenter, MessagesActivity> implements MessagesFeedView {
    @Bind(R.id.recycler_view_FMF)
    RecyclerView messagesRecyclerView;

    private MessagesAdapter messagesAdapter;


    @Override
    protected void setUpView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        messagesRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {
        switchToolbarIndicator(true, null);

        activity.setActionBarShadowVisible(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.title_messages_feed);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_messages_feed;
    }

    @Override
    protected MessagesFeedPresenter createPresenter() {
        return new MessagesFeedPresenter(new GetMessagesUseCase(0,100), new MessagesMapper());
    }

    @Override
    public void showMessages(List<MessagesModel> messages) {
        messagesAdapter = new MessagesAdapter(getActivity(), messages);
        messagesRecyclerView.setAdapter(messagesAdapter);
//        messagesAdapter.setOnNewsItemSelectedListener(this::showNewsItemDetails);
    }
}
