package com.EAD.EAD_monolithic.dto;


import com.EAD.EAD_monolithic.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {
    private int userId;
    private List<OrderItem> orderItems;
}
