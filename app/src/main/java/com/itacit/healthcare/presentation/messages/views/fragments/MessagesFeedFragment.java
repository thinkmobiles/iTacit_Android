package com.itacit.healthcare.presentation.messages.views.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import butterknife.OnClick;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesFeedFragment extends BaseFragmentView<MessagesFeedPresenter, MessagesActivity> implements MessagesFeedView, TabLayout.OnTabSelectedListener {
    @Bind(R.id.recycler_view_FMF)
    RecyclerView messagesRecyclerView;
    @Bind(R.id.tab_layout_FMF)
    TabLayout tabLayout;

    private MessagesAdapter messagesAdapter;
    private OnTabItemSelectedListener tabItemSelectedListener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout.addTab(tabLayout.newTab().setText("All \n1"));
        tabLayout.addTab(tabLayout.newTab().setText("Act on \n2"));
        tabLayout.addTab(tabLayout.newTab().setText("Waiting \n3"));
        tabLayout.addTab(tabLayout.newTab().setText("To my \n4"));
        tabLayout.addTab(tabLayout.newTab().setText("For me \n5"));

        tabLayout.setOnTabSelectedListener(this);

    }

    @OnClick(R.id.fab_button_FMF)
    void addNewMessage(){
        activity.switchContent(NewMessageFragment.class, false);
    }

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
        return new MessagesFeedPresenter(new GetMessagesUseCase(0,100), new MessagesMapper(),this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search_fmf, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search_FMFM:
                Toast.makeText(getActivity(),"Search",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMessages(List<MessagesModel> messages) {
        messagesAdapter = new MessagesAdapter(getActivity(), messages);
        messagesRecyclerView.setAdapter(messagesAdapter);

        messagesAdapter.setOnMessagesItemSelectedListener(this::showMessagesItemDetails);
    }

    public void showMessagesItemDetails(String messageId) {
//        Bundle args = new Bundle(1);
//        args.putString(MessageResponseFragment.Message_ID, messageId);
//        activity.switchContent(MessageResponseFragment.class, true, args);
        Toast.makeText(getActivity(),messageId,Toast.LENGTH_SHORT).show();

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

    public void setOnTabItemSelectedListener(OnTabItemSelectedListener listener) {
        this.tabItemSelectedListener = listener;
    }

    private void checkTabSelected(TabLayout.Tab tab) {
        String filter = null;
        if (tabItemSelectedListener != null) {
            switch (tab.getPosition()) {
                case 0:
                    filter = GetMessagesUseCase.Filter.ALL.toString();
                    break;
                case 1:
                    filter = GetMessagesUseCase.Filter.ACT.toString();
                    break;
                case 2:
                    filter = GetMessagesUseCase.Filter.WAITING.toString();
                    break;
                case 3:
                    filter = GetMessagesUseCase.Filter.SENT.toString();
                    break;
                case 4:
                    filter = GetMessagesUseCase.Filter.INBOX.toString();
                    break;
            }
            tabItemSelectedListener.onTabItemSelected(filter);
        }
    }

    public interface OnTabItemSelectedListener {
        void onTabItemSelected(String filter);
    }

}
