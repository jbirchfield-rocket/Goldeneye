/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.goldeneye.repo;

import org.springframework.data.repository.ListCrudRepository;

import com.goldeneye.model.Customer;


/**
 *
 * @author scanales
 */
public interface CustomerRepo extends ListCrudRepository<Customer, Integer> {
}
