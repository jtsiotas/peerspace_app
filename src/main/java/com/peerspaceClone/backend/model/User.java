package com.peerspaceClone.backend.model;

//import gr.aueb.cf.eduapp.model.static_data.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;   

    @Column(unique = true)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstname;
    private String lastname;

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
    private Set<Property> properties = new HashSet<>();
    

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

    public Set<Property> getAllProperties() {
        return Collections.unmodifiableSet(properties);
    }

     public void addProperty(Property property) {
        if (properties == null) properties = new HashSet<>();
        properties.add(property);
        property.setUser(this);
    }

    public void removeProperty(Property property) {
        if (properties == null) return;
        properties.remove(property);
        property.setUser(null);
    }
}
