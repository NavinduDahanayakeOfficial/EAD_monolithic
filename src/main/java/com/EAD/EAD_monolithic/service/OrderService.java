package com.EAD.EAD_monolithic.service;


import com.EAD.EAD_monolithic.dto.OrderDTO;
import com.EAD.EAD_monolithic.dto.OrderItemRequest;
import com.EAD.EAD_monolithic.dto.OrderRequest;
import com.EAD.EAD_monolithic.entity.Order;
import com.EAD.EAD_monolithic.entity.OrderItem;
import com.EAD.EAD_monolithic.repo.OrderItemRepo;
import com.EAD.EAD_monolithic.repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private ModelMapper modelMapper;

    public Order saveOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setIsPrepared(false);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItem orderItemRequest : orderRequest.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(orderItemRequest.getItemId());
            orderItem.setOrderId(order.getOrderId());
            orderItem.setId(orderItemRequest.getId()); // Change productId to itemId
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setUnitPrice(orderItemRequest.getUnitPrice());
            orderItem.setTotalUnitPrice(orderItemRequest.getUnitPrice() * orderItemRequest.getQuantity());

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        return orderRepo.save(order);
    }



    public List<OrderDTO> getAllOrders() {
        List<Order> orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>() {
        }.getType());
    }
}

