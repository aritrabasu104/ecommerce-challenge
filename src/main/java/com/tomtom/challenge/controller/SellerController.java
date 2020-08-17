package com.tomtom.challenge.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tomtom.challenge.dto.product.ProductReqDto;
import com.tomtom.challenge.dto.product.ProductResDto;
import com.tomtom.challenge.dto.seller.SellerReqDto;
import com.tomtom.challenge.dto.seller.SellerResDto;
import com.tomtom.challenge.model.Product;
import com.tomtom.challenge.model.Seller;
import com.tomtom.challenge.service.SellerService;

@RestController
@Validated
@RequestMapping("/api")
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/seller")
	public ResponseEntity<List<SellerResDto>> getSellers() {
		return ResponseEntity.status(200).body(sellerService.getSellers().stream()
				.map(item -> modelMapper.map(item, SellerResDto.class)).collect(Collectors.toList()));
	}

	@GetMapping("/seller/product")
	public ResponseEntity<List<ProductResDto>> getProducts(@Valid @RequestParam(required = true) @Min(1) Long sellerId) {
		Seller seller = new Seller();
		seller.setId(sellerId);
		return ResponseEntity.status(200).body(sellerService.getProducts(seller).stream()
				.map(item -> modelMapper.map(item, ProductResDto.class)).collect(Collectors.toList()));
	}
	

	@PostMapping("/seller")
	public ResponseEntity<SellerResDto> addSeller(@Valid @RequestBody SellerReqDto sellerDto) {
		Seller seller = modelMapper.map(sellerDto, Seller.class);
		seller = sellerService.addSeller(seller);
		return ResponseEntity.status(201).body(modelMapper.map(seller, SellerResDto.class));
	}

	@PostMapping("/seller/product")
	public ResponseEntity<ProductResDto> addProduct(@Valid @RequestBody ProductReqDto productDto) {
		Product product = modelMapper.map(productDto, Product.class);
		product = sellerService.addProduct(product);
		return ResponseEntity.status(201).body(modelMapper.map(product, ProductResDto.class));
	}


}
