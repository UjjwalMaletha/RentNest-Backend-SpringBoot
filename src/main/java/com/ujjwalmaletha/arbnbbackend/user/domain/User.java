package com.ujjwalmaletha.arbnbbackend.user.domain;

import com.ujjwalmaletha.arbnbbackend.sharedkernel.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Setter
@Entity
@Table(name = "airbnb_user")
public class User extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_generator", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Getter
    @Column(name = "last_name")
    private String lastName;

    @Getter
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Column(name = "email",length = 500)
    private String email;

    @Getter
    @Column(name = "image_url" , columnDefinition = "TEXT")
    private String imageUrl;

    @UuidGenerator
    @Column(name = "public_id", nullable = false)
    private UUID publicId;

    @Getter
    @ManyToMany
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) && Objects.equals(email, user.email) && Objects.equals(imageUrl, user.imageUrl) && Objects.equals(publicId, user.publicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, email, imageUrl, publicId);
    }

    @Override
    public String toString() {
        return "User{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publicId=" + publicId +
                '}';
    }
}
