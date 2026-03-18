package com.goldeneye.repo;

import java.time.LocalDate;
import java.util.List;

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
    @Query("INSERT INTO GLDEYE.TBORDER (CUSTID, LOCID) VALUES (:custId, :locationId)")
    void insertOrder(@Param("custId") int custId, @Param("locationId") int locationId);

    @Query("SELECT INTEGER(IDENTITY_VAL_LOCAL()) FROM SYSIBM.SYSDUMMY1")
    int getLastGeneratedId();

     @Query("SELECT O.ORDERID, C.NAME, O.ORDERDATE, O.LOCID AS LOCID FROM GLDEYE.TBORDER O JOIN GLDEYE.TBCUST C ON C.CUSTID = O.CUSTID WHERE O.CUSTID = :custId")
    List<OrderSummaryRow> findOrderSummariesByCustId(@Param("custId") int custId);

    @Modifying
    @Query("DELETE FROM GLDEYE.TBORDER WHERE ORDERID = :orderId")
    void deleteByOrderId(@Param("orderId") int orderId);

}