package com.EAD.EAD_monolithic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryDTO {
    private int deliveryId;
    private int orderId;
//    private User customerId;
//    private User deliveryPersonID;
    private String status;
}
