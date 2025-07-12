// ===================================================================================
// FILE: src/main/java/com/example/taskapp/entity/User.java
// ===================================================================================
package com.example.userapp.entity;

import javax.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "bit default 1")
    private boolean enabled = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private List<String> authorities = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (authorities.isEmpty()) {
            authorities.add("ROLE_USER");
        }
        if (!enabled) {
            enabled = true;
        }
    }
}
