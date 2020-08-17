package com.tomtom.challenge.repository;

import org.springframework.data.repository.CrudRepository;

import com.tomtom.challenge.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {

}
