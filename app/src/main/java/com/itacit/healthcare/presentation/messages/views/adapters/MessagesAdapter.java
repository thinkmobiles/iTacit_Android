package com.itacit.healthcare.presentation.messages.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.itacit.healthcare.R;
import com.itacit.healthcare.global.utils.AndroidUtils;
import com.itacit.healthcare.presentation.base.widgets.picasso.CircleTransformation;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private MessagesFeedPresenter presenter;
    private Context context;
    private List<MessageModel> messages;
    private Boolean canSwipe;
    private Picasso picasso;

    public MessagesAdapter(Context context, MessagesFeedPresenter presenter, List<MessageModel> messages, Boolean canSwipe) {
        this.context = context;
        this.presenter = presenter;
        this.messages = messages;
        this.canSwipe = canSwipe;
        picasso = AndroidUtils.createPicassoWithAuth(context);
    }

    public MessagesFeedPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(MessagesFeedPresenter _presenter) {
        presenter = _presenter;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context _context) {
        context = _context;
    }

    public void addMessages(List<MessageModel> messages) {
        messages.addAll(messages);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageModel messageModel = messages.get(position);

        holder.messageRl.setOnClickListener(e -> {
            presenter.onMessageSelected(messageModel.getId());
        });

        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                holder.archiveLl.setOnClickListener(e -> presenter.onMessageArchiveSelected(messageModel.getId()));
            }

            @Override
            public void onClose(SwipeLayout layout) {
                holder.archiveLl.setOnClickListener(null);
            }
        });

        picasso.load(messageModel.getHeadlineUri())
                .transform(new CircleTransformation())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .fit()
                .into(holder.headlineIv);

        holder.senderNameTv.setText(messageModel.getSenderName());
        holder.senderRoleNameTv.setText(messageModel.getSenderRoleName());
        holder.subjectTv.setText(messageModel.getSubject());
        holder.bodyTv.setText(Html.fromHtml(messageModel.getBody()));

        if (messageModel.getNumberOfResponse() > 0){
            holder.numberOfResponseTv.setVisibility(View.VISIBLE);
            holder.numberOfResponseTv.setText(messageModel.getNumberOfResponse().toString());
        } else {
            holder.numberOfResponseTv.setVisibility(View.INVISIBLE);
        }

        if (messageModel.isReadRequiredYn()){
            holder.readRequiredDateTv.setVisibility(View.VISIBLE);
            holder.readRequiredDateTv.setText("The must be read by " + messageModel.getReadRequiredDate());
        } else {
            holder.readRequiredDateTv.setVisibility(View.GONE);
        }

        holder.lastTimeResponseTv.setText(messageModel.getTimeSendMessage());

        if (canSwipe) {
	        holder.swipeLayout.setRightSwipeEnabled(false);
        }
    }

    public int getMessagePosition(String messageId) {
        for (MessageModel message : messages) {
            if (message.getId().equals(messageId)) {
                return messages.indexOf(message);
            }
        }
        return -1;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_headline_LIM)             ImageView headlineIv;
        @Bind(R.id.tv_sender_name_LIM)          TextView senderNameTv;
        @Bind(R.id.tv_last_time_response_LIM)   TextView lastTimeResponseTv;
        @Bind(R.id.tv_number_response_LIM)      TextView numberOfResponseTv;
        @Bind(R.id.tv_sender_role_LIM)          TextView senderRoleNameTv;
        @Bind(R.id.tv_subject_LIM)              TextView subjectTv;
        @Bind(R.id.tv_body_LIM)                 TextView bodyTv;
        @Bind(R.id.tv_read_required_date_LIM)   TextView readRequiredDateTv;
        @Bind(R.id.swipe_LIM)                   SwipeLayout swipeLayout;
        @Bind(R.id.ll_archive_LIM)              LinearLayout archiveLl;
        @Bind(R.id.rl_messageItem_FIM)
        RelativeLayout messageRl;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
