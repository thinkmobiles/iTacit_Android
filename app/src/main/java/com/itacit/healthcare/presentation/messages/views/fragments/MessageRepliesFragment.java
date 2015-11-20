package com.itacit.healthcare.presentation.messages.views.fragments;

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
import com.itacit.healthcare.presentation.messages.models.MessagesModel;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;
import com.itacit.healthcare.presentation.messages.presenters.MessageRepliesPresenter;
import com.itacit.healthcare.presentation.messages.views.MessageRepliesView;
import com.itacit.healthcare.presentation.messages.views.activity.MessagesActivity;
import com.itacit.healthcare.presentation.messages.views.adapters.RepliesAdapter;

import java.util.List;

import butterknife.Bind;

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
    @Bind(R.id.ib_reply_all_FMR)                    TextView tvReplyAll;

    public static final String Message_ID = "messageId";

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
        String messageId = getArguments().getString(Message_ID);
        return new MessageRepliesPresenter(new ListRepliesMapper(),
                new GetListRepliesUseCase(0,100),
                new MessagesMapper(),
                new GetHeaderUseCase(messageId),
                messageId);
    }

    @Override
    public void showHeaderReplies(MessagesModel messagesModel) {
        aBar.setTitle(messagesModel.getFirstName() + " " + messagesModel.getLastName());
        aBar.setSubtitle(messagesModel.getTimeSendMessage());

        tvSubject.setText(Html.fromHtml(messagesModel.getSubject()));
        tvBody.setText(Html.fromHtml(messagesModel.getBody()));

        if (messagesModel.isReadRequiredYn()) {
            tvRequestConfirmation.setVisibility(View.VISIBLE);
            tvResponseConfirmation.setVisibility(View.VISIBLE);

            tvRequestConfirmation.setText("Please confirm by " + messagesModel.getReadRequiredDate());
        } else {
            tvRequestConfirmation.setVisibility(View.GONE);
            tvResponseConfirmation.setVisibility(View.GONE);
        }

        tvNumberPeopleShared.setText(" " + messagesModel.getResponseCount());
        tvReplySender.setText(" to " + messagesModel.getFirstName());

        tvBody.post(truncated);
    }

    Runnable truncated = new Runnable() {
        public void run() {
            tvBody.makeExpandable(3, tvBody.getLayout().getLineEnd(2), tvBody.getLineCount());
        }
    };

    @Override
    public void showListReplies(List<RepliesModel> replies) {
        repliesAdapter = new RepliesAdapter(getActivity(),replies);
        repliesRecyclerView.setAdapter(repliesAdapter);
    }
}
