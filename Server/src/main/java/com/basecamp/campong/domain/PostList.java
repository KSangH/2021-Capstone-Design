package com.basecamp.campong.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table
@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postid;

    @ManyToOne
    @JoinColumn(name = "cateid")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime uploaddate;

    @Lob
    @Column
    private byte[] itemphoto;

    @Column(nullable = false)
    private long fee;

    @Column(nullable = false)
    private String lat;

    @Column(nullable = false)
    private String lon;

    @Column(nullable = false)
    private String location;

    @Column
    private int deletestate = 0;

    @Column
    private LocalDateTime deletedate;

}
