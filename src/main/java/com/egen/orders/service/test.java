package com.egen.orders.service;

import com.egen.orders.domain.Order;
import com.egen.orders.service.impl.OrderServiceImpl;

public class test {

    public static void main(String[] args) {
        OrderServiceImpl os = new OrderServiceImpl();
        os.create(new Order());
    }
}
