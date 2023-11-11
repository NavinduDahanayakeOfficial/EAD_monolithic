package com.EAD.EAD_monolithic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryDTO {
    private int itemId;
    private String name;
    private String description;
    private double unitPrice;
    private int quantity;
}
