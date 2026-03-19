/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

package com.goldeneye.controller;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.goldeneye.dto.CustomerDTO;
import com.goldeneye.dto.LocationDTO;
import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.dto.OrderDTO;
import com.goldeneye.dto.OrderItemDTO;
import com.goldeneye.dto.OrderSummaryDTO;
import com.goldeneye.dto.ProductDTO;
import com.goldeneye.dto.StoneDTO;
import com.goldeneye.dto.WidthDTO;
import com.goldeneye.service.CustomerService;
import com.goldeneye.service.LocationService;
import com.goldeneye.service.MaterialService;
import com.goldeneye.service.OrderService;
import com.goldeneye.service.ProductService;
import com.goldeneye.service.StoneService;
import com.goldeneye.service.WidthService;

/**
 *
 * @author scanales
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller Tests")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ControllerTests {

    @Mock private CustomerService customerService;
    @Mock private LocationService locationService;
    @Mock private ProductService productService;
    @Mock private StoneService stoneService;
    @Mock private MaterialService materialService;
    @Mock private WidthService widthService;
    @Mock private OrderService orderService;

    @InjectMocks
    private GoldeneyeController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllCustomersReturns200WithCustomerList() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(List.of(
            new CustomerDTO(1, "Golden Halo Jewelers", 1),
            new CustomerDTO(20, "Precious Band Co.", 1)
        ));

        mockMvc.perform(get("/api/customers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].custId").value(1))
            .andExpect(jsonPath("$[0].name").value("Golden Halo Jewelers"))
            .andExpect(jsonPath("$[1].custId").value(20))
            .andExpect(jsonPath("$[1].name").value("Precious Band Co."));
    }

    @Test
    void createCustomerReturns200WithNewCustomerId() throws Exception {
        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(1);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"custId\":0,\"name\":\"Golden Halo Jewelers\",\"active\":1}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(1));

        ArgumentCaptor<CustomerDTO> captor = ArgumentCaptor.forClass(CustomerDTO.class);
        verify(customerService).createCustomer(captor.capture());
        assertEquals("Golden Halo Jewelers", captor.getValue().getName());
    }

    @Test
    void updateCustomerReturns204() throws Exception {
        mockMvc.perform(put("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"custId\":1,\"name\":\"Golden Halo Jewelers Updated\",\"active\":1}"))
            .andExpect(status().isNoContent());

        ArgumentCaptor<CustomerDTO> captor = ArgumentCaptor.forClass(CustomerDTO.class);
        verify(customerService).updateCustomer(eq(1), captor.capture());
        assertEquals("Golden Halo Jewelers Updated", captor.getValue().getName());
    }

    @Test
    void getLocationsByCustIdReturns200WithLocationList() throws Exception {
        when(locationService.getLocationsByCustId(2)).thenReturn(List.of(
            new LocationDTO(1, 2, "123 Main St.", "Richmond", "VA", "23220"),
            new LocationDTO(2, 2, "456 Oak Ave.", "Richmond", "VA", "23221")
        ));

        mockMvc.perform(get("/api/locations/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].locId").value(1))
            .andExpect(jsonPath("$[0].city").value("Richmond"))
            .andExpect(jsonPath("$[1].locId").value(2))
            .andExpect(jsonPath("$[1].city").value("Richmond"));
    }

    @Test
    void deleteLocationReturns204() throws Exception {
        mockMvc.perform(delete("/api/locations/1"))
            .andExpect(status().isNoContent());

        verify(locationService).deleteLocation(1);
    }

    @Test
    void addLocationReturns200WithNewLocationId() throws Exception {
        when(locationService.addLocation(any(LocationDTO.class))).thenReturn(3);

        mockMvc.perform(post("/api/locations/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"locId\":0,\"custId\":2,\"street\":\"789 Pine Rd.\",\"city\":\"Richmond\",\"state\":\"VA\",\"zip\":\"23222\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(3));

        ArgumentCaptor<LocationDTO> captor = ArgumentCaptor.forClass(LocationDTO.class);
        verify(locationService).addLocation(captor.capture());
        assertEquals(2, captor.getValue().getCustId());
        assertEquals("789 Pine Rd.", captor.getValue().getStreet());
    }

    @Test
    void submitOrderReturns200WithNewOrderId() throws Exception {
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(40);

        mockMvc.perform(post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\":null,\"custId\":2,\"locationId\":1,\"billingLocationId\":1,\"date\":\"2026-03-19\",\"orderItems\":[]}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(40));

        ArgumentCaptor<OrderDTO> captor = ArgumentCaptor.forClass(OrderDTO.class);
        verify(orderService).createOrder(captor.capture());
        assertEquals(2, captor.getValue().getCustId());
    }

    @Test
    void getOrdersByCustIdReturns200WithOrderList() throws Exception {
        when(orderService.getOrdersByCustId(2)).thenReturn(List.of(
            new OrderSummaryDTO(40, "Golden Halo Jewelers", LocalDate.of(2026, 3, 19), null, null, List.of()),
            new OrderSummaryDTO(41, "Golden Halo Jewelers", LocalDate.of(2026, 3, 19), null, null, List.of())
        ));

        mockMvc.perform(get("/api/orders/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].orderId").value(40))
            .andExpect(jsonPath("$[0].customerName").value("Golden Halo Jewelers"))
            .andExpect(jsonPath("$[1].orderId").value(41));
    }

    @Test
    void deleteOrderByOrderIdReturns204() throws Exception {
        mockMvc.perform(delete("/api/order/40"))
            .andExpect(status().isNoContent());

        verify(orderService).deleteOrderByOrderId(40);
    }

    @Test
    void updateOrderReturns204() throws Exception {
        mockMvc.perform(put("/api/order/40")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\":40,\"custId\":2,\"locationId\":1,\"billingLocationId\":1,\"date\":\"2026-03-19\",\"orderItems\":[]}"))
            .andExpect(status().isNoContent());

        ArgumentCaptor<OrderDTO> captor = ArgumentCaptor.forClass(OrderDTO.class);
        verify(orderService).updateOrder(eq(40), captor.capture());
        assertEquals(2, captor.getValue().getCustId());
    }

    @Test
    void getAllStonesReturns200WithStoneList() throws Exception {
        when(stoneService.getAllStones()).thenReturn(List.of(
            new StoneDTO(1, "Diamond", 10, java.math.BigDecimal.valueOf(500)),
            new StoneDTO(2, "Sapphire", 5, java.math.BigDecimal.valueOf(200))
        ));

        mockMvc.perform(get("/api/stones"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].stoneId").value(1))
            .andExpect(jsonPath("$[0].name").value("Diamond"))
            .andExpect(jsonPath("$[1].stoneId").value(2))
            .andExpect(jsonPath("$[1].name").value("Sapphire"));
    }

    @Test
    void getAllWidthsReturns200WithWidthList() throws Exception {
        when(widthService.getAllWidths()).thenReturn(List.of(
            new WidthDTO(1, 2, 1.0f, 50),
            new WidthDTO(2, 4, 1.5f, 100)
        ));

        mockMvc.perform(get("/api/widths"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].widthId").value(1))
            .andExpect(jsonPath("$[0].width").value(2))
            .andExpect(jsonPath("$[1].widthId").value(2))
            .andExpect(jsonPath("$[1].width").value(4));
    }

    @Test
    void getAllMaterialsReturns200WithMaterialList() throws Exception {
        when(materialService.getAllMaterials()).thenReturn(List.of(
            new MaterialDTO(1, "Gold", 100, 1.5f),
            new MaterialDTO(2, "Silver", 200, 1.0f)
        ));

        mockMvc.perform(get("/api/materials"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].materialId").value(1))
            .andExpect(jsonPath("$[0].name").value("Gold"))
            .andExpect(jsonPath("$[1].materialId").value(2))
            .andExpect(jsonPath("$[1].name").value("Silver"));
    }

    @Test
    void getAllProductsReturns200WithProductList() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(
            new ProductDTO(1, "Standard Fit Grooved Band", "A classic band with a center groove for a clean look.", java.math.BigDecimal.valueOf(120)),
            new ProductDTO(2, "Comfort Fit Plain Band", "A smooth, rounded interior band for all-day comfort.", java.math.BigDecimal.valueOf(100))
        ));

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].prodId").value(1))
            .andExpect(jsonPath("$[0].name").value("Standard Fit Grooved Band"))
            .andExpect(jsonPath("$[1].prodId").value(2))
            .andExpect(jsonPath("$[1].name").value("Comfort Fit Plain Band"));
    }

    @Test
    void deleteOrderItemReturns204() throws Exception {
        mockMvc.perform(delete("/api/orderitem/10"))
            .andExpect(status().isNoContent());

        verify(orderService).deleteOrderItem(10);
    }

    @Test
    void updateOrderItemReturns204() throws Exception {
        mockMvc.perform(put("/api/orderitem/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderItemId\":10,\"productId\":1,\"materialId\":2,\"widthId\":1,\"stoneId\":1,\"quantity\":5}"))
            .andExpect(status().isNoContent());

        ArgumentCaptor<OrderItemDTO> captor = ArgumentCaptor.forClass(OrderItemDTO.class);
        verify(orderService).updateOrderItem(eq(10), captor.capture());
        assertEquals(1, captor.getValue().getProductId());
        assertEquals(2, captor.getValue().getMaterialId());
        assertEquals(1, captor.getValue().getWidthId());
        assertEquals(1, captor.getValue().getStoneId());
        assertEquals(5, captor.getValue().getQuantity());
    }

}