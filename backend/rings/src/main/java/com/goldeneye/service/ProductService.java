/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldeneye.dto.ProductDTO;
import com.goldeneye.model.Product;
import com.goldeneye.exception.ResourceNotFoundException;
import com.goldeneye.repo.ProductRepo;

/**
 *
 * @author dshelby
 */
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<ProductDTO> getAllProducts() {
        logger.info("Fetching all products");
        List<ProductDTO> products = productRepo.findAll()
            .stream()
            .map(c -> new ProductDTO(c.getProdId(), c.getName(), c.getDescription(), c.getBasePrice()))
            .toList();
        logger.debug("Retrieved {} products", products.size());
        return products;
    }

    public ProductDTO getProductById(int id) {
        logger.info("Fetching product with ID: {}", id);
        Product p = productRepo.findById(id);
        if (p == null) {
            logger.warn("Product not found with ID: {}", id);
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        logger.debug("Found product: {}", p.getName());
        return new ProductDTO(
            p.getProdId(), 
            p.getName(), 
            p.getDescription(), 
            p.getBasePrice()
        );
    }
}
