package com.xcale.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xcale.ecommerce.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
