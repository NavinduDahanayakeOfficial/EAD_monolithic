package com.EAD.EAD_monolithic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryPerson {
    private int deliveryId;
    private String deliveryStatus;
    private Double totalPrice;
    private Boolean isPrepared;
//    private String address;
//    private String firstName;
//    private String lastName;

//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }

}
