package com.example.mapper;

import com.example.role.Role;
import com.example.role.RoleDTO;
import com.example.user.User;
import com.example.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO toDTO(User user) {
        Set<RoleDTO> roles = user.getRoles()
                .stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getEmail(),
                roles);
    }

    public User toEntity(UserDTO dto) {
        Set<Role> role = dto.getRoles()
                .stream()
                .map(RoleMapper::toEntity)
                .collect(Collectors.toSet());

        return new User(
                dto.getId(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getEmail(),
                role
        );
    }
}
