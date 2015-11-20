package com.itacit.healthcare.presentation.messages.views.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.messages.ArchiveMessageUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetMessagesUseCase;
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
        implements MessagesFeedView, TabLayout.OnTabSelectedListener {
    @Bind(R.id.recycler_view_FMF)   RecyclerView messagesRecyclerView;
    @Bind(R.id.tab_layout_FMF)      TabLayout tabLayout;

    private MessagesAdapter messagesAdapter;
    private ProgressDialog progressDialog;

    @OnClick(R.id.fab_button_FMF)
    void addNewMessage() {
        activity.switchContent(NewMessageFragment.class, false);
    }

    @Override
    protected void setUpView() {
        tabLayout.addTab(tabLayout.newTab().setText("All \n1").setTag(MessagesFilter.ALL));
        tabLayout.addTab(tabLayout.newTab().setText("Act on \n2").setTag(MessagesFilter.ACT));
        tabLayout.addTab(tabLayout.newTab().setText("Waiting \n3").setTag(MessagesFilter.WAITING));
        tabLayout.addTab(tabLayout.newTab().setText("To my \n4").setTag(MessagesFilter.SENT));
        tabLayout.addTab(tabLayout.newTab().setText("For me \n5").setTag(MessagesFilter.INBOX));

        tabLayout.setOnTabSelectedListener(this);

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
        return new MessagesFeedPresenter(new GetMessagesUseCase(), new MessagesMapper(), new ArchiveMessageUseCase());
    }

    @Override
    public void showMessages(List<MessageModel> messages) {
        messagesAdapter = new MessagesAdapter(getActivity(), messages, presenter);
        messagesRecyclerView.setAdapter(messagesAdapter);
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    @Override
    public void showMessageDetails(String messageId) {
        Bundle args = new Bundle(1);
        args.putString(MessageRepliesFragment.Message_ID, messageId);
        activity.switchContent(MessageRepliesFragment.class, true, args);
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
        presenter.getMessages((MessagesFilter) tab.getTag());
    }
}
