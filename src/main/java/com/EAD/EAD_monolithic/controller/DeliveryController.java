package com.EAD.EAD_monolithic.controller;

import com.EAD.EAD_monolithic.dto.DeliveryDTO;

import com.EAD.EAD_monolithic.dto.DeliveryPerson;

import com.EAD.EAD_monolithic.dto.UserDelivery;
import com.EAD.EAD_monolithic.entity.Delivery;
import com.EAD.EAD_monolithic.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/delivery")
@CrossOrigin
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/getDeliveries")
    public List<Delivery> getAllDeliveries(){
        return deliveryService.getAllDeliveries();
    }

    @PostMapping("/newDelivery")
    public Delivery newDelivery(@RequestBody DeliveryDTO deliveryDTO){
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

    public boolean deleteDelivery(@PathVariable int deliveryId){
        return deliveryService.deleteDelivery(deliveryId);
    }

//    @GetMapping("/getDeliveryById/{deliveryId}")
//    public Delivery getDeliveryById(@PathVariable int deliveryId){
//        return deliveryService.getDeliveryById(deliveryId);
//    }

    @GetMapping("/getDeliveryPerson")
    public List<DeliveryPerson> getDeliveryPerson() {
        return deliveryService.getDeliveryPerson();
    }

    @GetMapping("/getAllUserDelivery")
    public List<UserDelivery> getAllUserDelivery() {
        return deliveryService.getAllUserDelivery();
    }


}
