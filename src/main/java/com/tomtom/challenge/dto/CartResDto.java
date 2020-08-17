package com.tomtom.challenge.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
