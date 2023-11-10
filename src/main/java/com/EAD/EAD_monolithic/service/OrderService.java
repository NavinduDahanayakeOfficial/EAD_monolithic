package com.EAD.EAD_monolithic.service;


import com.EAD.EAD_monolithic.Exception.OrderNotFoundException;
import com.EAD.EAD_monolithic.dto.OrderDTO;
import com.EAD.EAD_monolithic.dto.OrderItemRequest;
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
import java.util.Optional;
import java.util.stream.Collectors;

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


    public Order getOrderById(int id) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with id " + id);
        }
        return order;
    }

    public Order updateOrder(OrderUpdateRequest orderUpdateRequest, int id) {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));

        // Update order-level properties
        existingOrder.setUserId(orderUpdateRequest.getUserId());
        existingOrder.setIsPrepared(orderUpdateRequest.isPrepared());

        // Handle order items
        List<OrderItem> updatedOrderItems = new ArrayList<>();

        for (OrderItem orderItemRequest : orderUpdateRequest.getOrderItems()) {
            // Check if the item already exists in the order
            Optional<OrderItem> existingOrderItemOptional = existingOrder.getOrderItems().stream()
                    .filter(orderItem -> orderItem.getItemId() == orderItemRequest.getItemId())
                    .findFirst();

            if (existingOrderItemOptional.isPresent()) {
                // If the item exists, update its properties
                OrderItem existingOrderItem = existingOrderItemOptional.get();
                existingOrderItem.setQuantity(orderItemRequest.getQuantity());
                existingOrderItem.setUnitPrice(orderItemRequest.getUnitPrice());
                existingOrderItem.setTotalUnitPrice(orderItemRequest.getQuantity() * orderItemRequest.getUnitPrice());

                // Add the updated item to the list
                updatedOrderItems.add(existingOrderItem);
            } else {
                // If the item does not exist, create a new one
                OrderItem newOrderItem = new OrderItem();
                newOrderItem.setOrder(existingOrder);
                newOrderItem.setItemId(orderItemRequest.getItemId());
                newOrderItem.setQuantity(orderItemRequest.getQuantity());
                newOrderItem.setUnitPrice(orderItemRequest.getUnitPrice());
                newOrderItem.setTotalUnitPrice(orderItemRequest.getQuantity() * orderItemRequest.getUnitPrice());

                // Add the new item to the list
                updatedOrderItems.add(newOrderItem);
            }
        }

        // Manually remove orphaned items
        List<OrderItem> itemsToRemove = existingOrder.getOrderItems().stream()
                .filter(existingItem ->
                        updatedOrderItems.stream()
                                .noneMatch(updatedItem -> updatedItem.getOrderItemId() == existingItem.getOrderItemId()))
                .collect(Collectors.toList());

        existingOrder.getOrderItems().removeAll(itemsToRemove);

// Clear existing items before adding updated items
        existingOrder.getOrderItems().clear();

// Set the updated order items
        existingOrder.getOrderItems().addAll(updatedOrderItems);

        // Save and return the updated order
        return orderRepo.save(existingOrder);
    }



    public String deleteOrder(int id){
        Order order = orderRepo.findById(id).orElse(null);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with id " + id);
        }
        orderRepo.deleteById(id);
        return "order deleted with id " + id;
    }
}

