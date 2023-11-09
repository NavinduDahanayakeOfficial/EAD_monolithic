package com.EAD.EAD_monolithic.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderItemRequest {
    private int itemId;
    private int quantity;
    private Double unitPrice;
}
