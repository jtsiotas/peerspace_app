package com.peerspaceClone.backend.model;

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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", nullable = false)
    private Role role;

    private String firstname;
    private String lastname;

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
    private Set<Property> properties = new HashSet<>();

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY)
    private Set<Booking> bookings = new HashSet<>();

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>();

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private Set<Message> messages = new HashSet<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities =  new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        role.getCapabilities()
                .forEach(capability -> grantedAuthorities.add(new SimpleGrantedAuthority(capability.getName())));
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted();
    }
    

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

    public Set<Booking> getAllBookings() {
        return Collections.unmodifiableSet(bookings);
    }

    public void addBooking(Booking booking) {
        if (bookings == null) bookings = new HashSet<>();
        bookings.add(booking);
        booking.setGuest(this);
    }

    public void removeBooking(Booking booking) {
        if (bookings == null) return;
        bookings.remove(booking);
        booking.setGuest(null);
    }

    public Set<Review> getAllReviews() {
        return Collections.unmodifiableSet(reviews);
    }

    public void addReview(Review review) {
        if (reviews == null) reviews = new HashSet<>();
        reviews.add(review);
        review.setReviewer(this);
    }

    public void removeReview(Review review) {
        if (reviews == null) return;
        reviews.remove(review);
        review.setReviewer(null);
    }

    public Set<Message> getAllMessages() {
        return Collections.unmodifiableSet(messages);
    }

    public void addMessage(Message message) {
        if (messages == null) messages = new HashSet<>();
        messages.add(message);
        message.setSender(this);
    }

    public void removeMessage(Message message) {
        if (messages == null) return;
        messages.remove(message);
        message.setSender(null);
    }   
}
