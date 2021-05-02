package com.basecamp.campong.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String usernick;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String phone;

    //@Column(nullable = false)
    //private String profile;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime date;
}
