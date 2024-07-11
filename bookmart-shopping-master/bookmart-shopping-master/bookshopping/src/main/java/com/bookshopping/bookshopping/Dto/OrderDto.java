package com.bookshopping.bookshopping.Dto;

import com.bookshopping.bookshopping.Model.Orders;
import com.bookshopping.bookshopping.Response.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class OrderDto {
    public List<OrderResponse> mapToOrderResponse(List<Orders> orderList) {
        List<OrderResponse> orderResponseList = new ArrayList<>();

        for (Orders order : orderList) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(order.getId());
            orderResponse.setUserId(order.getAppUser().getId());
            orderResponse.setName(order.getAppUser().getName());
            orderResponse.setUsername(order.getAppUser().getUsername());
         orderResponse.setOrderStatus(order.getOrderStatus().getStatus());
            orderResponse.setAddress(order.getAddress());
            orderResponse.setBookList(order.getOrderedBook());
            orderResponseList.add(orderResponse);
        }

        return orderResponseList;
    }
}
