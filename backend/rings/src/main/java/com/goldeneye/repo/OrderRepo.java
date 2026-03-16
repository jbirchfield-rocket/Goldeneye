package com.goldeneye.repo;

import java.time.LocalDate;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.goldeneye.model.Order;

/**
 *
 * @author dshelby
 */
public interface OrderRepo extends ListCrudRepository<Order, Integer> {

    @Modifying
    @Query("INSERT INTO GLDEYE.TBORDER (CUSTID, ORDERDATE) VALUES (:custId, :orderDate)")
    void insertOrder(@Param("custId") int custId, @Param("orderDate") LocalDate orderDate);

    @Query("SELECT INTEGER(IDENTITY_VAL_LOCAL()) FROM SYSIBM.SYSDUMMY1")
    int getLastGeneratedId();
}