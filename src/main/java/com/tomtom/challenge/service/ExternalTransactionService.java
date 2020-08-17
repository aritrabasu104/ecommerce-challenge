package com.tomtom.challenge.service;

import com.tomtom.challenge.error.custom.CartNotEditable;
import com.tomtom.challenge.error.custom.NotEnoguhQuantityException;
import com.tomtom.challenge.model.OrderDetails;

public interface ExternalTransactionService {

	 Double getAmount(OrderDetails orderDetails) throws NotEnoguhQuantityException, CartNotEditable;
}
