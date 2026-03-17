package com.goldeneye.repo;

import java.math.BigDecimal;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.goldeneye.model.OrderItem;

/**
 *
 * @author scanalesR
 */
public interface OrderItemRepo extends ListCrudRepository<OrderItem, Integer> {

    String INSERTQUERY = "insert into GLDEYE.TBORDITM"
                       + "(OrderID, ProdID, MattID, WID, StoneID, UnitPrice, Qty)"
                       + "values (:orderId, :prodId, :mattId, :wid, :stoneId, :unitPrice, :qty)";

    @Modifying
    @Query(INSERTQUERY)
    void insertOrderItem(
        @Param("orderId") int orderId,
        @Param("prodId") int productId,
        @Param("mattId") int materialId,
        @Param("wid") int widthId,
        @Param("stoneId") int stoneId,
        @Param("unitPrice") BigDecimal unitPrice,
        @Param("qty") int quantity
    );
}