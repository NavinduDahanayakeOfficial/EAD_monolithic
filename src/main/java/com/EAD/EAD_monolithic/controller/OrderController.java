package com.EAD.EAD_monolithic.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/order")
@CrossOrigin
public class OrderController {

    @GetMapping("/getOrder")
    public String getOrder(){
        return "getOrder";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(){
        return "saveOrder";
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
