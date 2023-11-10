package com.EAD.EAD_monolithic.controller;

import com.EAD.EAD_monolithic.dto.DeliveryDTO;
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
    public List<DeliveryDTO> getAllDeliveries(){
        return deliveryService.getAllDeliveries();
    }

    @PostMapping("/newDelivery")
    public DeliveryDTO newDelivery(@RequestBody DeliveryDTO deliveryDTO){
        return deliveryService.newDelivery(deliveryDTO);
    }

    @PutMapping("/editDelivery")
    public DeliveryDTO editDelivery(@RequestBody DeliveryDTO deliveryDTO){
        return deliveryService.editDelivery(deliveryDTO);
    }

    @DeleteMapping("/{deliveryId}")
    public boolean deleteDelivery(@PathVariable long deliveryId){
        return deliveryService.deleteDelivery(deliveryId);
    }
}
