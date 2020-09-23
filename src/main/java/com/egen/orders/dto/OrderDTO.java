package com.egen.orders.dto;

import com.egen.orders.domain.Address;
import com.egen.orders.domain.OrderDetail;
import com.egen.orders.domain.OrderPayment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Address billingAddress;
    private Address shippingAddress;
    private Double total;
    private Double tax;
    private Double sub_total;
    private List<OrderPayment> payment_details;
    private List<OrderDetail> orderDetailList;
}
