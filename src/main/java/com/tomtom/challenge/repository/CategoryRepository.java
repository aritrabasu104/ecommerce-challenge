package com.tomtom.challenge.repository;

import org.springframework.data.repository.CrudRepository;

import com.tomtom.challenge.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
