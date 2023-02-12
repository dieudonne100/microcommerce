package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.web.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {
    List<Product> findAll();
    Product findById(int id);
    Product save(Product product);

    List<Product> findByPrixGreaterThan(int prixLimte);
}