package com.spring.data.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "party_permission", joinColumns = @JoinColumn(name = "party_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();

    public boolean removePermission(Permission permission) {
        return getPermissions().remove(permission);
    }

    public boolean addPermission(Permission permission) {
        return getPermissions().add(permission);
    }
}
