package com.itacit.healthcare.presentation.messages.views.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itacit.healthcare.R;
import com.itacit.healthcare.data.entries.Recipient;
import com.itacit.healthcare.domain.interactor.messages.ConfirmMessageReadUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetListRepliesUseCase;
import com.itacit.healthcare.domain.interactor.messages.GetMessageDetailsUseCase;
import com.itacit.healthcare.global.utils.AndroidUtils;
import com.itacit.healthcare.presentation.base.fragments.BaseFragmentView;
import com.itacit.healthcare.presentation.base.widgets.expandableTextView.ExpandableTextView;
import com.itacit.healthcare.presentation.messages.mappers.ListRepliesMapper;
import com.itacit.healthcare.presentation.messages.mappers.MessagesMapper;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;
import com.itacit.healthcare.presentation.messages.presenters.MessageDetailsPresenter;
import com.itacit.healthcare.presentation.messages.views.MessageDetailsView;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.views.adapters.RepliesAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Den on 17.11.15.
 */
public class MessageDetailsFragment extends BaseFragmentView<MessageDetailsPresenter, MessagesActivity> implements MessageDetailsView {
    @Bind(R.id.rl_messages_replies_header_FMD)      RelativeLayout mesageDetailsRl;
    @Bind(R.id.recycler_view_FMD)                   RecyclerView repliesRecyclerView;
    @Bind(R.id.tv_subject_FMD)                      TextView tvSubject;
    @Bind(R.id.tv_body_FMD)                         ExpandableTextView tvBody;
    @Bind(R.id.tv_request_for_confirmation_FMD)     TextView tvRequestConfirmation;
    @Bind(R.id.cb_response_for_confirmation_FMD)    TextView tvResponseConfirmation;
    @Bind(R.id.tv_number_people_shared_FMD)         TextView tvNumberPeopleShared;
    @Bind(R.id.tv_number_people_read_FMD)           TextView tvNumberPeopleRead;
    @Bind(R.id.tv_reply_to_sender_FMD)              TextView tvReplySender;
    @Bind(R.id.tv_reply_all_FMD)                    TextView tvReplyAll;

    public static final String Message_ID = "messageId";
    public static final String Reply_Recipient = "replyRecipient";
    public static final String IS_PRIVATE = "isPrivate";
    private String messageId = "";
    private String userName = "";
    private RepliesAdapter repliesAdapter;
    private ActionBar aBar;

    private List<Recipient> recipientsList;

    @Override
    @OnClick(R.id.tv_reply_all_FMD)
    public void replyToAll() {
        createReply(false);
    }

    @Override
    @OnClick(R.id.tv_reply_to_sender_FMD)
    public void privateReply() {
        createReply(true);
    }

    @Override
    protected void setUpView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        repliesRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_message_details;
    }

    @Override
    protected void setUpActionBar(ActionBar actionBar) {
        switchToolbarIndicator(false, v -> activity.switchContent(MessagesFeedFragment.class));

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
    protected MessageDetailsPresenter createPresenter() {
        messageId = getArguments().getString(Message_ID);
        return new MessageDetailsPresenter(new ListRepliesMapper(),
                new GetListRepliesUseCase(),
                new MessagesMapper(),
                new GetMessageDetailsUseCase(),
                new ConfirmMessageReadUseCase(),
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

        if (messageModel.isReadRequiredYn() && !messageModel.isUserMarksRead()) {
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

        recipientsList = messageModel.getRecipientsList();
        int count = 0;
        for (Recipient r : recipientsList) {
            if (r.getReadConfirmedYn().equals("Y")) {
                count++;
            }
        }
        if (count <= 0) {
            tvNumberPeopleRead.setVisibility(View.GONE);
        } else {
            tvNumberPeopleRead.setVisibility(View.VISIBLE);
            tvNumberPeopleRead.setText(" " + count);
        }

        tvResponseConfirmation.setOnClickListener(e -> presenter.sendResponseConfirm());
        mesageDetailsRl.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConfirmed() {
        sendResponse();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListReplies(List<RepliesModel> replies) {
        repliesAdapter = new RepliesAdapter(getActivity(), replies);
        AndroidUtils.checkRecyclerViewIsEmpty(replies, repliesRecyclerView, tvIsEmpty);
        repliesRecyclerView.setAdapter(repliesAdapter);

    }

    private void sendResponse() {
        if (tvBody.isExpandable()) {
            presenter.sendResponseConfirm();
            tvRequestConfirmation.setVisibility(View.GONE);
            tvResponseConfirmation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_compl, 0, 0, 0);
            tvResponseConfirmation.setTextColor(Color.GRAY);

            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            SimpleDateFormat format = new SimpleDateFormat("MMM.dd", Locale.CANADA);

            tvResponseConfirmation.setText(" Confirmed read on " + format.format(date));
        } else {
            tvBody.showMore();
        }
    }

    private void createReply(Boolean isPrivate) {
        Bundle args = new Bundle(3);
        args.putString(MessageDetailsFragment.Message_ID, messageId);
        if (isPrivate) {
            args.putString(MessageDetailsFragment.Reply_Recipient, userName);
        }
        args.putBoolean(MessageDetailsFragment.IS_PRIVATE, isPrivate);
        activity.switchContent(NewReplyFragment.class, args);
    }
}
