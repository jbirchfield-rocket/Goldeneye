/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.goldeneye.repo;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import com.goldeneye.model.Product;


/**
 *
 * @author dshelby, scanalesR
 */
public interface ProductRepo extends ListCrudRepository<Product, Integer> {
    @Query("SELECT PRODID, NAME, DSCRP, BASEPRICE FROM GLDEYE.TBPROD")
    List<Product> findAll();

    @Query("SELECT PRODID, NAME, DSCRP, BASEPRICE FROM GLDEYE.TBPROD WHERE PRODID = :id")
    Product findById(int id);
}