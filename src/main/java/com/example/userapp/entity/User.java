
// ===================================================================================
// FILE: src/main/java/com/example/taskapp/entity/User.java
// ===================================================================================
package com.example.userapp.entity;

import javax.persistence.*;
import lombok.Data;

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
}
