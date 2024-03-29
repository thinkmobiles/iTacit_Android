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
import com.itacit.healthcare.domain.models.RecipientModel;
import com.itacit.healthcare.domain.models.RecipientsGroupedModel;
import com.itacit.healthcare.presentation.messages.presenters.AddRecipientsPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 16.11.15.
 */
public class RecipientNamesAdapter extends RecyclerView.Adapter<RecipientNamesAdapter.RecipientNameViewHolder> {
    private final AddRecipientsPresenter presenter;
    private final RecipientsGroupedModel.RecipientType recipientType;
    private Context context;
    private List<? extends RecipientModel> models;
    private int layoutId;
    private RecipientClickListener recipientListener;

    public RecipientNamesAdapter(Context context, List<? extends RecipientModel> models, @LayoutRes int layoutId, AddRecipientsPresenter presenter, RecipientsGroupedModel.RecipientType type) {
        this.context = context;
        this.layoutId = layoutId;
        this.models = models;
        this.presenter = presenter;
        recipientType = type;
    }

    @Override
    public RecipientNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(layoutId, parent, false);
        return new RecipientNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipientNameViewHolder holder, int position) {
        RecipientModel model = models.get(position);

        holder.view.setOnClickListener(v -> {
            if (recipientListener == null) {
                return;
            }

            recipientListener.onRecipientClick(model, recipientType);
            holder.checkIv.setImageResource(presenter.isRecipientSelected(model.getId(), recipientType) ?
                    R.drawable.ic_check_act : R.drawable.ic_unselect);
        });

        holder.checkIv.setImageResource(presenter.isRecipientSelected(model.getId(), recipientType) ?
                R.drawable.ic_check_act : R.drawable.ic_unselect);
        holder.titleTv.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setRecipientSelectionListener(RecipientClickListener recipientClickListener) {
        recipientListener = recipientClickListener;
    }

    public static class RecipientNameViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)        TextView titleTv;
        @Bind(R.id.iv_check)        ImageView checkIv;
        View view;

        public RecipientNameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }
    }

    public interface RecipientClickListener {
        void onRecipientClick(RecipientModel recipient, RecipientsGroupedModel.RecipientType recipientType);
    }
}
