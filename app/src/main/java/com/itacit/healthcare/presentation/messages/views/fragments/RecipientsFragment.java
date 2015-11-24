package com.itacit.healthcare.presentation.messages.views.fragments;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.messages.GetRecipientsFullListUseCase;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.messages.models.UserRecipientModel;
import com.itacit.healthcare.presentation.messages.presenters.RecipientsPresenter;
import com.itacit.healthcare.presentation.messages.views.MessageStorage;
import com.itacit.healthcare.presentation.messages.views.RecipientsView;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.views.adapters.UsersRecipientAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by root on 23.11.15.
 */
public class RecipientsFragment extends BaseFragmentView<RecipientsPresenter, MessagesActivity>
        implements RecipientsView {
    @Bind(R.id.rv_userRecipients_FRL)   RecyclerView usersRecipientsRv;
    private UsersRecipientAdapter adapter;

    @Override
    protected void setUpView() {
        usersRecipientsRv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {
        switchToolbarIndicator(false, v -> activity.switchContent(NewMessageFragment.class));
        actionBar.setTitle(R.string.recipients);
        activity.setActionBarShadowVisible(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                presenter.selectRecipients();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recipients_list;
    }

    @Override
    protected RecipientsPresenter createPresenter() {
        return new RecipientsPresenter(new GetRecipientsFullListUseCase());
    }

    @Override
    public void showRecipients(List<UserRecipientModel> usersRecipients) {
        adapter = new UsersRecipientAdapter(activity, usersRecipients, presenter);
        usersRecipientsRv.setAdapter(adapter);
    }

    @Override
    public void removeRecipient(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public MessageStorage getMessageStorage() {
        return activity;
    }

    @Override
    public void navigateToCreateMessage() {
        activity.switchContent(NewMessageFragment.class);
    }
}
