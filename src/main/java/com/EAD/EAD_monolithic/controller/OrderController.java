package com.EAD.EAD_monolithic.controller;

import com.EAD.EAD_monolithic.dto.OrderDTO;
import com.EAD.EAD_monolithic.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrder")
    public String getOrder(){
        return "getOrder";
    }

    @PostMapping("/saveOrder")
    public OrderDTO saveOrder(@RequestBody OrderDTO orderDTO){
        orderService.saveOrder(orderDTO);
        return null;
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
