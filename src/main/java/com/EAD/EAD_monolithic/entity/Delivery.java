package com.EAD.EAD_monolithic.entity;

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

    @OneToOne
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;

    public String getDeliveryStatus() {
        return status;
    }

//    @ManyToOne
//    @JoinColumn(name = "customerId", referencedColumnName = "userId")
//    private User customer;
//
//    @ManyToOne
//    @JoinColumn(name = "deliveryPersonId", referencedColumnName = "userId")
//    private User deliveryPerson;


}
