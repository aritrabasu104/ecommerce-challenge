package com.tomtom.challenge.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.tomtom.challenge.error.custom.CartNotEditable;
import com.tomtom.challenge.error.custom.NotEnoguhQuantityException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long Id;

	@ElementCollection
	@EqualsAndHashCode.Include
	private Map<Product, Integer> itemQuantity = new HashMap<>();

	@OneToOne(mappedBy = "cart")
	private User user;

	private Boolean editable = true;

	public void addProduct(Product product, Integer quantity) throws CartNotEditable {
		if (editable) {
			Integer qOld = this.itemQuantity.get(product);
			if (Optional.ofNullable(qOld).isPresent()) {
				itemQuantity.put(product, qOld + quantity);

			} else {
				itemQuantity.put(product, quantity);
			}
		} else {
			throw new CartNotEditable(this);
		}
	}

	public void removeProduct(Product product, Integer quantity) throws CartNotEditable {
		if (editable) {
			Integer qOld = this.itemQuantity.get(product);
			if (Optional.ofNullable(qOld).isPresent()) {
				if (qOld > quantity)
					itemQuantity.put(product, qOld - quantity);
				else
					itemQuantity.remove(product);
			}
		} else {
			throw new CartNotEditable(this);
		}
	}

	public Double getCartTotal() throws NotEnoguhQuantityException, CartNotEditable {
		if (editable) {
			Double total = 0D;
			List<Product> errorList = new ArrayList<>();
			Set<Entry<Product, Integer>> entries = itemQuantity.entrySet();
			for (Entry<Product, Integer> entry : entries) {
				Product product = entry.getKey();
				Integer qOrdered = entry.getValue();

				if (product.getQuantity() >= qOrdered)
					total += (product.getProductDescription().getPrice() * qOrdered);
				else {
					errorList.add(entry.getKey());
					entries.remove(entry);
				}
			}
			if (errorList.size() > 0)
				throw new NotEnoguhQuantityException(errorList);
			return total;
		} else {
			throw new CartNotEditable(this);
		}
	}
	public List<Product> getUpdatedProducts(){
		List<Product> updatedProducts = new ArrayList<>();
		itemQuantity.forEach((prod,quantity) ->{
			prod.setQuantity(prod.getQuantity()-quantity);
			updatedProducts.add(prod);
		});
		return updatedProducts;
	}
}
