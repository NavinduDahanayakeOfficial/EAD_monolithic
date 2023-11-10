package com.EAD.EAD_monolithic.service;


import com.EAD.EAD_monolithic.Exception.OrderNotFoundException;
import com.EAD.EAD_monolithic.dto.OrderDTO;
import com.EAD.EAD_monolithic.dto.OrderRequest;
import com.EAD.EAD_monolithic.dto.UserDelivery;
import com.EAD.EAD_monolithic.entity.Delivery;
import com.EAD.EAD_monolithic.entity.Order;
import com.EAD.EAD_monolithic.entity.OrderItem;
import com.EAD.EAD_monolithic.repo.DeliveryRepo;
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
    private DeliveryRepo deliveryRepo;

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

/*    public Order updateOrder(OrderUpdateRequest orderUpdateRequest, int id){
        Order order = orderRepo.findById(id).orElse(null);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with id " + id);
        }

        order.setUserId(orderUpdateRequest.getUserId());
        order.setIsPrepared(orderUpdateRequest.isPrepared());



        return orderRepo.save(modelMapper.map(orderDTO, Order.class));
    }*/

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
    }
}

