package com.tomtom.challenge.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.tomtom.challenge.model.Product;
import com.tomtom.challenge.model.Seller;
import com.tomtom.challenge.repository.ProductRepository;
import com.tomtom.challenge.repository.SellerRepository;
import com.tomtom.challenge.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product addProduct(Product product) {
		product = productRepository.save(product);
		Seller seller = sellerRepository.findById(product.getSeller().getId()).get();
		List<Product> products = seller.getProducts();
		products.add(product);
		sellerRepository.save(seller);
		return product;
	}

	
	@Override
	public Seller addSeller(Seller seller) {
		return sellerRepository.save(seller);
	}

	@Cacheable("seller")
	@Override
	public List<Seller> getSellers() {
		return StreamSupport.stream(sellerRepository.findAll().spliterator(),true).collect(Collectors.toList());
	}

	@Cacheable("seller-products")
	@Override
	public List<Product> getProducts(Seller seller) {
		return productRepository.findBySeller(seller);
	}


}
