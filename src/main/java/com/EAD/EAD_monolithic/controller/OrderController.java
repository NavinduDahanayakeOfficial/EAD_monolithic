package com.EAD.EAD_monolithic.controller;

import com.EAD.EAD_monolithic.dto.OrderDTO;
import com.EAD.EAD_monolithic.dto.OrderRequest;
import com.EAD.EAD_monolithic.dto.OrderUpdateRequest;
import com.EAD.EAD_monolithic.dto.UserDelivery;
import com.EAD.EAD_monolithic.entity.Order;
import com.EAD.EAD_monolithic.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/saveOrder")
    public OrderDTO saveOrder(@RequestBody OrderRequest orderRequest){
        Order order = orderService.saveOrder(orderRequest);
        return modelMapper.map(order, OrderDTO.class);
    }

    @GetMapping("/getOrders")
    public List<OrderDTO> getOrder(){
        return orderService.getAllOrders();
    }

    @GetMapping("/getOrderById/{id}")
    public OrderDTO getOrderById(@PathVariable int id){
        return modelMapper.map(orderService.getOrderById(id), OrderDTO.class);
    }

    @PutMapping("/updateOrder/{id}")
    public OrderDTO updateOrder(@RequestBody OrderUpdateRequest orderUpdateRequest, @PathVariable int id) {
        Order order = orderService.updateOrder(orderUpdateRequest, id);
        return modelMapper.map(order, OrderDTO.class);
    }


    @DeleteMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable int id){
        return orderService.deleteOrder(id);
    }

    @PatchMapping("/updateOrderIsPrepared/{orderId}/update-prepared")
    public ResponseEntity<String> updateOrderPreparedStatus(@PathVariable int orderId, @RequestParam boolean isPrepared) {
        orderService.updateOrderPreparedStatus(orderId, isPrepared);
        return ResponseEntity.ok("Order prepared status updated successfully");
    }

}
