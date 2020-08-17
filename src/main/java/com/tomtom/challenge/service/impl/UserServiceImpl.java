package com.tomtom.challenge.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtom.challenge.error.custom.CartNotEditable;
import com.tomtom.challenge.error.custom.CartOrderDoesMatchCartUser;
import com.tomtom.challenge.error.custom.NotEnoguhQuantityException;
import com.tomtom.challenge.model.Cart;
import com.tomtom.challenge.model.OrderDetails;
import com.tomtom.challenge.model.OrderDetails.STATUS;
import com.tomtom.challenge.model.Product;
import com.tomtom.challenge.model.User;
import com.tomtom.challenge.repository.CartRepository;
import com.tomtom.challenge.repository.OrderDetailsRepository;
import com.tomtom.challenge.repository.ProductRepository;
import com.tomtom.challenge.repository.UserRepository;
import com.tomtom.challenge.service.ExternalTransactionService;
import com.tomtom.challenge.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private ExternalTransactionService transactionService;

	@Override
	public Cart addProducts(Long userId, Product product, Integer quantity) throws CartNotEditable {
		User user = userRepository.findById(userId).get();
		Cart cart = user.getCart();
		if (!Optional.ofNullable(cart).isPresent()) {
			cart = new Cart();
			cart.setUser(user);
			cart.addProduct(product, quantity);
			cart = cartRepository.save(cart);
			user.setCart(cart);
			userRepository.save(user);
		}else {
			cart.addProduct(product, quantity);
			cart = cartRepository.save(cart);
		}
		return cart;
		
	}

	@Override
	public OrderDetails placeOrder(OrderDetails orderDetails) throws CartOrderDoesMatchCartUser, NotEnoguhQuantityException, CartNotEditable {
		
		Cart cartOrder = cartRepository.findById(orderDetails.getCart().getId()).get();
		orderDetails.setCart(cartOrder);
			if (transactionService.getAmount(orderDetails).equals(cartOrder.getCartTotal())) {
				orderDetails.setStatus(STATUS.CONFIRMED);
				productRepository.saveAll(cartOrder.getUpdatedProducts());
				cartOrder.setEditable(false);
				cartRepository.save(cartOrder);
				
				User user = userRepository.findById(orderDetails.getUser().getId()).get();
				
				Cart newCart = new Cart();
				newCart.setUser(user);
				newCart = cartRepository.save(newCart);
				
				user.setCart(newCart);
				userRepository.save(user);
				
				
			}else{
				orderDetails.setStatus(STATUS.CANCELLED);
			}
			orderDetails = orderDetailsRepository.save(orderDetails);
		
		return orderDetails;
	}

	@Override
	public User addUser(User user) {
		Cart cart = new Cart();
		cart = cartRepository.save(cart);
		user.setCart(cart);
		return userRepository.save(user);
	}
	
	
	@Override
	public List<User> getUsers() {
		return StreamSupport.stream(userRepository.findAll().spliterator(),true).collect(Collectors.toList());
	}

	@Override
	public Cart updateCart(Cart cart, Product product) throws CartNotEditable {
		Cart cartActual = cartRepository.findById(cart.getId()).get();
		cartActual.addProduct(product, product.getQuantity());
		return cartRepository.save(cartActual);
	}

}
