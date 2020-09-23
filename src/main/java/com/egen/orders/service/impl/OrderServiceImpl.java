package com.egen.orders.service.impl;

import com.egen.orders.domain.Address;
import com.egen.orders.domain.Order;
import com.egen.orders.domain.OrderDetail;
import com.egen.orders.domain.OrderPayment;
import com.egen.orders.enums.OrderStatusEnum;
import com.egen.orders.enums.ResultEnum;
import com.egen.orders.exception.MyException;
import com.egen.orders.repository.AddressRepository;
import com.egen.orders.repository.OrderDetailRepository;
import com.egen.orders.repository.OrderPaymentRepository;
import com.egen.orders.repository.OrderRepository;
import com.egen.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderPaymentRepository orderPaymentRepository;

    @Override
    public Order findOne(Long orderId) {
        Order orderMain = orderRepository.findById(orderId);
        if(orderMain == null) {
            throw new MyException(ResultEnum.ORDER_NOT_FOUND);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder(orderMain);
        List<Address> addressList = addressRepository.findByOrder(orderMain);
        orderMain.setOrderDetails(orderDetailList);
        orderMain.setAddressesList(addressList);
        return orderMain;
    }

    @Override
    public Order finish(Long orderId) {
        Order order = findOne(orderId);
        if(!order.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }
        order.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepository.save(order);
        return orderRepository.findById(orderId);
    }

    @Override
    public Order cancel(Long orderId) {
        Order orderMain = findOne(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findById(orderId);
    }

    @Override
    public Order create(Order order) {

        orderRepository.save(order);
        Order o = orderRepository.findById(order.getId());

        ArrayList<Address> addressArrayList=new ArrayList<> (order.getAddressesList());
        ArrayList<OrderPayment> orderPaymentArrayList=new ArrayList<> (order.getOrderPaymentList());
        ArrayList<OrderDetail> ord= new ArrayList<> (order.getOrderDetails());

        for (Address a : addressArrayList) {
            a.setOrder(order);
            addressRepository.saveAndFlush(a);
        }

        for (OrderPayment op : orderPaymentArrayList){
            op.setOrder(order);
            orderPaymentRepository.saveAndFlush(op);
        }

        for(OrderDetail od : ord){
            od.setOrder(order);
            orderDetailRepository.saveAndFlush(od);
        }

        return o;
    }
}
