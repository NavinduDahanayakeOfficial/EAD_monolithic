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
    private long deliveryId;
    private String status;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;

//    @ManyToOne
//    @JoinColumn(name = "customerId", referencedColumnName = "userId")
//    private User customer;
//
//    @ManyToOne
//    @JoinColumn(name = "deliveryPersonId", referencedColumnName = "userId")
//    private User deliveryPerson;


}
