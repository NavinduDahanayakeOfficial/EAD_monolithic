package com.EAD.EAD_monolithic;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Product {
    @Id
    private int itemId;
    private String name;
    private String description;
    private double price;
    private int quantity;
}
