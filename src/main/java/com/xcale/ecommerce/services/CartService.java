package com.xcale.ecommerce.services;

import java.util.List;

import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.Product;

public interface CartService {

   Cart createCart();
   Cart getCartById(Long cartId);
   Cart addProductsToCart(Long cartId, List<Product> products);
   Cart deleteCartById(Long cartId);

}
