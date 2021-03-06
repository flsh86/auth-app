package com.example.user;

import com.example.role.RoleDTO;

import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<RoleDTO> roles;
    private Boolean isEnabled;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String password, String email, Boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isEnabled = isEnabled;
    }


    public UserDTO(Long id, String username, String password, String email, Boolean isEnabled, Set<RoleDTO> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}
