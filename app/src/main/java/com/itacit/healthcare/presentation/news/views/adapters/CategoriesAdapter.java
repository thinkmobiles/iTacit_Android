package com.itacit.healthcare.presentation.news.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.presentation.news.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.itacit.healthcare.presentation.base.widgets.chipsView.Filter.FilterType;

/**
 * Created by root on 02.11.15.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
	private final FilterType filterType = FilterType.Category;
	private Context context;
	private List<CategoryModel> categories;
	private List<String> selectedCategoriesIds = new ArrayList<>();
	private FilterSelectionListener categoriesListener;

	public CategoriesAdapter(Context context, List<CategoryModel> categories) {
		this.context = context;
		this.categories = categories;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.list_item_category_filter, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		CategoryModel categoryModel = categories.get(position);
		holder.view.setOnClickListener(v -> {
            boolean isSelected = selectedCategoriesIds.contains(categoryModel.getId());
            if (isSelected) {
                selectedCategoriesIds.remove(categoryModel.getId());
                categoriesListener.onFilterDeselected(categoryModel.getId(), filterType);
            } else {
                selectedCategoriesIds.add(categoryModel.getId());
                categoriesListener.onFilterSelected(categoryModel.getId(), filterType);
            }

            isSelected = !isSelected;
            holder.ivFilter.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        });

		holder.tvName.setText(categoryModel.getName());
		if (selectedCategoriesIds.contains(categoryModel.getId())) {
			holder.ivFilter.setVisibility(View.VISIBLE);
		} else {
			holder.ivFilter.setVisibility(View.INVISIBLE);
		}
	}

	public List<String> getSelectedCategoriesIds() {
		return selectedCategoriesIds;
	}


	@Override
	public int getItemCount() {
		return categories.size();
	}

	public void setItemSelectionListener(FilterSelectionListener listener) {
		categoriesListener = listener;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder{

		@Bind(R.id.iv_filter_IAF)
		ImageView ivFilter;
		@Bind(R.id.tv_name_IAF)
		TextView tvName;

		public View view;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			view = itemView;
		}
	}
}
