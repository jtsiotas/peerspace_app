package com.peerspaceClone.backend.model;

//import gr.aueb.cf.eduapp.model.static_data.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @Column(unique = true)
    private String name;

    private String email;
    private String password;

    private String firstname;
    private String lastname;
    

    @PrePersist
    public void initializeUUID() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUuid(), user.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUuid());
    }
}
