package com.itacit.healthcare.presentation.messages.views.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.domain.interactor.messages.GetHeaderUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetListRepliesUseCase;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.expandableTextView.ExpandableTextView;
import com.itacit.healthcare.presentation.messages.mappers.ListRepliesMapper;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;
import com.itacit.healthcare.presentation.messages.presenters.MessageRepliesPresenter;
import com.itacit.healthcare.presentation.messages.views.MessageRepliesView;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.views.adapters.RepliesAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Den on 17.11.15.
 */
public class MessageRepliesFragment extends BaseFragmentView<MessageRepliesPresenter,MessagesActivity> implements MessageRepliesView {

    @Bind(R.id.recycler_view_FMR)                   RecyclerView repliesRecyclerView;
    @Bind(R.id.tv_subject_FMR)                      TextView tvSubject;
    @Bind(R.id.tv_body_FMR)                         ExpandableTextView tvBody;
    @Bind(R.id.tv_request_for_confirmation_FMR)     TextView tvRequestConfirmation;
    @Bind(R.id.tv_response_for_confirmation_FMR)    TextView tvResponseConfirmation;
    @Bind(R.id.tv_number_people_shared_FMR)         TextView tvNumberPeopleShared;
    @Bind(R.id.tv_number_people_read_FMR)           TextView tvNumberPeopleRead;
    @Bind(R.id.tv_number_attachment_FMR)            TextView tvNumberAttachment;
    @Bind(R.id.tv_reply_to_sender_FMR)              TextView tvReplySender;
    @Bind(R.id.tv_reply_all_FMR)                    TextView tvReplyAll;

    public static final String Message_ID = "messageId";
    public static final String Reply_Recipient = "replyRecipient";
    public static final String IS_PRIVATE = "isPrivate";
	private String messageId = "";
	private String userName = "";
    private RepliesAdapter repliesAdapter;
    private ActionBar aBar;

    @Override
    protected void setUpView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        repliesRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_message_replies;
    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {
        switchToolbarIndicator(false, v -> activity.switchContent(MessagesFeedFragment.class, false));

        aBar = actionBar;
        actionBar.setHomeAsUpIndicator(null);
        activity.setActionBarShadowVisible(true);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
    }

    @Override
    protected MessageRepliesPresenter createPresenter() {
        messageId = getArguments().getString(Message_ID);
        return new MessageRepliesPresenter(new ListRepliesMapper(),
                new GetListRepliesUseCase(),
                new MessagesMapper(),
                new GetHeaderUseCase(),
                messageId);
    }

    @Override
    public void showHeaderReplies(MessageModel messageModel) {
        userName = messageModel.getFirstName() + " " + messageModel.getLastName();
        aBar.setTitle(userName);
        aBar.setSubtitle(messageModel.getTimeSendMessage());
        tvBody.setVisibility(View.INVISIBLE);
        tvSubject.setText(Html.fromHtml(messageModel.getSubject()));
        tvBody.setText(Html.fromHtml(messageModel.getBody()));

        if (messageModel.isReadRequiredYn()) {
            tvRequestConfirmation.setVisibility(View.VISIBLE);
            tvResponseConfirmation.setVisibility(View.VISIBLE);

            tvRequestConfirmation.setText("Please confirm by " + messageModel.getReadRequiredDate());
        } else {
            tvRequestConfirmation.setVisibility(View.GONE);
            tvResponseConfirmation.setVisibility(View.GONE);
        }

        tvNumberPeopleShared.setText(" " + messageModel.getResponseCount());
        tvReplySender.setText(" to " + messageModel.getFirstName());

        tvBody.post(() -> tvBody.makeExpandable(3, tvBody.getLayout().getLineEnd(2), tvBody.getLineCount()));
        tvBody.setVisibility(View.VISIBLE);
    }

    @Override
    public void showListReplies(List<RepliesModel> replies) {
        repliesAdapter = new RepliesAdapter(getActivity(),replies);
        repliesRecyclerView.setAdapter(repliesAdapter);
    }

    @Override
    @OnClick(R.id.tv_reply_all_FMR)
    public void replyToAll() {
		createReply(false);
    }

    @Override
    @OnClick(R.id.tv_reply_to_sender_FMR)
    public void privateReply() {
	    createReply(true);
    }

	private void createReply(Boolean isPrivate) {
		Bundle args = new Bundle(3);
		args.putString(MessageRepliesFragment.Message_ID, messageId);
        if (isPrivate) {
            args.putString(MessageRepliesFragment.Reply_Recipient, userName);
        }
		args.putBoolean(MessageRepliesFragment.IS_PRIVATE, isPrivate);
		activity.switchContent(NewReplyFragment.class, true, args);
	}
}
