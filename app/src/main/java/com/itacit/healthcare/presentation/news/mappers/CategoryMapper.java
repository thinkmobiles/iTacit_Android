package com.itacit.healthcare.presentation.news.mappers;

import com.itacit.healthcare.data.entries.Category;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.news.models.CategoryModel;

/**
 * Created by root on 02.11.15.
 */
public class CategoryMapper extends ModelMapper<CategoryModel, Category> {

	@Override
	public CategoryModel transform(Category dataEntry) {

		CategoryModel categoryModel = new CategoryModel();
		if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
			return null;
		}
		categoryModel.setId(dataEntry.getId());
		categoryModel.setName(dataEntry.getName() != null ? dataEntry.getName() : "");
		return categoryModel;
	}
}
