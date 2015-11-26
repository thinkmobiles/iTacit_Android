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
import com.itacit.healthcare.global.utils.AndroidUtils;
import com.itacit.healthcare.presentation.base.widgets.expandableTextView.ExpandableTextView;
import com.itacit.healthcare.presentation.base.widgets.picasso.CircleTransformation;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;
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
        picasso = AndroidUtils.createPicassoWithAuth(context);
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

        holder.senderNameTv.setText(repliesModel.getSenderNameFull());
        holder.senderRoleNameTv.setText(repliesModel.getSenderRoleName());
        holder.bodyTv.setText(Html.fromHtml(repliesModel.getBody()));
        holder.bodyTv.post(() -> holder.bodyTv.makeExpandable(2, holder.bodyTv.getLayout().getLineEnd(1), holder.bodyTv.getLineCount()));
        holder.lastTimeResponseTv.setText(repliesModel.getSendDateTime());

        holder.privateIv.setVisibility(repliesModel.isReplyPrivateYn() ? View.VISIBLE : View.GONE);
        holder.readConfirmedIv.setVisibility(repliesModel.isReadConfirmedYn() ? View.VISIBLE : View.GONE);

        if(repliesModel.isReplyMethodEmailYn() && !repliesModel.isReplyMethodSMSYn()) {
            holder.methodSendingIv.setImageResource(R.drawable.ic_mail);
            holder.privateIv.setVisibility(View.VISIBLE);
            holder.viaTv.setVisibility(View.VISIBLE);
        } else if(repliesModel.isReplyMethodSMSYn() && !repliesModel.isReplyMethodEmailYn()) {
            holder.methodSendingIv.setImageResource(R.drawable.ic_phone);
            holder.privateIv.setVisibility(View.VISIBLE);
            holder.viaTv.setVisibility(View.VISIBLE);
        } else {
            holder.privateIv.setVisibility(View.GONE);
            holder.viaTv.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_headline_LIMR)            ImageView headlineIv;
        @Bind(R.id.tv_sender_name_LIMR)         TextView senderNameTv;
        @Bind(R.id.tv_last_time_response_LIMR)  TextView lastTimeResponseTv;
        @Bind(R.id.tv_sender_role_LIMR)         TextView senderRoleNameTv;
        @Bind(R.id.tv_body_LIMR)                ExpandableTextView bodyTv;
        @Bind(R.id.iv_private_LIMR)             ImageView privateIv;
        @Bind(R.id.iv_method_sending_LIMR)      ImageView methodSendingIv;
        @Bind(R.id.tv_via_LIMR)                 TextView viaTv;
        @Bind(R.id.iv_confirmation_LIMR)        ImageView readConfirmedIv;

        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
