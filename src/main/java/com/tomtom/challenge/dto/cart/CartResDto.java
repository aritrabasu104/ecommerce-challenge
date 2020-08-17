package com.tomtom.challenge.dto.cart;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tomtom.challenge.dto.product.ProductResDto;
import com.tomtom.challenge.dto.user.UserResDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResDto {

	@NotNull
	private Long Id;

	private Map<ProductResDto, Integer> itemQuantity = new HashMap<>();

	@JsonIgnore
	private UserResDto user;
}
