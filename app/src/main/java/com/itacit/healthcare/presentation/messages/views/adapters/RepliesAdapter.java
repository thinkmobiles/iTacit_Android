package com.itacit.healthcare.presentation.messages.views.adapters;

import android.content.Context;
import android.net.Uri;
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
import com.itacit.healthcare.presentation.messages.models.RepliesModel;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Den on 18.11.15.
 */
public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.ViewHolder> {

    private Context context;
    private List<RepliesModel> replies;
    private Picasso picasso;

    public RepliesAdapter(Context _context, List<RepliesModel> _replies) {
        context = _context;
        replies = _replies;

        OkHttpClient picassoClient = new OkHttpClient();
        picassoClient.interceptors().add(new AuthInterceptor());
        picasso = new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(picassoClient))
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_message_reply, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RepliesModel repliesModel = replies.get(position);

        picasso.load(repliesModel.getSenderImageUri())
                .transform(new CircleTransformation())
                .fit()
                .into(holder.headlineIv);

        Log.d("WWW", " img:" + repliesModel.getSenderImageUri() +
                "\n role: " + repliesModel.getSenderRoleName() +
                "\n id: " + repliesModel.getId());

        holder.senderNameTv.setText(repliesModel.getSenderNameFull());
        holder.senderRoleNameTv.setText(repliesModel.getSenderRoleName());
        holder.bodyTv.setText(Html.fromHtml(repliesModel.getBody()));

        holder.lastTimeResponseTv.setText(repliesModel.getSendDateTime());
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_headline_LIMR)
        ImageView headlineIv;
        @Bind(R.id.tv_sender_name_LIMR)
        TextView senderNameTv;
        @Bind(R.id.tv_last_time_response_LIMR)
        TextView lastTimeResponseTv;
        @Bind(R.id.tv_sender_role_LIMR)
        TextView senderRoleNameTv;
        @Bind(R.id.tv_body_LIMR)
        TextView bodyTv;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
