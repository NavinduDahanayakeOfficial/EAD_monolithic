package com.EAD.EAD_monolithic.controller;

import com.EAD.EAD_monolithic.dto.OrderDTO;
import com.EAD.EAD_monolithic.dto.OrderRequest;
import com.EAD.EAD_monolithic.entity.Order;
import com.EAD.EAD_monolithic.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getOrders")
    public List<OrderDTO> getOrder(){
        return orderService.getAllOrders();
    }

    @PostMapping("/saveOrder")
    public OrderDTO saveOrder(@RequestBody OrderRequest orderRequest){
        Order order = orderService.saveOrder(orderRequest);

        return modelMapper.map(order, OrderDTO.class);
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
