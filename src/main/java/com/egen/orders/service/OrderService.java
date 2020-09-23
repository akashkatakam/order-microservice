package com.egen.orders.service;

import com.egen.orders.domain.Order;

public interface OrderService {

    Order findOne(Long orderId);
    Order finish(Long orderId);
    Order cancel(Long orderId);
    Order create(Order order);
}
