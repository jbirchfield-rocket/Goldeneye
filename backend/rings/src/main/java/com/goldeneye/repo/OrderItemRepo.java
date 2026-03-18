package com.goldeneye.repo;

import java.math.BigDecimal;
import java.util.List;

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

    @Query("SELECT OI.ORDITMID, OI.ORDERID, P.NAME AS PRODNAME, M.NAME AS MATTNAME, W.WIDTH, S.NAME AS STONENAME, OI.UNITPRICE, OI.QTY FROM GLDEYE.TBORDITM OI JOIN GLDEYE.TBPROD P ON P.PRODID = OI.PRODID JOIN GLDEYE.TBMATT M ON M.MATTID = OI.MATTID JOIN GLDEYE.TBWIDTH W ON W.WID = OI.WID JOIN GLDEYE.TBSTONE S ON S.STONEID = OI.STONEID WHERE OI.ORDERID = :orderId")
    List<OrderItemSummaryRow> findOrderItemSummariesByOrderId(@Param("orderId") int orderId);
}