package com.EAD.EAD_monolithic.service;

import com.EAD.EAD_monolithic.dto.DeliveryDTO;
import com.EAD.EAD_monolithic.entity.Delivery;
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
        deliveryRepo.save(modelMapper.map(deliveryDTO, Delivery.class));
        return delivery;
    }

    public DeliveryDTO editDelivery(DeliveryDTO deliveryDTO){
        deliveryRepo.save(modelMapper.map(deliveryDTO, Delivery.class));
        return deliveryDTO;
    }

    public boolean deleteDelivery(long deliveryId){
        deliveryRepo.deleteById(deliveryId);
        return true;
    }
}
