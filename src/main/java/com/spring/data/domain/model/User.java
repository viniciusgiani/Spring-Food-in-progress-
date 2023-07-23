package com.spring.data.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime registerData;

    @ManyToMany
    @JoinTable(name = "user_party", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "party_id"))
    private Set<Party> parties = new HashSet<>();

    public boolean removeParty(Party party) {
        return getParties().remove(party);
    }

    public boolean addParty(Party party) {
        return getParties().add(party);
    }

    public boolean isNew() {
        return getId() == null;
    }

}
