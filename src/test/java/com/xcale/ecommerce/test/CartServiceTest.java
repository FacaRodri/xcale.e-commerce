package com.xcale.ecommerce.test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.Product;
import com.xcale.ecommerce.repositories.CartRepository;
import com.xcale.ecommerce.services.impl.CartServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

   @Mock
   private CartRepository cartRepository;

   @InjectMocks
   private CartServiceImpl cartService;


   @Test
   public void testCreateAndGetCartById() {

      Cart cart = new Cart();
      List<Product> products = new ArrayList<>();
      cart.setId(1L);
      cart.setProducts(products);
      cart.setLastActivity(LocalDateTime.now());

      when(cartRepository.save(any(Cart.class))).thenReturn(cart);

      Cart createdCart = cartService.createCart();

      verify(cartRepository, times(1)).save(any(Cart.class));

      assertNotNull(createdCart);
      assertEquals(1L, createdCart.getId().longValue());

      when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

      Cart retrievedCart = cartService.getCartById(1L);

      assertEquals(cart.getId(), retrievedCart.getId());
   }

   @Test
   public void testAddProductsToCart() {

      Cart cart = new Cart();
      Product product = new Product(1L, "Product 1", 1);

      List<Product> products = new ArrayList<>();
      products.add(product);
      cart.setId(1L);
      cart.setProducts(products);
      cart.setLastActivity(LocalDateTime.now());

      when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

      cartService.addProductsToCart(1L, products);

      verify(cartRepository, times(1)).save(any(Cart.class));
   }

   @Test
   public void testDeleteCart() {
      Cart cart = new Cart();
      List<Product> products = new ArrayList<>();
      cart.setId(1L);
      cart.setProducts(products);
      cart.setLastActivity(LocalDateTime.now());

      when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));

      doNothing().when(cartRepository).deleteById(anyLong());

      cartService.deleteCartById(cart.getId());

      verify(cartRepository, times(1)).deleteById(eq(cart.getId()));
   }

   @Test
   public void testCleanupInactiveCarts() {
      Cart cart = new Cart();
      cart.setId(1L);
      cart.setLastActivity(LocalDateTime.now().minusMinutes(11));

      assertTrue(cart.getLastActivity().isBefore(LocalDateTime.now().minusMinutes(10)));

      cartService.cleanupInactiveCarts();

      assertFalse(cartRepository.findById(cart.getId()).isPresent());
   }

}


