package com.xcale.ecommerce.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Cart {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(
         name = "cart_product",
         joinColumns = @JoinColumn(name = "cart_id"),
         inverseJoinColumns = @JoinColumn(name = "product_id"))
   private List<Product> products;

   private LocalDateTime lastActivity;

   public void updateLastActivity() {
      this.lastActivity = LocalDateTime.now();
   }

}
