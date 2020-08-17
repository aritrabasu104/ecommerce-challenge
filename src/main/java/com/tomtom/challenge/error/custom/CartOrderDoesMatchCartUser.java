package com.tomtom.challenge.error.custom;

import com.tomtom.challenge.model.User;

public class CartOrderDoesMatchCartUser extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2095172056083901232L;
	
	public CartOrderDoesMatchCartUser(User user) {
		super("Cart associated to user" + user.getName() +" not matching cart in order. Removing user cart.");
	}

}
