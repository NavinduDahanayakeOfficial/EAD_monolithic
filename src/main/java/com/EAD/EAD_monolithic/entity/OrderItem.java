package com.EAD.EAD_monolithic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItem {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "total_unit_price", nullable = false)
    private Double totalUnitPrice;

}
