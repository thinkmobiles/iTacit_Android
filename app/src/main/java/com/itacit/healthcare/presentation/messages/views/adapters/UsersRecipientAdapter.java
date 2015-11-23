package com.itacit.healthcare.presentation.messages.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.data.network.interceptors.AuthInterceptor;
import com.itacit.healthcare.presentation.base.widgets.picasso.CircleTransformation;
import com.itacit.healthcare.presentation.messages.models.UserRecipientModel;
import com.itacit.healthcare.presentation.messages.presenters.RecipientsPresenter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 23.11.15.
 */
public class UsersRecipientAdapter extends RecyclerView.Adapter<UsersRecipientAdapter.UserRecipientViewHolder> {
    private final RecipientsPresenter presenter;
    private Context context;
    private Picasso picasso;

    public List<UserRecipientModel> getUsersRecipients() {
        return users;
    }

    private List<UserRecipientModel> users;

    public UsersRecipientAdapter(Context context, List<UserRecipientModel> users, RecipientsPresenter presenter) {
        this.context = context;
        this.users = users;
        this.presenter = presenter;
        OkHttpClient picassoClient = new OkHttpClient();
        picassoClient.interceptors().add(new AuthInterceptor());
        picasso = new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(picassoClient))
                .build();
    }

    @Override
    public UserRecipientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_search_users, parent, false);;
        return new UserRecipientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserRecipientViewHolder holder, int position) {
        UserRecipientModel userModel = users.get(position);
        picasso.load(userModel.getImageUri())
                .transform(new CircleTransformation())
                .fit()
                .into(holder.ivIcon);
        holder.tvName.setText(userModel.getFullName());
        String role = userModel.getRole() + ", " + userModel.getBusiness();
        holder.tvRole.setText(role);
        holder.ivCheck.setImageResource(R.drawable.btn_del_circle);
        holder.view.setOnClickListener(v -> {
            presenter.onUserRecipientClick(userModel.getId());
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class UserRecipientViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_icon_LISU)    ImageView ivIcon;
        @Bind(R.id.tv_name_LISU)    TextView tvName;
        @Bind(R.id.tv_role_LISU)    TextView tvRole;
        @Bind(R.id.iv_check_LISU)   ImageView ivCheck;

        public View view;

        UserRecipientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }
    }

}
