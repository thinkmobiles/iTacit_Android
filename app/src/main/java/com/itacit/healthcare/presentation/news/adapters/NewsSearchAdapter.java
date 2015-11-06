package com.itacit.healthcare.presentation.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.news.models.CategoryModel;
import com.itacit.healthcare.presentation.news.models.NewsModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 06.11.15.
 */
public class NewsSearchAdapter extends RecyclerView.Adapter<NewsSearchAdapter.ViewHolder> {

	private Context context;
	private List<NewsModel> news;
	private OnNewsSearchItemSelectedListener newsSearchItemSelectedListener;

	public NewsSearchAdapter(Context context, List<NewsModel> news) {
		this.context = context;
		this.news = news;
		setHasStableIds(true);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.list_item_search_news, parent, false);
		return new ViewHolder(view, newsSearchItemSelectedListener);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		NewsModel newsModel = news.get(position);
		holder.tvTitle.setText(newsModel.getHeadline());
	}

	@Override
	public long getItemId(int position) {
		return news.get(position).getArticleId();
	}

	@Override
	public int getItemCount() {
		return news.size();
	}

	public void setOnNewsItemSelectedListener(OnNewsSearchItemSelectedListener listener) {
		this.newsSearchItemSelectedListener = listener;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@Bind(R.id.tv_title_LISN)
		TextView tvTitle;

		private OnNewsSearchItemSelectedListener newsSearchItemSelectedListener;

		public ViewHolder(View itemView, OnNewsSearchItemSelectedListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(this);
			newsSearchItemSelectedListener = listener;
		}

		@Override
		public void onClick(View v) {
			if (newsSearchItemSelectedListener != null) {
				newsSearchItemSelectedListener.onNewsSearchItemSelected(getItemId());
			}
		}
	}

	public interface OnNewsSearchItemSelectedListener {
		void onNewsSearchItemSelected(long newsId);
	}
}
