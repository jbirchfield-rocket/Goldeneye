/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.goldeneye.repo;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.goldeneye.model.Customer;


/**
 *
 * @author dshelby
 */
public interface CustomerRepo extends ListCrudRepository<Customer, Integer> {
    @Query("SELECT CUSTID, NAME, ACTIVE FROM GLDEYE.TBCUST")
    List<Customer> findAll();

    @Query("SELECT COUNT(CUSTID) FROM GLDEYE.TBCUST WHERE CUSTID = :id")
    boolean existsById(@Param("id") int id);

    @Query("SELECT INTEGER(IDENTITY_VAL_LOCAL()) FROM SYSIBM.SYSDUMMY1")
    int getLastGeneratedId();

    @Modifying
    @Query("INSERT INTO GLDEYE.TBCUST (NAME) VALUES (:name)")
    void insertCustomer(@Param("name") String name);
  
    @Modifying
    @Query("UPDATE GLDEYE.TBCUST SET NAME = :name WHERE CUSTID = :custId")
    void updateCustomer(int custId, String name);

    @Modifying
    @Query("UPDATE GLDEYE.TBCUST SET ACTIVE = :active WHERE CUSTID = :custId")
    void deleteCustomer(int custId, int active);

}
