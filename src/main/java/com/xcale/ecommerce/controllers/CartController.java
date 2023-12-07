package com.xcale.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.Product;
import com.xcale.ecommerce.services.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

   private final CartService cartService;

   @Autowired
   public CartController(CartService cartService) {
      this.cartService = cartService;
   }

   @PostMapping
   public ResponseEntity<String> createCart() {
      Long cartId = cartService.createCart().getId();
      return ResponseEntity.ok("Cart created with ID: " + cartId);
   }

   @PostMapping("/{cartId}/addProducts")
   public ResponseEntity<?> addProductsToCart(@PathVariable("cartId") Long cartId, @RequestBody List<Product> products) {
      Cart cart = cartService.addProductsToCart(cartId, products);
      return (cart == null) ?
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found") :
            ResponseEntity.ok(cart);
   }

   @GetMapping("/{cartId}")
   public ResponseEntity<?> getCartById(@PathVariable Long cartId) {
      Cart cart = cartService.getCartById(cartId);
      return (cart == null) ?
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found") :
            ResponseEntity.ok(cart);
   }

   @DeleteMapping("/{cartId}")
   public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {
      Cart cart = cartService.deleteCartById(cartId);
      return (cart == null) ?
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found") :
            ResponseEntity.ok(cart);
   }

}
