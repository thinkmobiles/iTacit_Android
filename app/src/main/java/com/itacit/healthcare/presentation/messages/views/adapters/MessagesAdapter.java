package com.itacit.healthcare.presentation.messages.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.itacit.healthcare.R;
import com.itacit.healthcare.data.network.interceptors.AuthInterceptor;
import com.itacit.healthcare.presentation.base.widgets.picasso.CircleTransformation;
import com.itacit.healthcare.presentation.messages.models.MessageModel;
import com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private final MessagesFeedPresenter presenter;
    private Context context;
    private List<MessageModel> messages;
    private Picasso picasso;


    public MessagesAdapter(Context context, List<MessageModel> messages, MessagesFeedPresenter presenter) {
        this.context = context;
        this.messages = messages;
        this.presenter = presenter;
        OkHttpClient picassoClient = new OkHttpClient();
        picassoClient.interceptors().add(new AuthInterceptor());
        picasso = new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(picassoClient))
                .build();
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

        holder.view.setOnClickListener(e -> {
            presenter.onMessageSelected(messageModel.getId());
        });

        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                holder.archiveLl.setOnClickListener(e -> presenter.onMessageArchiveSeleced(messageModel.getId()));
            }

            @Override
            public void onClose(SwipeLayout layout) {
                holder.archiveLl.setOnClickListener(null);
            }
        });

        picasso.load(messageModel.getHeadlineUri())
                .transform(new CircleTransformation())
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

        if (messageModel.getReadRequiredYn()){
            holder.readRequiredDateTv.setVisibility(View.VISIBLE);
            holder.readRequiredDateTv.setText("The must be read by " + messageModel.getReadRequiredDate());
        } else {
            holder.readRequiredDateTv.setVisibility(View.GONE);
        }

        holder.lastTimeResponseTv.setText(messageModel.getLastTimeResponse());
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
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
