package com.itacit.healthcare.presentation.messages.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.data.network.interceptors.AuthInterceptor;
import com.itacit.healthcare.presentation.base.widgets.picasso.CircleTransformation;
import com.itacit.healthcare.presentation.messages.models.MessagesModel;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private Context context;
    private List<MessagesModel> messages;
    private Picasso picasso;
    private OnNewsItemSelectedListener newsItemSelectedListener;

    public MessagesAdapter(Context context, List<MessagesModel> messages) {
        this.context = context;
        this.messages = messages;

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
        return new ViewHolder(view, newsItemSelectedListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessagesModel messagesModel = messages.get(position);

        picasso.load(messagesModel.getHeadlineUri())
                .transform(new CircleTransformation())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .fit()
                .into(holder.headlineIv);

        holder.senderNameTv.setText(messagesModel.getSenderName());
        holder.lastTimeResponseTv.setText("12 min ago");
        holder.numberOfResponseTv.setText("12");
        holder.positionTv.setText(messagesModel.getSenderPosition());
        holder.subjectTv.setText(messagesModel.getSubject());
        holder.contentTv.setText(Html.fromHtml(messagesModel.getContent()));
    }

    @Override
    public long getItemId(int position) {
        return messages.get(position).getId();
    }

    public void setOnNewsItemSelectedListener(OnNewsItemSelectedListener listener) {
        this.newsItemSelectedListener = listener;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.iv_headline_LIM)
        ImageView headlineIv;
        @Bind(R.id.tv_name_LIM)
        TextView senderNameTv;
        @Bind(R.id.tv_last_time_response_LIM)
        TextView lastTimeResponseTv;
        @Bind(R.id.tv_number_response_LIM)
        TextView numberOfResponseTv;
        @Bind(R.id.tv_position_LIM)
        TextView positionTv;
        @Bind(R.id.tv_subject_LIM)
        TextView subjectTv;
        @Bind(R.id.tv_content_LIM)
        TextView contentTv;

        private OnNewsItemSelectedListener newsItemSelectedListener;

        public ViewHolder(View itemView, OnNewsItemSelectedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            newsItemSelectedListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (newsItemSelectedListener != null) {
                newsItemSelectedListener.onNewsItemSelected(getItemId());
            }
        }
    }

    public interface OnNewsItemSelectedListener {
        void onNewsItemSelected(long newsId);
    }
}
