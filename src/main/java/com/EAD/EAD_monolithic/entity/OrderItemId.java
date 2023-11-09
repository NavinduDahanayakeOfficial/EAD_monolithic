package com.EAD.EAD_monolithic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class OrderItemId implements Serializable {

    @Column(name = "item_id")
    private int itemId;

    @Column(name = "order_id")
    private int orderId;

    // Getters, setters, and constructors
}