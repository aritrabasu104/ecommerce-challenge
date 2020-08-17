package com.tomtom.challenge.repository;

import org.springframework.data.repository.CrudRepository;

import com.tomtom.challenge.model.OrderDetails;

public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Long> {

}
