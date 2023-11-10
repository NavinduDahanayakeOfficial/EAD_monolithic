package com.EAD.EAD_monolithic.service;

import com.EAD.EAD_monolithic.Exception.OrderNotFoundException;
import com.EAD.EAD_monolithic.dto.DeliveryDTO;
import com.EAD.EAD_monolithic.entity.Delivery;
import com.EAD.EAD_monolithic.entity.Order;
import com.EAD.EAD_monolithic.repo.DeliveryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DeliveryService {
    @Autowired
    private DeliveryRepo deliveryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderService orderService;

    public List<Delivery> getAllDeliveries(){
        List<Delivery>deliveryList = deliveryRepo.findAll();
        return modelMapper.map(deliveryList, new TypeToken<List<Delivery>>(){}.getType());
    }

    public Delivery newDelivery(DeliveryDTO deliveryDTO){
        Delivery delivery = new Delivery();
        delivery.setDeliveryId(deliveryDTO.getDeliveryId());
        delivery.setOrder(orderService.getOrderById(deliveryDTO.getOrderId()));
        delivery.setStatus(deliveryDTO.getStatus());
        deliveryRepo.save(modelMapper.map(deliveryDTO, Delivery.class));
        return delivery;
    }

    public Delivery getDeliveryById(int deliveryId) {
        Delivery delivery = deliveryRepo.findById(deliveryId).orElse(null);
        if (delivery == null) {
            throw new OrderNotFoundException("Delivery not found with id " + deliveryId);
        }
        return delivery;
    }

    public Delivery editDelivery(int deliveryId, DeliveryDTO deliveryDTO){
        Delivery delivery = getDeliveryById(deliveryId);
        //delivery.setDeliveryId(deliveryDTO.getDeliveryId());
        delivery.setOrder(orderService.getOrderById(deliveryDTO.getOrderId()));
        deliveryRepo.save(modelMapper.map(deliveryDTO, Delivery.class));
        return delivery;
    }

    public boolean deleteDelivery(int deliveryId){
        deliveryRepo.deleteById(deliveryId);
        return true;
    }
}
