package com.egen.orders.controller;

import com.egen.orders.domain.Address;
import com.egen.orders.domain.Order;
import com.egen.orders.dto.OrderDTO;
import com.egen.orders.enums.OrderStatusEnum;
import com.egen.orders.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/order")
    @ApiOperation(value = "Create an order", notes = "create an order with single/multiple payments",response = Order.class)
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO){
        Order order = new Order();
        List<Address> addressList = new ArrayList<>();

        Address billingAddress = orderDTO.getBillingAddress();
        Address shipAddress = orderDTO.getShippingAddress();

        billingAddress.setType("Billing");
        shipAddress.setType("Shipping");
        order.setOrderStatus(OrderStatusEnum.NEW.getCode());
        order.setSub_total(orderDTO.getSub_total());
        order.setTax(orderDTO.getTax());
        order.setTotal(orderDTO.getTotal());
        order.setUserId(21L);
        order.setOrderDetails(orderDTO.getOrderDetailList());
        addressList.add(billingAddress);
        addressList.add(shipAddress);
        order.setAddressesList(addressList);
        order.setOrderPaymentList(orderDTO.getPayment_details());
        return ResponseEntity.ok(orderService.create(order));
    }

    @GetMapping(value = "/order/{id}")
    @ApiOperation(value = "Find order by id", notes = "provide an id to lookup the order in the order registry",response = Order.class)
    public ResponseEntity<Order> createOrder(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.findOne(id));
    }


    @PatchMapping(value = "/order/cancel/{id}")
    @ApiOperation(value = "cancel order by id", notes = "provide an id to cancel the order",response = Order.class)
    public ResponseEntity<Order> cancelOrder(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.cancel(id));
    }

}
