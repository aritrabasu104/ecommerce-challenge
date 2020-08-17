package com.tomtom.challenge.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.tomtom.challenge.model.Category;
import com.tomtom.challenge.repository.CategoryRepository;
import com.tomtom.challenge.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Cacheable("category")
	@Override
	public List<Category> getCategories() {
		return StreamSupport.stream(categoryRepository.findAll().spliterator(), true).collect(Collectors.toList());
	}

}
