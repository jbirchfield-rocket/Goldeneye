/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.goldeneye.dto.CustomerDTO;
import com.goldeneye.dto.LocationDTO;
import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.dto.ProductDTO;
import com.goldeneye.dto.StoneDTO;
import com.goldeneye.dto.WidthDTO;
import com.goldeneye.dto.OrderDTO;
import com.goldeneye.service.CustomerService;
import com.goldeneye.service.LocationService;
import com.goldeneye.service.MaterialService;
import com.goldeneye.service.ProductService;
import com.goldeneye.service.StoneService;
import com.goldeneye.service.WidthService;
import com.goldeneye.service.OrderService;

/**
 *
 * @author dshelby
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class GoldeneyeController {

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
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/locations/{custId}")
    public ResponseEntity<List<LocationDTO>> getLocationsByCustId(@PathVariable int custId) {
        return ResponseEntity.ok(locationService.getLocationsByCustId(custId));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProductsByCustId() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/stones")
    public ResponseEntity<List<StoneDTO>> getAllStones() {
        return ResponseEntity.ok(stoneService.getAllStones());
    }

    @GetMapping("/materials")
    public ResponseEntity<List<MaterialDTO>> getAllMaterials() {
        return ResponseEntity.ok(materialService.getAllMaterials());
    }

    @GetMapping("/widths")
    public ResponseEntity<List<WidthDTO>> getAllWidths() {
        return ResponseEntity.ok(widthService.getAllWidths());
    }

    @PostMapping("/order")
    public ResponseEntity<Integer> submitOrder(@RequestBody OrderDTO) {
        int newOrderId = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(newOrderId);
    }

}
