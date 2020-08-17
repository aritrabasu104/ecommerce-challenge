package com.tomtom.challenge.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tomtom.challenge.dto.CartResDto;
import com.tomtom.challenge.dto.OrderDetailsReqDto;
import com.tomtom.challenge.dto.OrderDetailsResDto;
import com.tomtom.challenge.dto.ProductCartDto;
import com.tomtom.challenge.dto.UserReqDto;
import com.tomtom.challenge.dto.UserResDto;
import com.tomtom.challenge.error.custom.CartNotEditable;
import com.tomtom.challenge.error.custom.CartOrderDoesMatchCartUser;
import com.tomtom.challenge.error.custom.NotEnoguhQuantityException;
import com.tomtom.challenge.model.Cart;
import com.tomtom.challenge.model.OrderDetails;
import com.tomtom.challenge.model.Product;
import com.tomtom.challenge.model.User;
import com.tomtom.challenge.service.UserService;

@RestController
@Validated
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/user")
	public ResponseEntity<List<UserResDto>> getUsers() {
		return ResponseEntity.status(200).body(userService.getUsers().stream()
				.map(item -> modelMapper.map(item, UserResDto.class)).collect(Collectors.toList()));
	}

	@PostMapping("/user")
	public ResponseEntity<UserResDto> addUser(@Valid @RequestBody UserReqDto userReqDto) {
		User user = modelMapper.map(userReqDto, User.class);
		user = userService.addUser(user);
		return ResponseEntity.status(201).body(modelMapper.map(user,UserResDto.class));
	}
	
	@PostMapping("/user/order")
	public ResponseEntity<OrderDetailsResDto> placeOrder(@Valid @RequestBody OrderDetailsReqDto orderDetailsReqDto) throws CartOrderDoesMatchCartUser, NotEnoguhQuantityException, CartNotEditable {
		OrderDetails orderDetails = modelMapper.map(orderDetailsReqDto, OrderDetails.class);
		orderDetails = userService.placeOrder(orderDetails);
		return ResponseEntity.status(201).body(modelMapper.map(orderDetails,OrderDetailsResDto.class));
	}
	
	@PutMapping("/user/cart")
	public ResponseEntity<CartResDto> addToCart(@Valid @RequestBody ProductCartDto productCartDto,
				@RequestParam @Valid Long cartId) throws CartNotEditable {
		Cart cart = new Cart();
		cart.setId(cartId);
		Product product = modelMapper.map(productCartDto, Product.class);
		return ResponseEntity.status(201).body(modelMapper.map(userService.updateCart(cart,product),CartResDto.class));
	}


}
