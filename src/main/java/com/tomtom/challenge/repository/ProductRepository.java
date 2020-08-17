package com.tomtom.challenge.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.tomtom.challenge.model.Product;
import com.tomtom.challenge.model.Seller;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	public List<Product> findBySeller(Seller seller);

}
