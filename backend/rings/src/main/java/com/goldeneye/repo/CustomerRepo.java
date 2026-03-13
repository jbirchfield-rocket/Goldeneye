/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.goldeneye.repo;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import com.goldeneye.model.Customer;


/**
 *
 * @author dshelby
 */
public interface CustomerRepo extends ListCrudRepository<Customer, Integer> {
    @Query("SELECT CUSTID, NAME FROM GLDEYE.TBCUST")
    List<Customer> findAll();
}
