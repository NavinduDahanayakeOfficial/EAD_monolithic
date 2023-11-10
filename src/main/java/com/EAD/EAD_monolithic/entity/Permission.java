package com.EAD.EAD_monolithic.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_DELETE("customer:delete"),
    DELIVERY_PERSON_READ("deliveryPerson:read"),
    DELIVERY_PERSON_UPDATE("deliveryPerson:update"),
    DELIVERY_PERSON_CREATE("deliveryPerson:create"),
    DELIVERY_PERSON_DELETE("deliveryPerson:delete"),
    INVENTORY_KEEPER_READ("inventoryKeeper:read"),
    INVENTORY_KEEPER_UPDATE("inventoryKeeper:update"),
    INVENTORY_KEEPER_CREATE("inventoryKeeper:create"),
    INVENTORY_KEEPER_DELETE("inventoryKeeper:delete"),
    ;

    @Getter
    private final String permission;
}
