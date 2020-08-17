package com.tomtom.challenge.service.impl;

import org.springframework.stereotype.Service;

import com.tomtom.challenge.error.custom.CartNotEditable;
import com.tomtom.challenge.error.custom.NotEnoguhQuantityException;
import com.tomtom.challenge.model.OrderDetails;
import com.tomtom.challenge.service.ExternalTransactionService;

@Service
public class ExternalTransactionServiceImpl implements ExternalTransactionService {


	/**
	 *This method validates the transaction using transaction id and order amount etc.
	 *currently dummied to always return the cart total
	 * @throws CartNotEditable 
	 * @throws NotEnoguhQuantityException 
	 */
	@Override
	public Double getAmount(OrderDetails orderDetails) throws NotEnoguhQuantityException, CartNotEditable {
		return orderDetails.getCart().getCartTotal();
	}

}
