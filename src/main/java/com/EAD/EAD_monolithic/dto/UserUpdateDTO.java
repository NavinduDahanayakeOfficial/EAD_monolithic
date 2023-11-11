package com.EAD.EAD_monolithic.dto;


import com.EAD.EAD_monolithic.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDTO {
    private Integer userId;
    private Role role;
}
