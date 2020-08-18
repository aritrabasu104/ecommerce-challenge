package com.tomtom.challenge.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tomtom.challenge.dto.page.PageDto;
import com.tomtom.challenge.dto.product.ProductSearchDto;
import com.tomtom.challenge.error.custom.CartNotEditable;
import com.tomtom.challenge.error.custom.CartOrderDoesMatchCartUser;
import com.tomtom.challenge.error.custom.NotEnoguhQuantityException;
import com.tomtom.challenge.model.Cart;
import com.tomtom.challenge.model.Category;
import com.tomtom.challenge.model.OrderDetails;
import com.tomtom.challenge.model.OrderDetails.STATUS;
import com.tomtom.challenge.model.Product;
import com.tomtom.challenge.model.User;
import com.tomtom.challenge.repository.CartRepository;
import com.tomtom.challenge.repository.CategoryRepository;
import com.tomtom.challenge.repository.OrderDetailsRepository;
import com.tomtom.challenge.repository.ProductRepository;
import com.tomtom.challenge.repository.UserRepository;
import com.tomtom.challenge.service.ExternalTransactionService;
import com.tomtom.challenge.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final int DEF_PAGE_NUM = 0;

	private static final int DEF_PAGE_SIZE = 10;

	@Autowired
	EntityManager em;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private ExternalTransactionService transactionService;

	@Override
	public Cart addProducts(Long userId, Product product, Integer quantity) throws CartNotEditable {
		User user = userRepository.findById(userId).get();
		Cart cart = user.getCart();
		if (!Optional.ofNullable(cart).isPresent()) {
			cart = new Cart();
			cart.setUser(user);
			cart.addProduct(product, quantity);
			cart = cartRepository.save(cart);
			user.setCart(cart);
			userRepository.save(user);
		} else {
			cart.addProduct(product, quantity);
			cart = cartRepository.save(cart);
		}
		return cart;

	}

	@Override
	public OrderDetails placeOrder(OrderDetails orderDetails)
			throws CartOrderDoesMatchCartUser, NotEnoguhQuantityException, CartNotEditable {

		Cart cartOrder = cartRepository.findById(orderDetails.getCart().getId()).get();
		orderDetails.setCart(cartOrder);
		if (transactionService.getAmount(orderDetails).equals(cartOrder.getCartTotal())) {
			orderDetails.setStatus(STATUS.CONFIRMED);
			productRepository.saveAll(cartOrder.getUpdatedProducts());
			cartOrder.setEditable(false);
			cartRepository.save(cartOrder);

			User user = userRepository.findById(orderDetails.getUser().getId()).get();

			Cart newCart = new Cart();
			newCart.setUser(user);
			newCart = cartRepository.save(newCart);

			user.setCart(newCart);
			userRepository.save(user);

		} else {
			orderDetails.setStatus(STATUS.CANCELLED);
		}
		orderDetails = orderDetailsRepository.save(orderDetails);

		return orderDetails;
	}

	@Override
	public User addUser(User user) {
		Cart cart = new Cart();
		cart = cartRepository.save(cart);
		user.setCart(cart);
		return userRepository.save(user);
	}

	@Cacheable("users")
	@Override
	public List<User> getUsers() {
		return StreamSupport.stream(userRepository.findAll().spliterator(), true).collect(Collectors.toList());
	}

	@Override
	public Cart updateCart(Cart cart, Product product) throws CartNotEditable {
		Cart cartActual = cartRepository.findById(cart.getId()).get();
		cartActual.addProduct(product, product.getQuantity());
		return cartRepository.save(cartActual);
	}

	@Cacheable("search-product")
	@Override
	public Page<Product> searchProducts(ProductSearchDto productSearchDto) {
		PageDto page = productSearchDto.getPageDto();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<Product> product = cq.from(Product.class);
		if (Optional.ofNullable(productSearchDto.getCategory()).isPresent()) {
			Optional<Category> categoryOp = categoryRepository.findById(productSearchDto.getCategory().getId());
			if (categoryOp.isPresent()) {
				Predicate categoryPredicate = cb.equal(product.get("category"), categoryOp.get());
				predicates.add(categoryPredicate);
			}
		}
		if (productSearchDto.getMaxPrice() < Double.MAX_VALUE || productSearchDto.getMinPrice() > 0) {
			Predicate pricePredicate = cb.between(product.get("price"), productSearchDto.getMinPrice(),
					productSearchDto.getMaxPrice());

			predicates.add(pricePredicate);
		}
		if (productSearchDto.getMaxRating() < Integer.MAX_VALUE || productSearchDto.getMinRating() > 0) {
			Predicate ratingPredicate = cb.between(product.get("rating"), productSearchDto.getMinRating(),
					productSearchDto.getMaxRating());

			predicates.add(ratingPredicate);
		}

		Predicate titlePredicate = cb.like(cb.upper(product.get("title")),
				"%" + productSearchDto.getTitle().toUpperCase() + "%");
		predicates.add(titlePredicate);

		Predicate descriptionPredicate = cb.like(cb.upper(product.get("description")),
				"%" + productSearchDto.getDescription().toUpperCase() + "%");
		predicates.add(descriptionPredicate);

		cq.where(predicates.toArray(new Predicate[] {}));
		Order order;
		if (!Optional.ofNullable(productSearchDto.getSortOrder()).isPresent())
			productSearchDto.setSortOrder(ProductSearchDto.SortOrder.ASC);
		if (!Optional.ofNullable(productSearchDto.getSortColumn()).isPresent())
			productSearchDto.setSortColumn(ProductSearchDto.SortColumn.price);
		if (!Optional.ofNullable(page).isPresent() || (!Optional.ofNullable(page.getPageNumber()).isPresent())
				|| (!Optional.ofNullable(page.getPageSize()).isPresent()) || page.getPageSize() < 1) {
			page = new PageDto();
			page.setPageNumber(DEF_PAGE_NUM);
			page.setPageSize(DEF_PAGE_SIZE);
			productSearchDto.setPageDto(page);
		}
// 		productSearchDto.setSortColumn(ProductSearchDto.SortColumn.price);

		if (productSearchDto.getSortOrder() == ProductSearchDto.SortOrder.ASC) {
			order = cb.asc(product.get(productSearchDto.getSortColumn().toString()));
		} else {
			order = cb.desc(product.get(productSearchDto.getSortColumn().toString()));
		}
		cq.orderBy(order);
		TypedQuery<Product> query = em.createQuery(cq);

		int totalRows = query.getResultList().size();

		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		Page<Product> pagedRresult = new PageImpl<Product>(query.getResultList(), PageRequest.of(
				productSearchDto.getPageDto().getPageNumber(), productSearchDto.getPageDto().getPageSize()), totalRows);
		return pagedRresult;

	}

}
