package com.tomtom.challenge.service;

import java.util.List;

import com.tomtom.challenge.model.Category;

public interface AdminService {

	 Category addCategory(Category category);
	 List<Category> getCategories();
}
