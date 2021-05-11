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
public class ReserveState {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stateid;

    @ManyToOne
    @JoinColumn(name = "reserveid", nullable = false)
    private Reservelist reserve;

    @Builder.Default
    @Column(nullable = false)
    private int state = 0;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedate;
}
