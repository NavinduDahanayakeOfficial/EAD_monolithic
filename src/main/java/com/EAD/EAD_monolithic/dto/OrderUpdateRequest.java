package com.EAD.EAD_monolithic.dto;

import com.EAD.EAD_monolithic.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderUpdateRequest {
    private int userId;
    private boolean isPrepared;
    private List<OrderItem> orderItems;
}
