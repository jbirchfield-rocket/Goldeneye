/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldeneye.dto.CustomerDTO;
import com.goldeneye.dto.LocationDTO;
import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.dto.OrderDTO;
import com.goldeneye.dto.OrderSummaryDTO;
import com.goldeneye.dto.OrderItemDTO;
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
 * @author dshelby
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class GoldeneyeController {

    private static final Logger logger = LoggerFactory.getLogger(GoldeneyeController.class);

    private final CustomerService customerService;
    private final LocationService locationService;
    private final ProductService productService;
    private final StoneService stoneService;
    private final MaterialService materialService;
    private final WidthService widthService;
    private final OrderService orderService;

    public GoldeneyeController(CustomerService customerService, LocationService locationService, ProductService productService, StoneService stoneService, MaterialService materialService, WidthService widthService, OrderService orderService) {
        this.customerService = customerService;
        this.locationService = locationService;
        this.productService = productService;
        this.stoneService = stoneService;
        this.materialService = materialService;
        this.widthService = widthService;
        this.orderService = orderService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        logger.info("GET /api/customers - Fetching all customers");
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping("/customers")
    public ResponseEntity<Integer> createCustomer(@RequestBody CustomerDTO customer) {
        logger.info("POST /api/customers - Creating customer with name: {}", customer.getName());
        int newCustId = customerService.createCustomer(customer);
        logger.debug("Customer created with ID: {}", newCustId);
        return ResponseEntity.ok(newCustId);
    }
  
    @PutMapping("/customers/{custId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable int custId, @RequestBody CustomerDTO customer) {
        logger.info("PUT /api/customers/{} - Updating customer", custId);
        customerService.updateCustomer(custId, customer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customer/{custId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int custId) {
        logger.info("DELETE /api/customer/{} - Deleting customer", custId);
        customerService.deleteCustomer(custId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/locations/{custId}")
    public ResponseEntity<List<LocationDTO>> getLocationsByCustId(@PathVariable int custId) {
        logger.info("GET /api/locations/{} - Fetching locations for customer", custId);
        return ResponseEntity.ok(locationService.getLocationsByCustId(custId));
    }

    @DeleteMapping("/locations/{locId}")
    public ResponseEntity<Void> deleteLocation(@PathVariable int locId) {
        logger.info("DELETE /api/locations/{} - Deleting location", locId);
        locationService.deleteLocation(locId);
        return ResponseEntity.noContent().build();
    }
      
    @PostMapping("/locations/{custId}")
    public ResponseEntity<Integer> addLocation(@PathVariable int custId, @RequestBody LocationDTO location) {
        logger.info("POST /api/locations/{} - Adding location for customer", custId);
        location.setCustId(custId);
        int newLocationId = locationService.addLocation(location);
        logger.debug("Location created with ID: {}", newLocationId);
        return ResponseEntity.ok(newLocationId);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProductsByCustId() {
        logger.info("GET /api/products - Fetching all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/stones")
    public ResponseEntity<List<StoneDTO>> getAllStones() {
        logger.info("GET /api/stones - Fetching all stones");
        return ResponseEntity.ok(stoneService.getAllStones());
    }

    @GetMapping("/materials")
    public ResponseEntity<List<MaterialDTO>> getAllMaterials() {
        logger.info("GET /api/materials - Fetching all materials");
        return ResponseEntity.ok(materialService.getAllMaterials());
    }

    @GetMapping("/widths")
    public ResponseEntity<List<WidthDTO>> getAllWidths() {
        logger.info("GET /api/widths - Fetching all widths");
        return ResponseEntity.ok(widthService.getAllWidths());
    }

    @PostMapping("/order")
    public ResponseEntity<Integer> submitOrder(@RequestBody OrderDTO order) {
        logger.info("POST /api/order - Submitting order for customer ID: {}", order.getCustId());
        int newOrderId = orderService.createOrder(order);
        logger.debug("Order created with ID: {}", newOrderId);
        return ResponseEntity.ok(newOrderId);
    }

    @GetMapping("/orders/{custId}")
    public ResponseEntity<List<OrderSummaryDTO>> getOrdersByCustId(@PathVariable int custId) {
        logger.info("GET /api/orders/{} - Fetching orders for customer", custId);
        return ResponseEntity.ok(orderService.getOrdersByCustId(custId));
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrderByOrderId(@PathVariable int orderId) {
        logger.info("DELETE /api/order/{} - Deleting order", orderId);
        orderService.deleteOrderByOrderId(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<Void> updateOrder(@PathVariable int orderId, @RequestBody OrderDTO order) {
        logger.info("PUT /api/order/{} - Updating order", orderId);
        orderService.updateOrder(orderId, order);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/orderitem/{ordItmId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable int ordItmId) {
        logger.info("DELETE /api/orderitem/{} - Deleting order item", ordItmId);
        orderService.deleteOrderItem(ordItmId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/orderitem/{ordItmId}")
    public ResponseEntity<Void> updateOrderItem(@PathVariable int ordItmId, @RequestBody OrderItemDTO orderItem) {
        logger.info("PUT /api/orderitem/{} - Updating order item", ordItmId);
        orderService.updateOrderItem(ordItmId, orderItem);
        return ResponseEntity.noContent().build();
    }

}
