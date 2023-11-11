package com.EAD.EAD_monolithic.service;


import com.EAD.EAD_monolithic.Exception.*;
import com.EAD.EAD_monolithic.dto.*;
import com.EAD.EAD_monolithic.entity.*;
import com.EAD.EAD_monolithic.repo.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DeliveryRepo deliveryRepo;

    @Autowired
    private ModelMapper modelMapper;

    public Order saveOrder(OrderRequest orderRequest) {
        User user = userRepo.findById(orderRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Check if there are enough items in the Inventory for each OrderItem
        if (!hasEnoughItems(orderRequest.getOrderItems())) {
            throw new InsufficientItemQuantityException("Not enough items in inventory for the order");
        }

        Order order = new Order();
        order.setUser(user);

        order.setIsPrepared(false);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            Inventory inventory = inventoryRepo.findById(orderItemRequest.getItemId())
                    .orElseThrow(() -> new NotFoundException("Inventory item not found"));

/*
            // Check if there are enough items in the Inventory for the OrderItem
            if (inventory.getQuantity() < orderItemRequest.getQuantity()) {
                throw new RuntimeException("Not enough items in inventory for the order item");
            }
*/
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setInventory(inventory);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setUnitPrice(inventory.getUnitPrice());
            orderItem.setTotalUnitPrice(orderItem.getUnitPrice() * orderItem.getQuantity());

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        double totalPrice = calculateTotalPrice(orderItems);
        order.setTotalPrice(totalPrice);

        // Update the inventory quantities
        updateInventoryQuantities(orderItems);

        return orderRepo.save(order);
    }

    private boolean hasEnoughItems(List<OrderItemRequest> orderItemRequests) {
        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            Inventory inventory = inventoryRepo.findById(orderItemRequest.getItemId())
                    .orElseThrow(() -> new NotFoundException("Inventory item not found"));
            if (inventory.getQuantity() < orderItemRequest.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    private void updateInventoryQuantities(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            Inventory inventory = orderItem.getInventory();
            int newQuantity = inventory.getQuantity() - orderItem.getQuantity();
            inventory.setQuantity(newQuantity);
            inventoryRepo.save(inventory);
        }
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>() {
        }.getType());
    }

    public Order getOrderById(int id) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException("Order not found with id " + id);
        }
        return order;
    }

    public Order updateOrder(OrderUpdateRequest orderUpdateRequest, int orderId) {
        Order existingOrder = orderRepo.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        existingOrder.setIsPrepared(orderUpdateRequest.isPrepared());

        // Update order items
        List<OrderItem> updatedOrderItems = updateOrderItems(orderUpdateRequest.getOrderItems(), existingOrder);
        existingOrder.setOrderItems(updatedOrderItems);

        // Recalculate total price
        double totalPrice = calculateTotalPrice(updatedOrderItems);
        existingOrder.setTotalPrice(totalPrice);

        return orderRepo.save(existingOrder);
    }

    private List<OrderItem> updateOrderItems(List<OrderItemRequest> orderItemRequests, Order order) {
        List<OrderItem> updatedOrderItems = new ArrayList<>();

        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            // Find the existing order item by item ID
            OrderItem existingOrderItem = order.getOrderItems().stream()
                    .filter(item -> item.getInventory().getItemId() == orderItemRequest.getItemId())
                    .findAny()
                    .orElseThrow(() -> new NotFoundException("Order item not found"));

            // Update the quantity
            existingOrderItem.setQuantity(orderItemRequest.getQuantity());

            // Recalculate total unit price
            double totalUnitPrice = existingOrderItem.getUnitPrice() * existingOrderItem.getQuantity();
            existingOrderItem.setTotalUnitPrice(totalUnitPrice);

            updatedOrderItems.add(existingOrderItem);
        }

        return updatedOrderItems;
    }

    private double calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToDouble(OrderItem::getTotalUnitPrice)
                .sum();
    }

    public String deleteOrder(int id) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order == null) {
            throw new NotFoundException("Order not found with id " + id);
        }

        // Update inventory for each order item
        for (OrderItem orderItem : order.getOrderItems()) {
            Inventory inventory = orderItem.getInventory();
            int updatedQuantity = inventory.getQuantity() + orderItem.getQuantity();
            inventory.setQuantity(updatedQuantity);

            inventoryRepo.save(inventory);
        }

        // Delete the order
        orderRepo.deleteById(id);

        return "Order deleted with id " + id;
    }

    public void updateOrderPreparedStatus(int orderId, boolean isPrepared) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        order.setIsPrepared(isPrepared);
        orderRepo.save(order);
    }
}



