/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldeneye.dto.ProductDTO;
import com.goldeneye.repo.ProductRepo;

/**
 *
 * @author dshelby
 */
@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepo.findAll()
            .stream()
            .map(c -> new ProductDTO(c.getProdId(), c.getName(), c.getDescription(), c.getBasePrice()))
            .toList();
    }
}
