package com.tomtom.challenge.service;

import java.util.List;

import com.tomtom.challenge.error.custom.CartNotEditable;
import com.tomtom.challenge.error.custom.CartOrderDoesMatchCartUser;
import com.tomtom.challenge.error.custom.NotEnoguhQuantityException;
import com.tomtom.challenge.model.Cart;
import com.tomtom.challenge.model.OrderDetails;
import com.tomtom.challenge.model.Product;
import com.tomtom.challenge.model.User;

public interface UserService {

	User addUser(User user);
	
	List<User> getUsers();
	Cart addProducts(Long userId,Product product, Integer quantity) throws CartNotEditable;
	OrderDetails placeOrder(OrderDetails orderDetails) throws CartOrderDoesMatchCartUser, NotEnoguhQuantityException, CartNotEditable;

	Cart updateCart(Cart cart,Product product) throws CartNotEditable;
}
