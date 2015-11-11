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
	try {
		long id = dataEntry.getId() != null ? Long.parseLong(dataEntry.getId()) : 0;
		if (id == 0) return null;
		categoryModel.setId(id);
		categoryModel.setName(dataEntry.getName() != null ? dataEntry.getName() : "");
		return categoryModel;
	} catch (NumberFormatException e) {
		e.printStackTrace();
	}
		return null;
	}
}
