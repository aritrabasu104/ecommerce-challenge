package com.tomtom.challenge.service;

import java.util.List;

import com.tomtom.challenge.model.Product;
import com.tomtom.challenge.model.Seller;

public interface SellerService {
	
	 Seller addSeller(Seller seller);
	 Product addProduct(Product product);
	 
	 List<Seller> getSellers();
	 List<Product> getProducts(Seller seller);
	
}
