package com.EAD.EAD_monolithic.dto;

import com.EAD.EAD_monolithic.dto.OrderItemRequest;
import com.EAD.EAD_monolithic.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    private int orderId;
    private int userId;
    private Double totalPrice;
    private Boolean isPrepared;
    private List<OrderItem> orderItems;
}