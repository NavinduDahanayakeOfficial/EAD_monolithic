package com.EAD.EAD_monolithic.dto;

import com.EAD.EAD_monolithic.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String deliveryAddress;
    private Role role;
}
