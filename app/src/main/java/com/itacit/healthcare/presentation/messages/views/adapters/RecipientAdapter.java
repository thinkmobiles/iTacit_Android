package com.itacit.healthcare.presentation.messages.views.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.messages.models.RecipientModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 16.11.15.
 */
public class RecipientAdapter extends RecyclerView.Adapter<RecipientAdapter.RecipientViewHolder> {
    private Context context;
    private List<? extends RecipientModel> models;
    private int layoutId;

    public RecipientAdapter(Context context, List<? extends RecipientModel> models, @LayoutRes int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        this.models = models;
    }

    @Override
    public RecipientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(layoutId, parent, false);
        return new RecipientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipientViewHolder holder, int position) {
        RecipientModel model = models.get(position);
        holder.titleTv.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class RecipientViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)        TextView titleTv;
        @Bind(R.id.iv_check)        ImageView checkIv;

        public RecipientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
