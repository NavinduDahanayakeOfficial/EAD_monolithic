package com.EAD.EAD_monolithic.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemRequest {
    private int itemId;
    private int quantity;
    private Double unitPrice;  //this should be retrieved from item table
}
