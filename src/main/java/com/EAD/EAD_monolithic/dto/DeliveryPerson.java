package com.EAD.EAD_monolithic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryPerson {
    private int deliveryId;
    private String deliveryStatus;
    private Double totalPrice;
    private Boolean isPrepared;
}
