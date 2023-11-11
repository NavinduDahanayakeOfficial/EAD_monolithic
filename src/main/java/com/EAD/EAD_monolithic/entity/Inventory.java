package com.EAD.EAD_monolithic.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Inventory {
    @Id
    private int itemId;
    private String name;
    private String description;
    private double unitPrice;
    private int quantity;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<OrderItem> orderItems;
}
