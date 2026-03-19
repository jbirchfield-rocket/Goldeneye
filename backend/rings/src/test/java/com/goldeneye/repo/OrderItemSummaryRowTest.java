package com.goldeneye.repo;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 *
 * @author scanales
 */
@DisplayName("Order Item Summary Row Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderItemSummaryRowTest {

    @Test
    void noArgConstructorCreatesInstance() {
        OrderItemSummaryRow row = new OrderItemSummaryRow();

        assertNotNull(row);
        assertNull(row.getOrderItemId());
        assertNull(row.getProdName());
        assertNull(row.getMattName());
        assertEquals(0, row.getWidth());
        assertNull(row.getStoneName());
        assertNull(row.getUnitPrice());
        assertEquals(0, row.getQty());
    }

    @Test
    void constructorInitializesAllFields() {
        OrderItemSummaryRow row = new OrderItemSummaryRow(1, "Test Product", "Test Material", 2, "Test Stone", BigDecimal.valueOf(1050.00), 3);

        assertEquals(1, row.getOrderItemId());
        assertEquals("Test Product", row.getProdName());
        assertEquals("Test Material", row.getMattName());
        assertEquals(2, row.getWidth());
        assertEquals("Test Stone", row.getStoneName());
        assertEquals(0, BigDecimal.valueOf(1050.00).compareTo(row.getUnitPrice()));
        assertEquals(3, row.getQty());
    }

    @Test
    void setOrderItemIdUpdatesValue() {
        OrderItemSummaryRow row = new OrderItemSummaryRow();
        row.setOrderItemId(5);
        assertEquals(5, row.getOrderItemId());
    }

    @Test
    void setProdNameUpdatesValue() {
        OrderItemSummaryRow row = new OrderItemSummaryRow();
        row.setProdName("Updated Product");
        assertEquals("Updated Product", row.getProdName());
    }

    @Test
    void setMattNameUpdatesValue() {
        OrderItemSummaryRow row = new OrderItemSummaryRow();
        row.setMattName("Updated Material");
        assertEquals("Updated Material", row.getMattName());
    }

    @Test
    void setWidthUpdatesValue() {
        OrderItemSummaryRow row = new OrderItemSummaryRow();
        row.setWidth(4);
        assertEquals(4, row.getWidth());
    }

    @Test
    void setStoneNameUpdatesValue() {
        OrderItemSummaryRow row = new OrderItemSummaryRow();
        row.setStoneName("Updated Stone");
        assertEquals("Updated Stone", row.getStoneName());
    }

    @Test
    void setUnitPriceUpdatesValue() {
        OrderItemSummaryRow row = new OrderItemSummaryRow();
        row.setUnitPrice(BigDecimal.valueOf(500.00));
        assertEquals(0, BigDecimal.valueOf(500.00).compareTo(row.getUnitPrice()));
    }

    @Test
    void setQtyUpdatesValue() {
        OrderItemSummaryRow row = new OrderItemSummaryRow();
        row.setQty(10);
        assertEquals(10, row.getQty());
    }
}
