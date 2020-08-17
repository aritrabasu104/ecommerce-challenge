package com.tomtom.challenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tomtom.challenge.model.Seller;

@Repository
public interface SellerRepository extends CrudRepository<Seller, Long> {

}
