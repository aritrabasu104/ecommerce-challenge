package com.tomtom.challenge.error.custom;

import java.util.List;

import com.tomtom.challenge.model.Product;

public class NotEnoguhQuantityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2852278733445186747L;

	public NotEnoguhQuantityException(List<Product> products) {
		super("Not enough quantity avaiable for the following items :"+products.toString());
	}
}
