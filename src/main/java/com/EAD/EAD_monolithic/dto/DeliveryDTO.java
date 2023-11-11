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
    private int customerId;
    private int deliveryPersonId;
    private String status;
}
