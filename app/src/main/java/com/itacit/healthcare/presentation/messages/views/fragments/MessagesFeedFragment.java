package com.itacit.healthcare.presentation.messages.views.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.messages.ArchiveMessageUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetMessagesUseCase;
import com.itacit.healthcare.global.utils.AndroidUtils;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter;
import com.itacit.healthcare.presentation.messages.views.MessagesFeedView;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.views.adapters.MessagesAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter.MessagesFilter;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesFeedFragment extends BaseFragmentView<MessagesFeedPresenter, MessagesActivity>
        implements MessagesFeedView, TabLayout.OnTabSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recycler_view_FMF)   RecyclerView messagesRecyclerView;
    @Bind(R.id.tab_layout_FMF)      TabLayout tabLayout;
    @Bind(R.id.swipe_container_FMF)      SwipeRefreshLayout swipeRefreshLayout;

    private MessagesAdapter messagesAdapter;
    private ProgressDialog progressDialog;
    private Boolean isArchive = false;
    private MessagesFilter currentFilter;

    @OnClick(R.id.fab_button_FMF)
    void addNewMessage() {
        activity.switchContent(NewMessageFragment.class);
    }

    @Override
    protected void setUpView() {
        tabLayout.addTab(tabLayout.newTab().setText("All \n1").setTag(MessagesFilter.ALL));
        tabLayout.addTab(tabLayout.newTab().setText("Act on \n2").setTag(MessagesFilter.ACT));
        tabLayout.addTab(tabLayout.newTab().setText("Waiting \n3").setTag(MessagesFilter.WAITING));
        tabLayout.addTab(tabLayout.newTab().setText("To my \n4").setTag(MessagesFilter.SENT));
        tabLayout.addTab(tabLayout.newTab().setText("For me \n5").setTag(MessagesFilter.INBOX));
        tabLayout.addTab(tabLayout.newTab().setText("Archive \n6").setTag(MessagesFilter.ARCHIVE));

        tabLayout.setOnTabSelectedListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        messagesRecyclerView.setLayoutManager(layoutManager);

        presenter.getMessages((MessagesFilter) tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getTag());
    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {
        switchToolbarIndicator(true, null);

        activity.setActionBarShadowVisible(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.title_messages_feed);
        actionBar.setSubtitle("");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_messages_feed;
    }

    @Override
    protected MessagesFeedPresenter createPresenter() {
	    currentFilter = MessagesFilter.ALL;
        return new MessagesFeedPresenter(new GetMessagesUseCase(), new MessagesMapper(), new ArchiveMessageUseCase());
    }

    @Override
    public void showMessages(List<MessageModel> messages) {
        messagesAdapter = new MessagesAdapter(getActivity(), messages, presenter, isArchive);
        AndroidUtils.checkRecyclerViewIsEmpty(messages, messagesRecyclerView, tvIsEmpty);
        messagesRecyclerView.setAdapter(messagesAdapter);
    }

    @Override
    public void showProgress() {
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(true);
//        }
//        progressDialog.show();
		swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.hide();
//        }
	    swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessageDetails(String messageId) {
        Bundle args = new Bundle(1);
        args.putString(MessageDetailsFragment.MESSAGE_ID, messageId);
        activity.switchContent(MessageDetailsFragment.class, args);
    }

    @Override
    public void removeMessage(String messageId) {
        int position = messagesAdapter.getMessagePosition(messageId);
        if (position >= 0) {
            messagesAdapter.getMessages().remove(position);
            messagesAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        checkTabSelected(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        checkTabSelected(tab);
    }

    private void checkTabSelected(TabLayout.Tab tab) {
        isArchive = MessagesFilter.ARCHIVE.equals(tab.getTag());
        currentFilter = (MessagesFilter) tab.getTag();
        presenter.getMessages(currentFilter);
    }

    @Override
    public void onRefresh() {
        presenter.getMessages(currentFilter);
    }
}
