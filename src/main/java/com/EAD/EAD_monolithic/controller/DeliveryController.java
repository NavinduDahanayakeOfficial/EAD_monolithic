package com.EAD.EAD_monolithic.controller;

import com.EAD.EAD_monolithic.Exception.NotFoundException;
import com.EAD.EAD_monolithic.dto.DeliveryDTO;

import com.EAD.EAD_monolithic.dto.DeliveryPerson;

import com.EAD.EAD_monolithic.dto.UserDelivery;
import com.EAD.EAD_monolithic.entity.Delivery;
import com.EAD.EAD_monolithic.entity.Order;
import com.EAD.EAD_monolithic.service.DeliveryService;
import com.EAD.EAD_monolithic.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/delivery")
@CrossOrigin
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/getDeliveries")
    public List<DeliveryDTO> getAllDeliveries(){
        return deliveryService.getAllDeliveries();
    }

    @PostMapping("/newDelivery")
    public Delivery newDelivery(@RequestBody DeliveryDTO deliveryDTO){
//        if(orderService.getOrderById(deliveryDTO.getDeliveryId()).getIsPrepared() == false){
//            throw new NotFoundException("Order is no Prepared");
//        }
        return deliveryService.newDelivery(deliveryDTO);
    }

    @GetMapping("/getDeliveryById/{deliveryId}")
    public Delivery getDeliveryById(@PathVariable int deliveryId){
        return deliveryService.getDeliveryById(deliveryId);
    }
    @PatchMapping("/editDelivery/{deliveryId}")
    public Delivery editDelivery(@PathVariable int deliveryId, @RequestBody DeliveryDTO deliveryDTO){
        return deliveryService.editDelivery(deliveryId, deliveryDTO);
    }

    @DeleteMapping("/deleteDelevery/{deliveryId}")
    public String deleteDelivery(@PathVariable int deliveryId){
//        if(deliveryService.getDeliveryById(deliveryId).getStatus() == "Delivered"){
//            return ("Can't Cancel. Order already delivered.");
//        } else {
            return deliveryService.deleteDelivery(deliveryId);
//        }
    }

    @GetMapping("/getDeliveryPerson")
    public List<DeliveryPerson> getDeliveryPerson() {
        return deliveryService.getDeliveryPerson();
    }

    @GetMapping("/getAllUserDelivery")
    public List<UserDelivery> getAllUserDelivery() {
        return deliveryService.getAllUserDelivery();
    }


}
