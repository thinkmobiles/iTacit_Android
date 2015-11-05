package com.itacit.healthcare.presentation.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.news.models.AuthorModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 02.11.15.
 */
public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.ViewHolder> {

	private Context context;
	private List<AuthorModel> authors;
	private Picasso picasso;
	private OnAuthorsItemSelectedListener authorsItemSelectedListener;

	public AuthorsAdapter(Context context, List<AuthorModel> authors) {
		this.context = context;
		this.authors = authors;
		picasso = new Picasso.Builder(context).build();
		setHasStableIds(true);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.item_author_filter, parent, false);
		return new ViewHolder(view, authorsItemSelectedListener);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		AuthorModel authorModel = authors.get(position);
		picasso.load(authorModel.getImageUri()).into(holder.ivIcon);
		holder.tvName.setText(authorModel.getFullName());
		holder.tvPosition.setText(authorModel.getRole());
	}

	@Override
	public long getItemId(int position) {
		return authors.get(position).getId();
	}

	@Override
	public int getItemCount() {
		return authors.size();
	}

	public void setOnAuthorsItemSelectedListener(OnAuthorsItemSelectedListener listener) {
		this.authorsItemSelectedListener = listener;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@Bind(R.id.iv_filter_IAF)
		ImageView ivFilter;
		@Bind(R.id.iv_icon_IAF)
		ImageView ivIcon;
		@Bind(R.id.tv_name_IAF)
		TextView tvName;
		@Bind(R.id.tv_position_IAF)
		TextView tvPosition;

		private OnAuthorsItemSelectedListener authorsItemSelectedListener;

		public ViewHolder(View itemView, OnAuthorsItemSelectedListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(this);
			authorsItemSelectedListener = listener;
		}

		@Override
		public void onClick(View v) {
			if (authorsItemSelectedListener != null) {
				authorsItemSelectedListener.onAuthorsItemSelected(getItemId());
			}
		}
	}

	public interface OnAuthorsItemSelectedListener {
		void onAuthorsItemSelected(long authorId);
	}
}