package com.EAD.EAD_monolithic.service;


import com.EAD.EAD_monolithic.Exception.OrderNotFoundException;
import com.EAD.EAD_monolithic.dto.OrderDTO;
import com.EAD.EAD_monolithic.dto.OrderRequest;
import com.EAD.EAD_monolithic.dto.OrderUpdateRequest;
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

    public Order saveOrder(OrderRequest orderRequest){
        Order order =new Order();
        order.setUserId(orderRequest.getUserId());
        order.setIsPrepared(false);

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItem orderItemRequest : orderRequest.getOrderItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItemId(orderItemRequest.getItemId());
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setUnitPrice(orderItemRequest.getUnitPrice());
            orderItem.setTotalUnitPrice(orderItemRequest.getQuantity()* orderItemRequest.getUnitPrice());

            orderItems.add(orderItem);
        }

        double totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalUnitPrice();
        }
        order.setTotalPrice(totalPrice);

        order.setOrderItems(orderItems);

        return orderRepo.save(order);
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>() {
        }.getType());
    }


    public Order getOrderById(OrderUpdateRequest orderUpdateRequest, int id) {
        Order exisitingOrder = orderRepo.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));

        exisitingOrder.setUserId(orderUpdateRequest.getUserId());
        exisitingOrder.setIsPrepared(orderUpdateRequest.isPrepared());

        List<OrderItem> updatedOrderItems = new ArrayList<>();
        for(OrderItem orderItemRequest : orderUpdateRequest.getOrderItems()){
            OrderItem existingOrderItem = exisitingOrder.getOrderItems().stream()
                    .filter(orderItem -> orderItem.getItemId() == orderItemRequest.getItemId())
                    .findFirst()
                    .orElseThrow(() -> new OrderNotFoundException("Order item not found with id " + orderItemRequest.getItemId()));

            updatedOrderItems.add(existingOrderItem);
        }

        exisitingOrder.setOrderItems(updatedOrderItems);

        double totalPrice = updatedOrderItems.stream()
                .mapToDouble(orderItem -> orderItem.getTotalUnitPrice())
                .sum();

        return orderRepo.save(exisitingOrder);
    }




    public String deleteOrder(int id){
        Order order = orderRepo.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
        orderRepo.deleteById(id);
        return "order deleted with id " + id;
    }
}

