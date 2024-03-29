package com.itacit.healthcare.presentation.news.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.global.utils.AndroidUtils;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.itacit.healthcare.presentation.base.widgets.chipsView.Chip.FilterType;

/**
 * Created by root on 02.11.15.
 */
public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.ViewHolder> {
	private final FilterType filterType = FilterType.Author;
	private Context context;
	private List<AuthorModel> authors;
	private List<String> selectedAuthorsIds = new ArrayList<>();
	private Picasso picasso;
	private FilterSelectionListener authorsItemSelectedListener;

	public AuthorsAdapter(Context context, List<AuthorModel> authors) {
		this.context = context;
		this.authors = authors;
		picasso = AndroidUtils.createPicassoWithAuth(context);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.list_item_author_filter, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		AuthorModel authorModel = authors.get(position);
		picasso.load(authorModel.getImageUri()).into(holder.ivIcon);
		holder.view.setOnClickListener(v -> {
            boolean isSelected = selectedAuthorsIds.contains(authorModel.getId());
            if (isSelected) {
                selectedAuthorsIds.remove(authorModel.getId());
                authorsItemSelectedListener.onFilterDeselected(authorModel.getId(), filterType);
            } else {
                selectedAuthorsIds.add(authorModel.getId());
                authorsItemSelectedListener.onFilterSelected(authorModel.getId(), filterType);
            }

            isSelected = !isSelected;
            holder.ivFilter.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        });
		holder.tvName.setText(authorModel.getFullName());
		holder.tvPosition.setText(authorModel.getRole());
		if (selectedAuthorsIds.contains(authorModel.getId())) {
			holder.ivFilter.setVisibility(View.VISIBLE);
		} else {
			holder.ivFilter.setVisibility(View.INVISIBLE);
		}
	}

	public List<String> getSelectedAuthorsIds() {
		return selectedAuthorsIds;
	}

	@Override
	public int getItemCount() {
		return authors.size();
	}

	public void setItemSelectionListener(FilterSelectionListener listener) {
		this.authorsItemSelectedListener = listener;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.iv_filter_IAF)		ImageView ivFilter;
		@Bind(R.id.iv_icon_IAF)			ImageView ivIcon;
		@Bind(R.id.tv_name_IAF)			TextView tvName;
		@Bind(R.id.tv_position_IAF)		TextView tvPosition;

		public View view;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			view = itemView;
		}
	}
}
