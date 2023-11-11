package com.EAD.EAD_monolithic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Delivery")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deliveryId;
    private String status;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", referencedColumnName = "userId")
    @JsonBackReference
    private User customer;
//
    @ManyToOne
    @JoinColumn(name = "deliveryPersonId", referencedColumnName = "userId")
    private User deliveryPerson;


}
