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
public class Reservelist { //예약정보 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reserveid; //예약번호

    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private PostList postid; //예약게시물

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User userid; //대여자

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime rentaldate; //대여자의 대여요청날짜

    @Column
    private LocalDateTime returndate; //대여자의 반납요청날짜
}
