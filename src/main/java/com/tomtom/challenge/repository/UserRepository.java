package com.tomtom.challenge.repository;

import org.springframework.data.repository.CrudRepository;

import com.tomtom.challenge.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
