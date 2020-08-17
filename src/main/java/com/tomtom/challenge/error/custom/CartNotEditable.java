package com.tomtom.challenge.error.custom;

import com.tomtom.challenge.model.Cart;

public class CartNotEditable extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2095172056083901232L;
	
	public CartNotEditable(Cart cart) {
		super("Cart " + cart +" not editable : removing");
	}

}
