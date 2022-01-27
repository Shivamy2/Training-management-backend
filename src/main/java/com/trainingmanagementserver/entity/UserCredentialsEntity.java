package com.trainingmanagementserver.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Table(name = "user")
@Setter
@ToString
@AllArgsConstructor
public class UserCredentialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

//    @NotEmpty(message = "Username is mandatory")
//    @Size(min = 5, max = 15, message = "Username must be of 5 to 15 characters")
    private String username;

//    @NotEmpty(message = "Password is mandatory")
//    @Size(min = 5, max = 50, message = "Password must be b/w 5 to 50 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

//    @NotEmpty(message = "Email is mandatory")
//    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public UserCredentialsEntity(String username, String password, String email, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public UserCredentialsEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCredentialsEntity that = (UserCredentialsEntity) o;
        return id == that.id && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(email, that.email);
    }

    public int hashCode() {
        return getClass().hashCode();
    }
}
