package com.EAD.EAD_monolithic.controller;

import com.EAD.EAD_monolithic.dto.OrderDTO;
import com.EAD.EAD_monolithic.dto.OrderRequest;
import com.EAD.EAD_monolithic.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrders")
    public List<OrderDTO> getOrder(){
        return orderService.getAllOrders();
    }

    @PostMapping("/saveOrder")
    public OrderRequest saveOrder(@RequestBody OrderRequest orderRequest){
        orderService.saveOrder(orderRequest);
        return orderRequest;
    }

    @PutMapping("/updateOrder")
    public String updateOrder(){
        return "updateOrder";
    }

    @DeleteMapping("/deleteOrder")
    public String deleteOrder(){
        return "deleteOrder";
    }


}
