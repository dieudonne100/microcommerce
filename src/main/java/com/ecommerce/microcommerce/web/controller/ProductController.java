package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.web.dao.ProductDao;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.ecommerce.microcommerce.web.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.invoke.MethodType;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;


    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public MappingJacksonValue productList() {
        List<Product> products = productDao.findAll();
        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider filterList = new SimpleFilterProvider().addFilter("monFiltreDynamique", myFilter);
        MappingJacksonValue productsFilter = new MappingJacksonValue(products);
        productsFilter.setFilters(filterList);
        return productsFilter;
    }

    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping("/product/{id}")
    public Product getProductbyId(@PathVariable int id) {
        Product product= productDao.findById(id);
        if (product==null) throw new ProduitIntrouvableException("Product not found");
        return product;
    }

    @GetMapping(value = "test/product/{prixLimit}")
    public List<Product> requestTest(@PathVariable int prixLimit) {
        return productDao.findByPrixGreaterThan(400);
    }

    @PostMapping(value = "/product")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        Product productAdd = productDao.save(product);
        if (Objects.isNull(productAdd)) {
            return ResponseEntity.notFound().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productAdd.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        productDao.deleteById(id);
    }

    @PutMapping("/product")
    public void updateProduct(@RequestBody Product product) {
        productDao.save(product);
    }
}
