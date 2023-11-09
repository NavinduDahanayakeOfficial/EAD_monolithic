package com.EAD.EAD_monolithic.entity;

import com.EAD.EAD_monolithic.entity.Order;
import com.EAD.EAD_monolithic.entity.OrderItemId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @MapsId("orderId")
    private Order order;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "total_unit_price", nullable = false)
    private Double totalUnitPrice;



}