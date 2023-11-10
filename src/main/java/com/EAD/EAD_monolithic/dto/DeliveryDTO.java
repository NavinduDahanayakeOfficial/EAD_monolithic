package com.EAD.EAD_monolithic.dto;

import com.EAD.EAD_monolithic.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryDTO {
    private long deliveryId;
    private int orderId;
//    private User customerId;
//    private User deliveryPersonID;
    private String status;
}
