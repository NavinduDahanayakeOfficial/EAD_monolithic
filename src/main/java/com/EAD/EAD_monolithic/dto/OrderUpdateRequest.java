package com.EAD.EAD_monolithic.dto;

import com.EAD.EAD_monolithic.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderUpdateRequest {
    private int userId;
    private boolean isPrepared;
    private List<OrderItemRequest> orderItems;

    public List<Integer> getOrderItemIds() {
        return orderItems.stream()
                .map(orderItemRequest -> orderItemRequest.getItemId())
                .collect(Collectors.toList());
    }
}
