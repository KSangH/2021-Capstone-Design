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
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageid;

    @Column(nullable = false)
    private String mediatype;

    @Lob
    @Column(nullable = false)
    private byte[] imagefile;

}
