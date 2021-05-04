package com.basecamp.campong.domain;

import lombok.*;

import javax.persistence.*;

@Table
@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cateid;

    @Column(nullable = false)
    private String catename;

}
