package com.EAD.EAD_monolithic.service;


import com.EAD.EAD_monolithic.Exception.InsufficientItemQuantityException;
import com.EAD.EAD_monolithic.Exception.InventoryItemNotFoundException;
import com.EAD.EAD_monolithic.Exception.UserNotFoundException;
import com.EAD.EAD_monolithic.dto.*;
import com.EAD.EAD_monolithic.entity.*;
import com.EAD.EAD_monolithic.repo.DeliveryRepo;
import com.EAD.EAD_monolithic.repo.OrderRepo;
import com.EAD.EAD_monolithic.repo.InventoryRepo;
import com.EAD.EAD_monolithic.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
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
    private InventoryRepo inventoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DeliveryRepo deliveryRepo;

    @Autowired
    private ModelMapper modelMapper;

    public void saveOrder(OrderRequest orderRequest) {
        User user = userRepo.findById(orderRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if there are enough items in the Inventory for each OrderItem
        if (!hasEnoughItems(orderRequest.getOrderItems())) {
            throw new InsufficientItemQuantityException("Not enough items in inventory for the order");
        }

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(calculateTotalPrice(orderRequest.getOrderItems()));
        order.setIsPrepared(false);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            Inventory inventory = inventoryRepo.findById(orderItemRequest.getItemId())
                    .orElseThrow(() -> new InventoryItemNotFoundException("Inventory item not found"));

/*
            // Check if there are enough items in the Inventory for the OrderItem
            if (inventory.getQuantity() < orderItemRequest.getQuantity()) {
                throw new RuntimeException("Not enough items in inventory for the order item");
            }
*/

            OrderItem orderItem = new OrderItem();
            orderItem.setInventory(inventory);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setUnitPrice(inventory.getUnitPrice());
            orderItem.setTotalUnitPrice(orderItem.getUnitPrice() * orderItem.getQuantity());

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        // Update the inventory quantities
        updateInventoryQuantities(orderItems);

        orderRepo.save(order);
    }

    private boolean hasEnoughItems(List<OrderItemRequest> orderItemRequests) {
        for (OrderItemRequest orderItemRequest : orderItemRequests) {
            Inventory inventory = inventoryRepo.findById(orderItemRequest.getItemId())
                    .orElseThrow(() -> new InventoryItemNotFoundException("Inventory item not found"));
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
            inventoryRepository.save(inventory);
        }
    }


    /*public boolean checkProductQuantity(int itemId, int quantity) {
        Inventory inventory = inventoryRepo.getProductByProductID(itemId);
        if(inventory == null){
            throw new ProductNotFoundException("Product not found with id " + itemId);
        }
        return inventory.getQuantity() >= quantity;
    }

    public double getProductUnitPrice(int itemId) {
        Inventory inventory = inventoryRepo.getProductByProductID(itemId);
        if(inventory == null){
            throw new ProductNotFoundException("Product not found with id " + itemId);
        }
        return inventory.getUnitPrice();
    }

    public Order saveOrder(OrderRequest orderRequest) {

        // Check if all order items have enough quantity in stock
        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            if (!checkProductQuantity(orderItemRequest.getItemId(), orderItemRequest.getQuantity())) {
                throw new InsufficientProductQuantityException("Insufficient quantity for product with id " + orderItemRequest.getItemId() + " in stock");
            }
        }

        //Reduce the quantity of each product in the database
        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            Inventory inventory = inventoryRepo.getProductByProductID(orderItemRequest.getItemId());
            inventory.setQuantity(inventory.getQuantity() - orderItemRequest.getQuantity());
            inventoryRepo.save(inventory);
        }

        Order newOrder = new Order();
        newOrder.setUserId(orderRequest.getUserId());
        newOrder.setIsPrepared(false);

        // Create a list to store the OrderItem objects
        List<OrderItem> newOrderItems = new ArrayList<>();

       //Iterate over the OrderItemRequest objects and create a new OrderItem object for each one
        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setOrder(newOrder);
            newOrderItem.setItemId(orderItemRequest.getItemId());
            newOrderItem.setQuantity(orderItemRequest.getQuantity());

            // Set the unit price on the OrderItem object
            newOrderItem.setUnitPrice(getProductUnitPrice(orderItemRequest.getItemId()));

            // Calculate the total unit price for the OrderItem object
            newOrderItem.setTotalUnitPrice(newOrderItem.getQuantity() * newOrderItem.getUnitPrice());

            // Add the OrderItem object to the list
            newOrderItems.add(newOrderItem);
        }
        // Set the OrderItem objects on the Order object
        newOrder.setOrderItems(newOrderItems);

        double totalPrice = 0;
        for (OrderItem orderItem : newOrder.getOrderItems()) {
            totalPrice += orderItem.getTotalUnitPrice();
        }

        // Set the total price on the Order object
        newOrder.setTotalPrice(totalPrice);

        // Save the Order object
        return orderRepo.save(newOrder);
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

        for (OrderItemRequest orderItemRequest : orderUpdateRequest.getOrderItems()) {
            // Check if the item already exists in the order
            Optional<OrderItem> existingOrderItemOptional = existingOrder.getOrderItems().stream()
                    .filter(orderItem -> orderItem.getItemId() == orderItemRequest.getItemId())
                    .findFirst();

            if (existingOrderItemOptional.isPresent()) {
                // If the item exists, update its properties
                OrderItem existingOrderItem = existingOrderItemOptional.get();
                existingOrderItem.setQuantity(orderItemRequest.getQuantity());
                existingOrderItem.setUnitPrice(getProductUnitPrice(orderItemRequest.getItemId()));
                existingOrderItem.setTotalUnitPrice(orderItemRequest.getQuantity() * existingOrderItem.getUnitPrice());

                // Add the updated item to the list
                updatedOrderItems.add(existingOrderItem);

                // Update the product quantity in the database
                Inventory inventory = inventoryRepo.getProductByProductID(orderItemRequest.getItemId());
                inventory.setQuantity(inventory.getQuantity() + existingOrderItem.getQuantity() - orderItemRequest.getQuantity());
                inventoryRepo.save(inventory);
            } else {
                throw new ProductNotFoundException("Product not found with id " + orderItemRequest.getItemId());
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

        double totalPrice = 0;
        for (OrderItem orderItem : existingOrder.getOrderItems()) {
            totalPrice += orderItem.getTotalUnitPrice();
        }

        // Set the total price on the Order object
        existingOrder.setTotalPrice(totalPrice);

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

    public List<UserDelivery> getAllUserDelivery() {
        List<Order> orderList = orderRepo.findAll();
        List<UserDelivery> userDeliveryList = new ArrayList<>();
        for (Order order : orderList) {
            Delivery delivery = deliveryRepo.findByOrder(order);
            UserDelivery userDelivery = new UserDelivery();
            userDelivery.setDeliveryId(delivery.getDeliveryId());
            userDelivery.setOrderId(order.getOrderId());
            userDelivery.setDeliveryStatus(delivery.getStatus());
            userDelivery.setTotalPrice(order.getTotalPrice());
            userDelivery.setIsPrepared(order.getIsPrepared());

            userDeliveryList.add(userDelivery);
        }
        return userDeliveryList;
    }*/
}

