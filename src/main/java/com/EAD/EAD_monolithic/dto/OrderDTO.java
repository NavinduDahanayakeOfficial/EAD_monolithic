package com.EAD.EAD_monolithic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    private int orderId;
    private int userId;
    private Double totalPrice;
    private Boolean isPrepared;
}
