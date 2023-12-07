package com.xcale.ecommerce.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xcale.ecommerce.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

   Optional<Cart> findById(Long cartId);

   @Query("SELECT c FROM Cart c WHERE c.lastActivity <= :thresholdTime")
   List<Cart> findInactiveCarts(@Param("thresholdTime") LocalDateTime thresholdTime);

}
