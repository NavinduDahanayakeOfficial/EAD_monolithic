package com.EAD.EAD_monolithic.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    //unauthorized user don't have any permission
    USER(Collections.emptySet()),

    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE,
                    Permission.ADMIN_CREATE,
                    Permission.CUSTOMER_READ,
                    Permission.CUSTOMER_UPDATE,
                    Permission.CUSTOMER_DELETE,
                    Permission.CUSTOMER_CREATE,
                    Permission.DELIVERY_PERSON_READ,
                    Permission.DELIVERY_PERSON_UPDATE,
                    Permission.DELIVERY_PERSON_CREATE,
                    Permission.DELIVERY_PERSON_DELETE,
                    Permission.INVENTORY_KEEPER_READ,
                    Permission.INVENTORY_KEEPER_UPDATE,
                    Permission.INVENTORY_KEEPER_CREATE,
                    Permission.INVENTORY_KEEPER_DELETE
            )
    ),
    CUSTOMER(
            Set.of(
                    Permission.CUSTOMER_READ,
                    Permission.CUSTOMER_UPDATE,
                    Permission.CUSTOMER_DELETE,
                    Permission.CUSTOMER_CREATE
            )
    ),
    DELIVERY_PERSON(
            Set.of(
                    Permission.DELIVERY_PERSON_READ,
                    Permission.DELIVERY_PERSON_UPDATE,
                    Permission.DELIVERY_PERSON_CREATE,
                    Permission.DELIVERY_PERSON_DELETE
            )
    ),
    INVENTORY_KEEPER(
            Set.of(
                    Permission.INVENTORY_KEEPER_READ,
                    Permission.INVENTORY_KEEPER_UPDATE,
                    Permission.INVENTORY_KEEPER_CREATE,
                    Permission.INVENTORY_KEEPER_DELETE
            )
    )
    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities= getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
        return authorities;
    }
}
