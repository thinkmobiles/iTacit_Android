package com.itacit.healthcare.presentation.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.news.models.CategoryModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 02.11.15.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

	private Context context;
	private List<CategoryModel> categories;
	private List<Long> selectedCategoriesIds;
	private OnCategoriesItemSelectedListener categoriesItemSelectedListener;

	public CategoriesAdapter(Context context, List<CategoryModel> categories, List<Long> selectedCategoriesIds) {
		this.context = context;
		this.categories = categories;
		this.selectedCategoriesIds = selectedCategoriesIds;
		setHasStableIds(true);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.list_item_category_filter, parent, false);
		return new ViewHolder(view, categoriesItemSelectedListener);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		CategoryModel categoryModel = categories.get(position);
		holder.tvName.setText(categoryModel.getName());
		if (selectedCategoriesIds.contains(categoryModel.getId())) {
			holder.ivFilter.setVisibility(View.VISIBLE);
		} else {
			holder.ivFilter.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public long getItemId(int position) {
		return categories.get(position).getId();
	}

	@Override
	public int getItemCount() {
		return categories.size();
	}

	public void setOnCategoriesItemSelectedListener(OnCategoriesItemSelectedListener listener) {
		this.categoriesItemSelectedListener = listener;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@Bind(R.id.iv_filter_IAF)
		ImageView ivFilter;
		@Bind(R.id.tv_name_IAF)
		TextView tvName;
		private OnCategoriesItemSelectedListener categoriesItemSelectedListener;

		public ViewHolder(View itemView, OnCategoriesItemSelectedListener listener) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(this);
			categoriesItemSelectedListener = listener;
		}

		@Override
		public void onClick(View v) {
			if (categoriesItemSelectedListener != null) {
				categoriesItemSelectedListener.onCategoriesItemSelected(getItemId());
				ivFilter.setVisibility(View.VISIBLE);
			}
		}
	}

	public interface OnCategoriesItemSelectedListener {
		void onCategoriesItemSelected(long categoryId);
	}
}
