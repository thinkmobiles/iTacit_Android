package com.itacit.healthcare.presentation.messages.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Den on 12.11.15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private Context context;
    private List<MessagesModel> messages;
    private Picasso picasso;
    private OnMessagesItemSelectedListener messagesItemSelectedListener;

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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessagesModel messagesModel = messages.get(position);

        if (messagesItemSelectedListener != null) {
            holder.view.setOnClickListener(e -> {
                if (messagesItemSelectedListener != null) {
                    messagesItemSelectedListener.onMessagesItemSelected(messagesModel.getId());
                }
            });
        }

        picasso.load(messagesModel.getHeadlineUri())
                .transform(new CircleTransformation())
                .fit()
                .into(holder.headlineIv);

        holder.senderNameTv.setText(messagesModel.getSenderName());
        holder.senderRoleNameTv.setText(messagesModel.getSenderRoleName());
        holder.subjectTv.setText(messagesModel.getSubject());
        holder.bodyTv.setText(Html.fromHtml(messagesModel.getBody()));

        if(messagesModel.getNumberOfResponse() > 0){
            holder.numberOfResponseTv.setVisibility(View.VISIBLE);
            holder.numberOfResponseTv.setText(messagesModel.getNumberOfResponse().toString());
        } else {
            holder.numberOfResponseTv.setVisibility(View.INVISIBLE);
        }

        if(messagesModel.getReadRequiredYn()){
            holder.readRequiredDateTv.setVisibility(View.VISIBLE);
            holder.readRequiredDateTv.setText("The must be read by " + messagesModel.getReadRequiredDate());
        }else {
            holder.readRequiredDateTv.setVisibility(View.GONE);
        }

        holder.lastTimeResponseTv.setText(messagesModel.getLastTimeResponse());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setOnMessagesItemSelectedListener(OnMessagesItemSelectedListener listener) {
        this.messagesItemSelectedListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_headline_LIM)
        ImageView headlineIv;
        @Bind(R.id.tv_sender_name_LIM)
        TextView senderNameTv;
        @Bind(R.id.tv_last_time_response_LIM)
        TextView lastTimeResponseTv;
        @Bind(R.id.tv_number_response_LIM)
        TextView numberOfResponseTv;
        @Bind(R.id.tv_sender_role_LIM)
        TextView senderRoleNameTv;
        @Bind(R.id.tv_subject_LIM)
        TextView subjectTv;
        @Bind(R.id.tv_body_LIM)
        TextView bodyTv;
        @Bind(R.id.tv_read_required_date_LIM)
        TextView readRequiredDateTv;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnMessagesItemSelectedListener {
        void onMessagesItemSelected(String messageId);
    }
}
