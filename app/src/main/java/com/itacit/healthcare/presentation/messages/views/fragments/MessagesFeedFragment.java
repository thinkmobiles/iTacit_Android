package com.itacit.healthcare.presentation.messages.views.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.messages.ArchiveMessageUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetMessagesSummaryUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetMessagesUseCase;
import com.itacit.healthcare.global.utils.AndroidUtils;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.mappers.MessagesSummaryMapper;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter;
import com.itacit.healthcare.presentation.messages.views.MessagesFeedView;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.views.adapters.MessagesAdapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter.MessagesFilter;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesFeedFragment extends BaseFragmentView<MessagesFeedPresenter, MessagesActivity>
        implements MessagesFeedView, TabLayout.OnTabSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler_view_FMF)        RecyclerView       messagesRecyclerView;
    @Bind(R.id.tab_layout_FMF)           TabLayout          tabLayout;
    @Bind(R.id.swipe_container_FMF)      SwipeRefreshLayout swipeRefreshLayout;

    private MessagesAdapter messagesAdapter;
    private boolean handleScrolls = true;

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (layoutManager.findFirstVisibleItemPosition() ==
                    layoutManager.getItemCount() - layoutManager.getChildCount()) {
                messagesRecyclerView.removeOnScrollListener(this);
                if (handleScrolls)  presenter.getMore();
                handleScrolls = false;
            }
        }
    };

    @OnClick(R.id.fab_button_FMF)
    void addNewMessage() {
        activity.switchContent(NewMessageFragment.class);
    }

    private Map<MessagesFilter, String> tabsNamesMap;
    {
        tabsNamesMap = new HashMap<>();
        tabsNamesMap.put(MessagesFilter.ALL, "All \n");
        tabsNamesMap.put(MessagesFilter.ACT, "Act on \n");
        tabsNamesMap.put(MessagesFilter.WAITING, "Waiting \n");
        tabsNamesMap.put(MessagesFilter.SENT, "To my \n");
        tabsNamesMap.put(MessagesFilter.INBOX, "For me \n");
        tabsNamesMap.put(MessagesFilter.ARCHIVE, "Archive \n");
        tabsNamesMap.put(MessagesFilter.DONE, "Done \n");
    }

    @Override
    protected void setUpView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        for (MessagesFilter filter : MessagesFilter.values()) {
            tabLayout.addTab(tabLayout.newTab().setText(tabsNamesMap.get(filter)).setTag(filter));
        }
        tabLayout.setOnTabSelectedListener(this);

        presenter.onFilterSelected((MessagesFilter) tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getTag());
    }

    @Override
    public void showMessagesSummary(Map<MessagesFilter, String> summary) {
        List<MessagesFilter> filters = Arrays.asList(MessagesFilter.values());
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            for (MessagesFilter filter : filters) {
                if (tab.getTag().equals(filter)) {
                    String tabText = tabsNamesMap.get(filter) + summary.get(filter);
                    tab.setText(tabText).setTag(filter);
                }
            }
        }

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
        return new MessagesFeedPresenter(new GetMessagesUseCase(), new MessagesMapper(),
                new ArchiveMessageUseCase(), new GetMessagesSummaryUseCase(), new MessagesSummaryMapper());
    }

    @Override
    public void showMessages(List<MessageModel> messages, boolean canArchive) {
        messagesAdapter = new MessagesAdapter(activity, presenter, messages, canArchive);
        if(!messages.isEmpty()){
            AndroidUtils.checkRecyclerViewIsEmpty(messages, messagesRecyclerView, tvIsEmpty);
            swipeRefreshLayout.setRefreshing(false);
        }
        messagesRecyclerView.setAdapter(messagesAdapter);
        messagesRecyclerView.addOnScrollListener(scrollListener);
   }

    @Override
    public void addMessages(List<MessageModel> messages) {
        messagesAdapter.addMessages(messages);
        handleScrolls = true;
        messagesRecyclerView.addOnScrollListener(scrollListener);
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
    public void disableLoadMore() {
        messagesRecyclerView.removeOnScrollListener(scrollListener);
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
        messagesAdapter.getMessages().clear();
        presenter.onFilterSelected((MessagesFilter) tab.getTag());
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        int tabPosition = tabLayout.getSelectedTabPosition();
        presenter.onFilterSelected((MessagesFilter) tabLayout.getTabAt(tabPosition).getTag());
    }
}
