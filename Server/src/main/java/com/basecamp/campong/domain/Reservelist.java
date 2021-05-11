package com.basecamp.campong.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private PostList post; //예약게시물

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user; //대여자

    @Column(nullable = false)
    private String rentaldate; //대여자의 대여요청날짜

    @Column(nullable = false)
    private String returndate; //대여자의 반납요청날짜

    @Builder.Default
    @OneToMany(mappedBy = "reserve")
    private List<ReserveState> posts = new ArrayList<ReserveState>();

    @Transient
    private long postid;

}
