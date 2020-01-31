package com.example.mapper;

import com.example.role.Role;
import com.example.role.RoleDTO;

public class RoleMapper {

    public static RoleDTO toDTO(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getRole()
        );
    }

    public static Role toEntity(RoleDTO dto) {
        return new Role(
                dto.getId(),
                dto.getRole()
        );
    }
}
