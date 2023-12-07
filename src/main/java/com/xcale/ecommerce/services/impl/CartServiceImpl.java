package com.xcale.ecommerce.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.Product;
import com.xcale.ecommerce.repositories.CartRepository;
import com.xcale.ecommerce.services.CartService;

@Service
public class CartServiceImpl implements CartService {

   private final CartRepository cartRepository;

   public CartServiceImpl(CartRepository cartRepository) {
      this.cartRepository = cartRepository;
   }

   @Override
   public Cart createCart() {
      Cart cart = new Cart();
      return cartRepository.save(cart);
   }

   @Override
   public Cart getCartById(Long cartId) {
      return cartRepository.findById(cartId).orElse(null);
   }

   @Override
   public Cart addProductsToCart(Long cartId, List<Product> products) {
      return cartRepository.findById(cartId)
                           .map(cart -> {
                              for (Product newProduct : products) {
                                 boolean productExists = cart.getProducts().stream()
                                                             .anyMatch(p -> p.getId().equals(newProduct.getId()));

                                 if (productExists) {
                                    cart.getProducts().stream()
                                        .filter(p -> p.getId().equals(newProduct.getId()))
                                        .findFirst()
                                        .ifPresent(existingProduct -> {
                                           int currentAmount = existingProduct.getAmount();
                                           existingProduct.setAmount(currentAmount + newProduct.getAmount());
                                        });
                                 } else {
                                    cart.getProducts().add(newProduct);
                                 }
                              }
                              cart.updateLastActivity();
                              return cartRepository.save(cart);
                           })
                           .orElse(null);
   }

   @Override
   public Cart deleteCartById(Long cartId) {
      return cartRepository.findById(cartId)
                           .map(cart -> {
                              cart.getProducts().clear();
                              cartRepository.deleteById(cartId);
                              return cart;
                           })
                           .orElse(null);
   }

   @Scheduled(fixedRate = 60000)
   public void cleanupInactiveCarts() {
      LocalDateTime thresholdTime = LocalDateTime.now().minusMinutes(10);
      List<Cart> inactiveCarts = cartRepository.findInactiveCarts(thresholdTime);
      cartRepository.deleteAll(inactiveCarts);
   }
}
