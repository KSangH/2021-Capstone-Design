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
public class PostList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postid;

    @ManyToOne
    @JoinColumn(name = "cateid", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime uploaddate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageid")
    private Image itemphoto;

    @Column(nullable = false)
    private long fee;

    @Column(nullable = false)
    private String lat;

    @Column(nullable = false)
    private String lon;

    @Column(nullable = false)
    private String location;

    @Column
    @Builder.Default
    private int deletestate = 0;

    @Column
    private LocalDateTime deletedate;

    @Transient //임시로 받을 카테고리명
    private String catename;

    @Transient //임시로 받을 imageid
    private long imageid;
}
