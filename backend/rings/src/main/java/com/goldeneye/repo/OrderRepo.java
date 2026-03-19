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
    @Query("INSERT INTO GLDEYE.TBORDER (CUSTID, LOCID, BILLID) VALUES (:custId, :locationId, :billingLocationId)")
    void insertOrder(@Param("custId") int custId, @Param("locationId") int locationId, @Param("billingLocationId") int billingLocationId);

    @Query("SELECT INTEGER(IDENTITY_VAL_LOCAL()) FROM SYSIBM.SYSDUMMY1")
    int getLastGeneratedId();

     @Query("SELECT O.ORDERID, C.NAME, O.ORDERDATE, O.LOCID AS LOCID, O.BILLID AS BILLID FROM GLDEYE.TBORDER O JOIN GLDEYE.TBCUST C ON C.CUSTID = O.CUSTID WHERE O.CUSTID = :custId")
    List<OrderSummaryRow> findOrderSummariesByCustId(@Param("custId") int custId);

    @Modifying
    @Query("DELETE FROM GLDEYE.TBORDER WHERE ORDERID = :orderId")
    void deleteByOrderId(@Param("orderId") int orderId);

    @Modifying
    @Query("UPDATE GLDEYE.TBORDER SET CUSTID = :custId, LOCID = :locationId, BILLID = :billingLocationId WHERE ORDERID = :orderId")
    void updateOrder(@Param("custId") int custId, @Param("locationId") int locationId, @Param("billingLocationId") int billingLocationId, @Param("orderId") int orderId);

}